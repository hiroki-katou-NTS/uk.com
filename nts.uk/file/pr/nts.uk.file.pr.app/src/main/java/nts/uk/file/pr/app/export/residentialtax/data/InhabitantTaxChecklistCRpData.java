package nts.uk.file.pr.app.export.residentialtax.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InhabitantTaxChecklistCRpData {
	/**
	 * DBD_001,DBD_002(社員呼称)
	 */
	private String residenceTaxCode;

	/**
	 * DBD_003(住民税納付先コード)
	 */
	private String numberPeople;

	/**
	 * DBD_004(住民税納付先名称)
	 */
	private String value;
	
	/**
	 * DBD_005(社員コード)
	 */
	private String employeeCode;
	
	/**
	 * DBD_006(氏名)
	 */
	private String name;
	
	/**
	 * DBD_007(納付額)
	 */
	private String paymentAmount;

	/**
	 * CTR_001(合計人数)
	 */
	private String sumNumberPeople;

	/**
	 * CTR_002(合計納付額)
	 */
	private String sumPaymentAmount;
	
	/**
	 * CTR_003(総合計人数)
	 */
	private String totalNumberPeople;
	
	/**
	 * CTR_004(総合計納付額)
	 */
	private String totalPaymentAmount;
}
