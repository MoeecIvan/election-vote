package test.ivan.vote.admin.service.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import test.ivan.vote.admin.entity.User;
import test.ivan.vote.admin.vo.LoginUserVo;


@Mapper
public interface UserVoConvert {
    UserVoConvert INSTANCE = Mappers.getMapper(UserVoConvert.class);

    /**
     * User对象转换成LoginUserVo
     * @param user
     * @return
     */
    LoginUserVo userToVo(User user);

}
