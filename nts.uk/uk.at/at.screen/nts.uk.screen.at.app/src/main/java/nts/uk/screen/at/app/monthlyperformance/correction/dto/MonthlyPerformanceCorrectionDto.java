package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;

/**
 * TODO
 */
@Getter
@Setter
public class MonthlyPerformanceCorrectionDto {
	/**
	 * 承認処理の利用設定
	 */
	private ApprovalProcessingUseSetting approvalProcessingUseSetting;
	/**
	 * 本人確認処理の利用設定
	 */
	private IdentityProcessUseSet identityProcessUseSet;
	/**
	 * 現在の締め期間
	 */
	private PresentClosingPeriodExport presentClosingPeriodExport;
	/**
	 * list fixed header
	 */
	private List<MPHeaderDto> lstFixedHeader;
}
