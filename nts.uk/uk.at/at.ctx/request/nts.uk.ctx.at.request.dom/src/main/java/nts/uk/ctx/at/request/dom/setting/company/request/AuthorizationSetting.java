package nts.uk.ctx.at.request.dom.setting.company.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 承認設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AuthorizationSetting extends DomainObject {
	
	/**
	 * 承認時に申請内容を変更できる
	 */
	private Boolean appContentChangeFlg;
	
	public static AuthorizationSetting toDomain(Integer appContentChangeFlg){
		return new AuthorizationSetting(appContentChangeFlg == 1 ? true : false);
	}
}
