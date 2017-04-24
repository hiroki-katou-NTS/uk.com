package nts.uk.file.pr.app.export.residentialtax.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InhabitantTaxChecklistBRpData {

	/**
	 * DBD_001(住民税納付先コード)
	 */
	private String residenceTaxCode;
	
	/**
	 * DBD_002(住民税納付先名称)
	 */
	private String resiTaxAutonomy;
	
	/**
	 * DBD_003(人数)
	 */
	private String numberPeople;
	
	/**
	 * DBD_004(納付額)
	 */
	private Double value;
	
	/**
	 * CTR_001(総合計人数) 
	 */
	//private String totalNumberPeople ;
	
	/**
	 * CTR_002(総合計納付額)
	 */
	//private String totalPaymentAmount;
}
