package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailService;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.shr.com.primitive.PersonId;

@RequestScoped
public class PaymentDetailServiceImpl implements PaymentDetailService {
	
	@Inject
	private PersonalAllotSettingRepository personalAllotSettingRepo;
	@Inject
	private CompanyAllotSettingRepository companyAllotSettingRepo;
	@Inject
	private LayoutMasterRepository layoutMasterRepo;
	@Inject
	private LayoutMasterDetailRepository layoutDetailMasterRepo;
	@Inject
	private LayoutMasterLineRepository layoutLineMasterRepo;
	@Inject
	private LayoutMasterCategoryRepository layoutCategoryMasterRepo;
	@Inject 
	private ItemMasterRepository itemMasterRepo;
	@Inject
	private PaymentDateMasterRepository payDateMasterRepo;
	
	@Override
	public Map<CategoryAtr, DetailItem> calculatePayValue(
			String companyCode, PersonId personId, int baseYearMonth, HolidayPaid holiday, PersonalEmploymentContract employmentContract, PaymentCalculationBasicInformation payCalBasicInfo) {
		Map<CategoryAtr, DetailItem> payDetail = new HashMap<>();
		// get allot personal setting
		PersonalAllotSetting personalAllotSetting = getPersonalAllotSetting(companyCode, personId.v(), baseYearMonth);
		
		String stmtCode = personalAllotSetting.getPaymentDetailCode().v();
		int startYearMonth = personalAllotSetting.getStartDate().v();
		int endYearMonth = personalAllotSetting.getEndDate().v();
		
		// get layout master
		LayoutMaster layoutHead  = layoutMasterRepo.getLayout(companyCode, stmtCode, startYearMonth).get();
		
		// get layout category
		List<LayoutMasterCategory> categories = layoutCategoryMasterRepo.getCategories(companyCode, stmtCode, startYearMonth);
		
		// get layout lines
		List<LayoutMasterLine> layoutLines = layoutLineMasterRepo.getLines(companyCode, stmtCode, startYearMonth);
		
		// get layout detail master
		List<LayoutMasterDetail> layoutMasterDetailList = layoutDetailMasterRepo.getDetails(companyCode, stmtCode, startYearMonth);
		
		for (LayoutMasterDetail itemLayoutMasterDetail : layoutMasterDetailList) {
			DetailItem detail = null;
			
			// get item code
			String itemCode = itemLayoutMasterDetail.getItemCode().v();
			
			// get item
			ItemMaster itemMaster = itemMasterRepo.getItemMaster(companyCode, itemLayoutMasterDetail.getCategoryAtr().value, itemCode).get();
			
//			// get pay day
//			payDateMasterRepo.find(companyCode, payBonusAtr, processingYm, sparePayAtr)
//			
//			// PayrollSystem == 2 || 3 
//			if (employmentContract.isPayrollSystemDailyOrDay() && itemLayoutMasterDetail.getCategoryAtr().value == 2) {
//				detail = getPayValueByMonthlyDaily(itemCode, holiday, payDay, payCalBasicInfo);
//			} else if (employmentContract.isPayrollSystemDailyOrMonthly()) { 
//				// PayrollSystem == 0 || 1
//				detail = getPayValueByPayrollDayHours(itemCode, holiday);
//			} else {
//				throw new RuntimeException("Error system");
//			}
			
			payDetail.put(itemLayoutMasterDetail.getCategoryAtr(), detail);
		}
		
		return payDetail;
	}
	
	/**
	 * PayrollSystem == 2 || 3 
	 * @param itemCode
	 * @param holiday
	 * @return QSTDT_PAYMENT_DETAIL.VAL
	 */
	private DetailItem getPayValueByPayrollDayHours(String itemCode, HolidayPaid holiday) {
		double value = 0;
		
		switch (itemCode) {
		case "F206":
			value = holiday.getRemainDays().doubleValue();
			
		case "F212":
			value = holiday.getRemainTime().doubleValue();
		}
		
		return new DetailItem(new ItemCode(itemCode), value, null, null, null);
	}
	
	/**
	 * PayrollSystem == 0 || 1
	 * @param itemCode
	 * @param holiday
	 * @param payDay
	 * @param payCalBasic
	 * @return QSTDT_PAYMENT_DETAIL.VAL
	 */
	private DetailItem getPayValueByMonthlyDaily(String itemCode, HolidayPaid holiday, PaymentDateMaster payDay, PaymentCalculationBasicInformation payCalBasic) {
		double value = 0;
		
		switch (itemCode) {
		case "F206":
			value = holiday.getRemainDays().doubleValue(); 
			
		case "F212":
			value =  holiday.getRemainTime().doubleValue();
			
		case "F201":
			value =  payDay.getNeededWorkDay();
			
		case "F202":
			value =  payDay.getNeededWorkDay();
			
		case "F203":
			value =  payDay.getNeededWorkDay() * payCalBasic.getBaseHours().intValue();
		}
		
		return new DetailItem(new ItemCode(itemCode), value, null, null, null);
	}
	
	/**
	 * Get allot setting by personal
	 * @return
	 */
	private PersonalAllotSetting getPersonalAllotSetting(String companyCode, String personId, int baseYearMonth) {
		PersonalAllotSetting result = null;
		
		Optional<PersonalAllotSetting> personalAllotSettingOp = personalAllotSettingRepo.find(companyCode, personId, baseYearMonth);
		
		if (!personalAllotSettingOp.isPresent()) {
			// get allot company setting
			CompanyAllotSetting companyAllotSetting = companyAllotSettingRepo.find(companyCode, baseYearMonth).get();
			
			result = new PersonalAllotSetting(
					new CompanyCode(companyCode), 
					new PersonId(personId), 
					companyAllotSetting.getStartDate(),
					companyAllotSetting.getEndDate(), 
					companyAllotSetting.getBonusDetailCode(), 
					companyAllotSetting.getPaymentDetailCode());
		} else {
			result = personalAllotSettingOp.get();
		}
		
		return result;
	}

}
