package nts.uk.ctx.at.function.dom.alarmworkplace;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.List;

/**
 * アラームリスト権限設定
 */
@Getter
public class AlarmPermissionSetting extends DomainObject {

	/**
	 * 権限設定を使用する
	 */
	private boolean authSetting;

	/**
	 *  list role
	 */
	private List<String> roleIds;
	
	public AlarmPermissionSetting(boolean authSetting,List<String> roleIds) {
		super();
		this.authSetting = authSetting;
		this.roleIds = roleIds;
	}
	
}
