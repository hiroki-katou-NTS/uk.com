package nts.uk.file.pr.app.export.residentialtax.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentTaxReportData {
	private String residenceTaxCode;
	private String resiTaxAutonomy;
	private String companyAccountNo;
	private String registeredName;
	private String companySpecifiedNo;
	
	/**
	 * DBD_006
	 * value + taxPayrollMny
	 */
	private String dbd006;
//	private String value;
//	private String taxPayrollMny;
	
//	private String cityTaxMny1;
//	private String prefectureTaxMny1;
//	private String taxRetirementMny;
	/**
	 * DBD_007
	 * cityTaxMny1 + prefectureTaxMny1 + taxRetirementMny
	 */
	private String dbd007;
	
	private String postal;
	private String address1;
	private String address2;
	private String companyName;
	private String cordinatePostalCode;
	private String cordinatePostOffice;
//	private String residenceCode;
//	private String headCount;
	/**
	 * residenceCode + headCount
	 */ 
    private String dbd014;
	private String actualRecieveMny;
	private String cityTaxMny2;
	private String prefectureTaxMny2;
	private String taxOverdueMny;
	private String taxDemandChargeMny;
	private String dueDate;
}
