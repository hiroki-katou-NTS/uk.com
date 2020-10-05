package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.残業休出申請共通設定
 * @author Doan Duy Hung
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeLeaveAppCommonSet {

	/**
	 * 事前超過表示設定
	 */
	private NotUseAtr preExcessDisplaySetting;

	/**
	 * 時間外超過区分
	 */
	private Time36AgreeCheckRegister extratimeExcessAtr;

	/**
	 * 時間外表示区分
	 */
	private NotUseAtr extratimeDisplayAtr;

	/**
	 * 実績超過区分
	 */
	private AppDateContradictionAtr performanceExcessAtr;

	/**
	 * 登録時の指示時間超過チェック
	 */
	private NotUseAtr checkOvertimeInstructionRegister;

	/**
	 * 登録時の乖離時間チェック
	 */
	private NotUseAtr checkDeviationRegister;

	/**
	 * 実績超過打刻優先設定
	 */
	private OverrideSet overrideSet;

	public static OvertimeLeaveAppCommonSet create(int preExcessDisplaySetting, int extratimeExcessAtr, int extratimeDisplayAtr, int performanceExcessAtr, int checkOvertimeInstructionRegister, int checkDeviationRegister, int overrideSet) {
		return new OvertimeLeaveAppCommonSet(
				EnumAdaptor.valueOf(preExcessDisplaySetting, NotUseAtr.class),
				EnumAdaptor.valueOf(extratimeExcessAtr, Time36AgreeCheckRegister.class),
				EnumAdaptor.valueOf(extratimeDisplayAtr, NotUseAtr.class),
				EnumAdaptor.valueOf(performanceExcessAtr, AppDateContradictionAtr.class),
				EnumAdaptor.valueOf(checkOvertimeInstructionRegister, NotUseAtr.class),
				EnumAdaptor.valueOf(checkDeviationRegister, NotUseAtr.class),
				EnumAdaptor.valueOf(overrideSet, OverrideSet.class)
		);
	}

}
