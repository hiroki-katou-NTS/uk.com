package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理社員ごとの既読状態
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.shared.トップページアラーム.管理社員ごとの既読状態
 * @author chungnt
 *
 */

@Getter
@AllArgsConstructor
public class ReadStatusManagementEmployee extends ValueObject {

	/**
	 * 了解フラグ
	 */
	private RogerFlag rogerFlag;
	
	/**
	 * 	社員ID
	 */
	private String employeeId;
	
	/**
	 * 	[C-1] 未読を作成する
	 * @param employeeId
	 */
	public ReadStatusManagementEmployee(String employeeId) {
		super();
		this.employeeId = employeeId;
		this.rogerFlag = RogerFlag.UNREAD;
	}
}
