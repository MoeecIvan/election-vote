package test.ivan.vote.admin.service.convert;

import javax.annotation.Generated;
import test.ivan.vote.admin.entity.User;
import test.ivan.vote.admin.vo.LoginUserVo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-19T16:48:22+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_281 (Oracle Corporation)"
)
public class UserVoConvertImpl implements UserVoConvert {

    @Override
    public LoginUserVo userToVo(User user) {
        if ( user == null ) {
            return null;
        }

        LoginUserVo loginUserVo = new LoginUserVo();

        loginUserVo.setUserId( user.getUserId() );
        loginUserVo.setUserName( user.getUserName() );
        loginUserVo.setPassword( user.getPassword() );
        loginUserVo.setAvatar( user.getAvatar() );
        loginUserVo.setNickName( user.getNickName() );
        loginUserVo.setEmail( user.getEmail() );
        loginUserVo.setCreatedTime( user.getCreatedTime() );

        return loginUserVo;
    }
}
