package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 支払日の設定の規定値
 */
@Getter
public class ValPayDateSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	private BasicSetting basicSetting;

	private AdvancedSetting advancedSetting;

	public ValPayDateSet(String cid, int processCateNo, int processMonth, int disposalDay, int refeMonth, int refeDate,
			int datePayMent, BigDecimal workDay, int printingMonth, int socialInsuColleMonth, int empInsurRefeDate,
			int empInsurBaseMonth, int inComRefeDate, int inComBaseYear, int inComBaseMonth, int sociInsuBaseMonth,
			int sociInsuBaseYear, int sociInsuRefeDate, int timeCloseDate, Integer closeDateBaseMonth,
						 Integer closeDateBaseYear, Integer closeDateRefeDate) {
		super();
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.basicSetting = new BasicSetting(processMonth, disposalDay, refeMonth, refeDate, datePayMent, workDay);
		this.advancedSetting = new AdvancedSetting(printingMonth, socialInsuColleMonth, empInsurRefeDate,
				empInsurBaseMonth, inComRefeDate, inComBaseYear, inComBaseMonth, sociInsuBaseMonth, sociInsuBaseYear,
				sociInsuRefeDate, timeCloseDate, closeDateBaseMonth, closeDateBaseYear, closeDateRefeDate);
	}

}
