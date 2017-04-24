package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailDifferentialDto {

	private String employeeCode;

	private String employeeName;

	private String itemCode;

	private String itemName;

	private int categoryAtr;

	private BigDecimal comparisonValue1;

	private BigDecimal comparisonValue2;

	private BigDecimal valueDifference;

	private String reasonDifference;

	private String registrationStatus1;

	private String registrationStatus2;

	private int confirmedStatus;

	public static DetailDifferentialDto createFromJavaType(DetailDifferential domain) {
		return new DetailDifferentialDto(domain.getEmployeeCode().v(), domain.getEmployeeName().v(),
				domain.getItemCode().v(), domain.getItemName().v(), domain.getCategoryAtr().value,
				domain.getComparisonValue1().v(), domain.getComparisonValue2().v(), domain.getValueDifference().v(),
				domain.getReasonDifference().v(), domain.getRegistrationStatus1().name,
				domain.getRegistrationStatus2().name, domain.getConfirmedStatus().value);
	}
}
