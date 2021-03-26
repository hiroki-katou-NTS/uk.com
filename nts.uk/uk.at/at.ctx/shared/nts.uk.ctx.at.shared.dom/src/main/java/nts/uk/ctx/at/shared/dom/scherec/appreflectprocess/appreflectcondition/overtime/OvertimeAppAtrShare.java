package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.残業休出申請.残業申請.残業申請区分
 * @author thanhnx
 *
 */
@AllArgsConstructor
public enum OvertimeAppAtrShare {
	
	/**
	 * 早出残業
	 */
	EARLY_OVERTIME(0, "Enum_APP_OVERTIME_EARLY"),
	/**
	 * 通常残業
	 */
	NORMAL_OVERTIME(1, "Enum_APP_OVERTIME_NORMAL"),
	/**
	 * 早出残業・通常残業
	 */
	EARLY_NORMAL_OVERTIME(2, "Enum_APP_OVERTIME_EARLY_NORMAL");
	
	public final int value;
	
	public final String name;
}
