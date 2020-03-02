package nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author hieult
 * Domain 人事年度の就業規則
 */
@Getter

public class HrPeriodRegulation extends AggregateRoot {
	
	/** 会社ID **/
	private String companyId;
	
	/** 履歴ID **/
	private String historyId;
	
    /** 年度開始日 **/
	private int yearStartDate;
	/** 年度終了日 **/ 
	private int yearEndDate;
	/** 年度開始月 **/
	private int yearStartMonth;
	/** 年度終了月 **/
	private int yearEndMonth;
	public HrPeriodRegulation(String companyId, String historyId, int yearStartDate, int yearEndDate,
			int yearStartMonth, int yearEndMonth) {
		super();
		this.companyId = companyId;
		this.historyId = historyId;
		this.yearStartDate = yearStartDate;
		this.yearEndDate = yearEndDate;
		this.yearStartMonth = yearStartMonth;
		this.yearEndMonth = yearEndMonth;
	}
	
	
}
