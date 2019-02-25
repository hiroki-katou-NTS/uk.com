package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfEmployment extends AggregateRoot {

	private String companyId;

	private String basicSettingId;

	private LaborSystemtAtr laborSystemAtr;

	private String employmentCategoryCode;
	
	private AgreementOneMonthTime upperMonth;

	private AgreementOneMonthTime upperMonthAverage;

	public AgreementTimeOfEmployment(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			String employmentCategoryCode, AgreementOneMonthTime upperMonth, AgreementOneMonthTime upperMonthAverage) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.employmentCategoryCode = employmentCategoryCode;
		this.upperMonth = upperMonth;
		this.upperMonthAverage = upperMonthAverage;
	}

	public static AgreementTimeOfEmployment createJavaType(String companyId, String basicSettingId,
			int laborSystemAtr, String employmentCategoryCode, int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfEmployment(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class), employmentCategoryCode,
				new AgreementOneMonthTime(upperMonth), new AgreementOneMonthTime(upperMonthAverage));
	}
}
