package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearservicecom;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.YearServiceSet;

/**
 * 
 * @author yennth
 *
 */
@Getter
public class YearServiceCom extends AggregateRoot {
	/** 会社ID **/
	private String companyId;
	/** コード **/
	private String specialHolidayCode;
	/** 勤続年数 **/
	private int lengthServiceYearAtr;
	private List<YearServiceSet> yearServiceSets;

	public YearServiceCom(String companyId, String specialHolidayCode, int lengthServiceYearAtr,
			List<YearServiceSet> yearServiceSets) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.lengthServiceYearAtr = lengthServiceYearAtr;
		this.yearServiceSets = yearServiceSets;
	}

	public static YearServiceCom createFromJavaType(String companyId, String specialHolidayCode,
			int lengthServiceYearAtr, List<YearServiceSet> yearServiceSets) {
		return new YearServiceCom(companyId, specialHolidayCode, lengthServiceYearAtr, yearServiceSets);
	}

	@Override
	public void validate() {
		super.validate();
	}

}
