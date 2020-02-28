package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * 給与支払日設定
 */
@AllArgsConstructor
@Getter
public class SetDaySupport extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 勤怠締め日
	 */
	private GeneralDate closeDateTime;

	/**
	 * 雇用保険基準日
	 */
	private GeneralDate empInsurdStanDate;

	/**
	 * 経理締め年月日
	 */
	private GeneralDate closureDateAccounting;

	/**
	 * 支払年月日
	 */
	private GeneralDate paymentDate;

	/**
	 * 社員抽出基準日
	 */
	private GeneralDate empExtraRefeDate;

	/**
	 * 社会保険基準日
	 */
	private GeneralDate socialInsurdStanDate;

	/**
	 * 社会保険徴収年月
	 */
	private int socialInsurdCollecMonth;

	/**
	 * 処理年月
	 */
	private YearMonth processDate;

	/**
	 * 所得税基準日
	 */
	private GeneralDate incomeTaxDate;

	/**
	 * 要勤務日数
	 */
	private ReqStandardWorkingDays numberWorkDay;

	public SetDaySupport(String cid,
						 int processCateNo,
						 int processDate,
						 GeneralDate closeDateTime,
						 GeneralDate empInsurdStanDate,
						 GeneralDate closureDateAccounting,
						 GeneralDate paymentDate,
						 GeneralDate empExtraRefeDate,
						 GeneralDate socialInsurdStanDate,
						 int socialInsurdCollecMonth,
						 GeneralDate incomeTaxDate,
						 BigDecimal numberWorkDay) {
		super();
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.closeDateTime = closeDateTime;
		this.empInsurdStanDate = empInsurdStanDate;
		this.closureDateAccounting = closureDateAccounting;
		this.paymentDate = paymentDate;
		this.empExtraRefeDate = empExtraRefeDate;
		this.socialInsurdStanDate = socialInsurdStanDate;
		this.socialInsurdCollecMonth = socialInsurdCollecMonth;
		this.processDate = new YearMonth(processDate);
		this.incomeTaxDate = incomeTaxDate;
		this.numberWorkDay = new ReqStandardWorkingDays(numberWorkDay);
	}

}
