package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.standardday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.paymentdata.ProcessingNo;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.standardday.*;

@AllArgsConstructor
public class StandardDay extends AggregateRoot {

	@Getter
	private CompanyCode companyCode;

	@Getter
	private ProcessingNo processingNo;

	@Getter
	private SocialInsStdYearAtr socialInsStdYearAtr;

	@Getter
	private SocialInsStdMon socialInsStdMon;

	@Getter
	private SocialInsStdDay socialInsStdDay;

	@Getter
	private IncometaxStdYearAtr incometaxStdYearAtr;

	@Getter
	private IncometaxStdMon incometaxStdMon;

	@Getter
	private IncometaxStdDay incometaxStdDay;

	@Getter
	private EmpInsStdMon empInsStdMon;

	@Getter
	private EmpInsStdDay empInsStdDay;

	public static StandardDay createSimpleFromJavaType(String companyCode, int processingNo, int socialInsStdYearAtr,
			int socialInsStdMon, int socialInsStdDay, int incometaxStdYearAtr, int incometaxStdMon, int incometaxStdDay,
			int empInsStdMon, int empInsStdDay) {

		return new StandardDay(new CompanyCode(companyCode), new ProcessingNo(processingNo),
				EnumAdaptor.valueOf(socialInsStdYearAtr, SocialInsStdYearAtr.class),
				new SocialInsStdMon(socialInsStdMon), new SocialInsStdDay(socialInsStdDay),
				EnumAdaptor.valueOf(incometaxStdYearAtr, IncometaxStdYearAtr.class),
				new IncometaxStdMon(incometaxStdMon), new IncometaxStdDay(incometaxStdDay),
				new EmpInsStdMon(empInsStdMon), new EmpInsStdDay(empInsStdDay));
	}
}
