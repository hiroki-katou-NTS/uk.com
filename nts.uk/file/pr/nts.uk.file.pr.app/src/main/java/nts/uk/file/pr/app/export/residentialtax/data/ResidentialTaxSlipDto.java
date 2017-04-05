package nts.uk.file.pr.app.export.residentialtax.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ResidentialTaxSlipDto {
	private String resiTaxCode;
	private int yearMonth;
	private int taxPayrollMny;
	private int taxRetirementMny;
	private int taxOverdueMny;
	private int taxDemandChargeMny;
	private String address;
	private GeneralDate dueDate;
	private int headCount;
	private int retirementBonusAmount;
	private int cityTaxMny;
	private int prefectureTaxMny;
}
