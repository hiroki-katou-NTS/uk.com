package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import lombok.Data;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.setting.UserInfoUseMethod_Dto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod_;

/**
 * 
 * @author admin
 *
 */
@Data
public class UserInfoUseMethod_SaveCommand {
	
	/**
	 * DTO ユーザー情報の使用方法
	 */
	private UserInfoUseMethod_Dto userInfoUseMethod_Dto;
	
	public UserInfoUseMethod_ toDomain() {
		return UserInfoUseMethod_.createFromMemento(this.userInfoUseMethod_Dto);
	}
}
