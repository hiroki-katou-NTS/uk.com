package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailDifferentialDto {

	private String personID;

	private String employeeCode;

	private String employeeName;

	private String itemCode;

	private String itemName;

	private int categoryAtr;

	private String categoryAtrName;

	private BigDecimal comparisonValue1;

	private BigDecimal comparisonValue2;

	private BigDecimal valueDifference;

	private String reasonDifference;

	private String registrationStatus1;

	private String registrationStatus2;

	private int confirmedStatus;
}
