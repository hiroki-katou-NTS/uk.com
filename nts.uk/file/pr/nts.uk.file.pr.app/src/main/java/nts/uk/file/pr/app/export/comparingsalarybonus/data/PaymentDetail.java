package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.Value;
@Value
public class PaymentDetail {
	String companyCode;
	String personId;
	int processingNo;
	int payBonusAtr;
	int processingYm;
	int sparePayAtr;
	int ctgAtr;
	String itemCode;
	int val;
	int correctFlg;
	int taxAtr;
	int limitMny;
	int socialInsAtr;
	int laborInsAtr;
	int fixPayAtr;
	int deductAtr;
	int itemAtr;
	int commuAllowTaxImpose;
	int commuAllowMonth;
	int commuAllowFraction;
	int printLinePos;
	int itemPosColumn;
	/**
	 * @param companyCode
	 * @param personId
	 * @param processingNo
	 * @param payBonusAtr
	 * @param processingYm
	 * @param sparePayAtr
	 * @param ctgAtr
	 * @param itemCode
	 * @param val
	 * @param correctFlg
	 * @param taxAtr
	 * @param limitMny
	 * @param socialInsAtr
	 * @param laborInsAtr
	 * @param fixPayAtr
	 * @param deductAtr
	 * @param itemAtr
	 * @param commuAllowTaxImpose
	 * @param commuAllowMonth
	 * @param commuAllowFraction
	 * @param printLinePos
	 * @param itemPosColumn
	 */
	public PaymentDetail(String companyCode, String personId, int processingNo, int payBonusAtr, int processingYm,
			int sparePayAtr, int ctgAtr, String itemCode, int val, int correctFlg, int taxAtr, int limitMny,
			int socialInsAtr, int laborInsAtr, int fixPayAtr, int deductAtr, int itemAtr, int commuAllowTaxImpose,
			int commuAllowMonth, int commuAllowFraction, int printLinePos, int itemPosColumn) {
		super();
		this.companyCode = companyCode;
		this.personId = personId;
		this.processingNo = processingNo;
		this.payBonusAtr = payBonusAtr;
		this.processingYm = processingYm;
		this.sparePayAtr = sparePayAtr;
		this.ctgAtr = ctgAtr;
		this.itemCode = itemCode;
		this.val = val;
		this.correctFlg = correctFlg;
		this.taxAtr = taxAtr;
		this.limitMny = limitMny;
		this.socialInsAtr = socialInsAtr;
		this.laborInsAtr = laborInsAtr;
		this.fixPayAtr = fixPayAtr;
		this.deductAtr = deductAtr;
		this.itemAtr = itemAtr;
		this.commuAllowTaxImpose = commuAllowTaxImpose;
		this.commuAllowMonth = commuAllowMonth;
		this.commuAllowFraction = commuAllowFraction;
		this.printLinePos = printLinePos;
		this.itemPosColumn = itemPosColumn;
	}


}
