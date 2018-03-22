package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	
	/**
	 * コメント
	 */
	private String comment;
	/**
	 * 処理年月
	 * YYYYMM
	 */
	private int processDate;
	
	/**
	 * 締め名称
	 * 画面項目「A4_2：対象締め日」
	 */
	private String closureName;
	/**
	 * ・実績期間：List＜実績期間＞
	 * 画面項目「A4_5：実績期間選択肢」
	 */
	private List<ActualTime> lstActualTimes;
	/**
	 * 期間：取得した期間に一致する
	 * ※一致する期間がない場合は、先頭を選択状態にする
	 */
	private DatePeriod selectedActualTime;
}
