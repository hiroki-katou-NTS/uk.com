package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 高度な設定
 */
@Getter
public class AdvancedSetting extends DomainObject {

	/**
	 * 明細印字月
	 */
	private DetailPrintingMonth itemPrintingMonth;

	/**
	 * 社会保険徴収月
	 */
	private SalaryInsuColMon socialInsuColleMon;

	/**
	 * 雇用保険基準日
	 */
	private EmpInsurStanDate empInsurStanDate;

	/**
	 * 所得税基準年月日
	 */
	private IncomTaxBaseYear incomTaxBaseYear;

	/**
	 * 社会保険基準年月日
	 */
	private SociInsuStanDate sociInsuStanDate;

	/**
	 * 勤怠締め年月日
	 */
	private CloseDate closeDate;

	public AdvancedSetting(int printingMonth, int socialInsuColleMon, int empInsurRefeDate, int empInsurBaseMonth,
			int inComRefeDate, int inComBaseYear, int inComBaseMonth, int sociInsuBaseMonth, int sociInsuBaseYear,
			int sociInsuRefeDate, int timeCloseDate, Integer closeDateBaseMonth, Integer closeDateBaseYear,
						   Integer closeDateRefeDate) {
		super();
		this.itemPrintingMonth = new DetailPrintingMonth(printingMonth);
		this.socialInsuColleMon = new SalaryInsuColMon(socialInsuColleMon);
		this.empInsurStanDate = new EmpInsurStanDate(empInsurRefeDate, empInsurBaseMonth);
		this.incomTaxBaseYear = new IncomTaxBaseYear(inComRefeDate, inComBaseYear, inComBaseMonth);
		this.sociInsuStanDate = new SociInsuStanDate(sociInsuBaseMonth, sociInsuBaseYear, sociInsuRefeDate);
		this.closeDate = new CloseDate(timeCloseDate, closeDateBaseMonth, closeDateBaseYear, closeDateRefeDate);
	}

}
