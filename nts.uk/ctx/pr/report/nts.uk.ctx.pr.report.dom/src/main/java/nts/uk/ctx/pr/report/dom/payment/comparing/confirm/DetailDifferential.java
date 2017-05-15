package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.CategoryAtr;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ItemCode;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ItemName;

@Getter
@AllArgsConstructor
public class DetailDifferential extends AggregateRoot {

	private String companyCode;

	private EmployeeCode employeeCode;

	private EmployeeName employeeName;

	private ItemCode itemCode;

	private ItemName itemName;

	private CategoryAtr categoryAtr;

	private ComparisonValue comparisonValue1;

	private ComparisonValue comparisonValue2;

	private ValueDifference valueDifference;

	private ReasonDifference reasonDifference;

	private RegistrationStatus registrationStatus1;

	private RegistrationStatus registrationStatus2;

	private ConfirmedStatus confirmedStatus;

	public static DetailDifferential createFromJavaType(String companyCode, String employeeCode, String employeeName,
			String itemCode, String itemName, int categoryAtr, BigDecimal comparisonValue1, BigDecimal comparisonValue2,
			BigDecimal valueDifference, String reasonDifference, int registrationStatus1, int registrationStatus2,
			int confirmedStatus) {
		return new DetailDifferential(companyCode, new EmployeeCode(employeeCode), new EmployeeName(employeeName),
				new ItemCode(itemCode), new ItemName(itemName), EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class),
				new ComparisonValue(comparisonValue1), new ComparisonValue(comparisonValue2),
				new ValueDifference(valueDifference), new ReasonDifference(reasonDifference),
				EnumAdaptor.valueOf(registrationStatus1, RegistrationStatus.class),
				EnumAdaptor.valueOf(registrationStatus2, RegistrationStatus.class),
				EnumAdaptor.valueOf(confirmedStatus, ConfirmedStatus.class));
	}

}
