package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.PayBonusAttribute;
import nts.uk.ctx.pr.proto.dom.enums.SparePayAttribute;
import nts.uk.ctx.pr.proto.dom.paymentdatainput.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdatainput.ProcessingNo;
import nts.uk.shr.com.primitive.PersonId;

/**
 * Create data payroll.
 */
public class PaymentCreateData {
	private CompanyCode companyCode;
	private PersonId personId;
	private ProcessingNo processingNo;
	private PayBonusAttribute payBonusAttribute;
	private int processingYM;
	private SparePayAttribute sparePayAttribute;
	
	/**
	 * Set Company Code
	 * @param companyCode
	 * @return
	 */
	public PaymentCreateData withCompanyCode(String companyCode) {
		this.companyCode = new CompanyCode(companyCode);
		return this;
	}
	
	/**
	 * Set Person Id
	 * @param personId
	 * @return
	 */
	public PaymentCreateData withPersonId(String personId) {
		this.personId = new PersonId(personId);
		return this;
	}
	
	/**
	 * Set Processing No
	 * @param processingNo
	 * @return
	 */
	public PaymentCreateData withProcessingNo(int processingNo) {
		this.processingNo = new ProcessingNo(processingNo);
		return this;
	}
	
	/**
	 * Set Pay Bonus Attribute
	 * @param payBonusAttribute
	 * @return
	 */
	public PaymentCreateData withPayBonusAttribute(int payBonusAttribute) {
		this.payBonusAttribute = PayBonusAttribute.valueOf(payBonusAttribute);
		return this;
	}
	
	/**
	 * Set processing year month
	 * @param processingYM
	 * @return
	 */
	public PaymentCreateData withProcessingYM(int processingYM) {
		this.processingYM = processingYM;
		return this;
	}
	
	/**
	 * Set SparePayAttribute
	 * @param sparePayAttribute
	 * @return
	 */
	public PaymentCreateData withSparePayAttribute(int sparePayAttribute) {
		this.sparePayAttribute = SparePayAttribute.valueOf(sparePayAttribute);
		return this;
	}
	
	/**
	 * Create data
	 */
	public void createData() {
		Payment payment = new Payment(this.companyCode, this.personId, this.processingNo, this.payBonusAttribute, this.processingYM, this.sparePayAttribute);
	}
	
	
}
