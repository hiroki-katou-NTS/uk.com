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
public class AgreementTimeOfCompany extends AggregateRoot {

	private String companyId;

	private String basicSettingId;

	private LaborSystemtAtr laborSystemAtr;

	private AgreementOneMonthTime upperMonth;

	private AgreementOneMonthTime upperMonthAverage;

	public AgreementTimeOfCompany(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			AgreementOneMonthTime upperMonth, AgreementOneMonthTime upperMonthAverage) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.upperMonth = upperMonth;
		this.upperMonthAverage = upperMonthAverage;
	}

	public static AgreementTimeOfCompany createFromJavaType(String companyId, String basicSettingId,
			int laborSystemAtr, int upperMonth, int upperMonthAverage) {
		return new AgreementTimeOfCompany(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class),
				new AgreementOneMonthTime(upperMonth), new AgreementOneMonthTime(upperMonthAverage));
	}

}
