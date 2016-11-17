package nts.uk.ctx.pr.proto.dom.paymentdata.service;

import lombok.Data;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFee;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.shr.com.primitive.PersonId;

@Data
public class PaymentDetailParam {
	private String companyCode;
	private PersonId personId;
	private int baseYearMonth;
	private HolidayPaid holiday;
	private PersonalEmploymentContract employmentContract;
	private PaymentCalculationBasicInformation payCalBasicInfo;
	private PaymentDateMaster paymentDateMaster;
	private PersonalWage personalWage;
	private PersonalCommuteFee personalCommute;
	
	public PaymentDetailParam(String companyCode, PersonId personId, int baseYearMonth) {
		super();
		this.companyCode = companyCode;
		this.personId = personId;
		this.baseYearMonth = baseYearMonth;
	}
	
}
