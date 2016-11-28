package nts.uk.ctx.pr.proto.dom.paymentdata.service;

import java.util.List;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.shr.com.primitive.PersonId;

@Value
public class PaymentDetailParam {
	String companyCode;
	PersonId personId;
	YearMonth baseYearMonth;
	YearMonth currentProcessingYearMonth;
	HolidayPaid holiday;
	PersonalEmploymentContract employmentContract;
	PaymentCalculationBasicInformation payCalBasicInfo;
	PaymentDateMaster paymentDateMaster;
	PersonalAllotSetting personalAllotSetting;
	List<LayoutMasterDetail> layoutMasterDetailList;
	
	public int getStartYearMonth() {
		return personalAllotSetting.getStartDate().v();
	}
}
