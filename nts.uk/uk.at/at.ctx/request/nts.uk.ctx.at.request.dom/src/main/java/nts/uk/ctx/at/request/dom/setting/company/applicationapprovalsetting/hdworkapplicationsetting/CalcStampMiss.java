package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.休日出勤申請設定.打刻漏れ計算区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum CalcStampMiss {
	
	/**
	 * 登録不可
	 */
	CAN_NOT_REGIS(0, "登録不可"),
	
	/**
	 * システム時刻仮計算
	 */
	CALC_STAMP_MISS(1, "システム時刻仮計算");
	
	public final int value;
	
	public final String name;
}
