package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.利用する残業枠設定
 * @author Doan Duy Hung
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeQuotaSetUse {
	
	/**
	 * 残業申請区分
	 */
	private OvertimeAppAtr overtimeAppAtr;
	
	/**
	 * フレックス勤務者区分
	 */
	private FlexWorkAtr flexWorkAtr;
	
	/**
	 * 対象残業枠
	 */
	private List<OverTimeFrameNo> targetOvertimeLimit;

	public static OvertimeQuotaSetUse create(int overtimeAppAtr, int flexWorkAtr, List<Integer> targetOvertimeLimit) {
		return new OvertimeQuotaSetUse(
				EnumAdaptor.valueOf(overtimeAppAtr, OvertimeAppAtr.class),
				EnumAdaptor.valueOf(flexWorkAtr, FlexWorkAtr.class),
				targetOvertimeLimit.stream().map(OverTimeFrameNo::new).collect(Collectors.toList())
		);
	}

}
