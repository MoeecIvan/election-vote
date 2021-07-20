package test.ivan.vote.admin.interceptor;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.xs.StringList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.common.constant.CommonRedisKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AdminApiInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        log.info("path:{}, method:{}", path, method);

        //printHeaders(request);
        if (!isIncludePath(path)) {
            log.info("不包含path");
            return true;
        }

        String token = request.getHeader("token");
        // 验证token
        if (token == null) {
            ApiResult result = ApiResult.fail(ApiCode.NOT_PERMISSION);
            returnJson(response, result);
            return false;
        }

        // token md5值
        String tokenMd5 = DigestUtils.md5Hex(token);
        String tokenRedisKey = String.format(CommonRedisKey.USER_LOGIN_TOKEN, tokenMd5);
        if (!redisTemplate.hasKey(tokenRedisKey)) {
            ApiResult result = ApiResult.fail(ApiCode.TOKEN_INVALID);
            returnJson(response, result);
            return false;
        }

        // Proceed in any case.
        return true;
    }

    private void returnJson(HttpServletResponse response, ApiResult result) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e){
            log.error("拦截器输出流异常"+e);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    private boolean isExcludePath(String path) {
        String paths = "/swagger-ui.html,/docs,/doc.html,/swagger-resources/**,/webjars/**,/v2/api-docs,/csrf,/v2/api-docs-ext,/null/swagger-resources/**";
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/admin/login");

        String[] pathList = paths.split(",");
        for (int i=0;i<pathList.length;i++) {
            excludePaths.add(pathList[i]);
        }
        return excludePaths.contains(path);
    }

    private boolean isIncludePath(String path) {
        String paths = "/admin/user,/admin/election,/admin/candidate";
        String[] pathList = paths.split(",");
        for (int i=0;i<pathList.length;i++) {
            if (path.startsWith(pathList[i])) {
                return true;
            }
        }
        return false;
    }

    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder builder = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerKey = headerNames.nextElement();
            builder.append(String.format("%s: %s;", headerKey, request.getHeader(headerKey)));
        }
        log.info("请求头：{}", builder.toString());
    }
}

