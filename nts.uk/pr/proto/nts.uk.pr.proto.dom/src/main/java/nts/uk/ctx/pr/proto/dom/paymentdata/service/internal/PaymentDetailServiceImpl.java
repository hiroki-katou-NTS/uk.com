package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
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
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailService;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContractRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaidRepository;
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
	private PersonalEmploymentContractRepository personalEmploymentContractRepo;
	@Inject 
	private ItemMasterRepository itemMasterRepo;
	@Inject
	private HolidayPaidRepository holidayPaidRepo;
	
	@Override
	public Double calculatePayValue(CompanyCode companyCode, PersonId personId, int baseYearMonth) {
		double payValue = 0;
		String stmtCode = null;
		int startYearMonth;
		int endYearMonth;
		
		// get allot personal setting
		Optional<PersonalAllotSetting> personalAllotSettingOp = personalAllotSettingRepo.find(companyCode.v(), personId.v(), baseYearMonth);
		
		if (!personalAllotSettingOp.isPresent()) {
			// get allot company setting
			Optional<CompanyAllotSetting> companyAllotSettingOp = companyAllotSettingRepo.find(companyCode.v(), baseYearMonth);
			stmtCode = companyAllotSettingOp.get().getPaymentDetailCode().v();
			startYearMonth = companyAllotSettingOp.get().getStartDate().v();
			endYearMonth = companyAllotSettingOp.get().getEndDate().v();
		} else {
			stmtCode = personalAllotSettingOp.get().getPaymentDetailCode().v();
			startYearMonth = personalAllotSettingOp.get().getStartDate().v();
			endYearMonth = personalAllotSettingOp.get().getEndDate().v();
		}
		
		// get layout master
		Optional<LayoutMaster> layoutHeadOp = layoutMasterRepo.getLayout(companyCode.v(), stmtCode, startYearMonth);
		
		LayoutMaster layoutHead = layoutHeadOp.get();
		
		// get layout ctg
		List<LayoutMasterCategory> categories = layoutCategoryMasterRepo.getCategories(companyCode.v(), layoutHead.getStmtCode().v(), startYearMonth);
		
		// get layout lines
		List<LayoutMasterLine> layoutLines = layoutLineMasterRepo.getLines(companyCode.v(), startYearMonth, stmtCode);
		
		// get layout detail master
		Optional<LayoutMasterDetail> layoutDetail = layoutDetailMasterRepo.find(companyCode.v(), layoutHead.getStmtCode().v(), startYearMonth, stmtCode, 2, autoLineID);
		
		// get item
		Optional<ItemMaster> itemMaster = itemMasterRepo.getItemMaster(companyCode.v(), categoryAtr, itemCode);
		
		// get PayrollSystem
		List<PersonalEmploymentContract> personalEmploymentContractList = personalEmploymentContractRepo.find(companyCode.v(), personIdList, baseYmd);
		
		// get holiday
		List<HolidayPaid> holiday = holidayPaidRepo.find(companyCode.v(), personIdList);
		
		Map<PersonId, PersonalEmploymentContract> maps = personalEmploymentContractList.stream().collect(Collectors.toMap(PersonalEmploymentContract::getPersonId, x -> x));
		
		PersonalEmploymentContract personal = maps.get(personId);
		
		// PayrollSystem == 2 || 3 
		if (personal.isPayrollSystemDailyOrDay()) {
			payValue = getPayValueByMonthlyDaily(itemCode, holiday, payDay, payCalBasic);
		} else if (personal.isPayrollSystemDailyOrMonthly()) { 
			// PayrollSystem == 0 || 1
			payValue = getPayValueByPayrollDayHours(itemCode, holiday);
		} else {
			throw new RuntimeException("Error system");
		}
		
		return payValue;
	}
	
	/**
	 * PayrollSystem == 2 || 3 
	 * @param itemCode
	 * @param holiday
	 * @return QSTDT_PAYMENT_DETAIL.VAL
	 */
	private double getPayValueByPayrollDayHours(String itemCode, HolidayPaid holiday) {
		switch (itemCode) {
		case "F206":
			return holiday.getRemainDays().doubleValue();
			
		case "F212":
			return holiday.getRemainTime().doubleValue();
			
		default:
			return 0;
		}
		
	}
	
	/**
	 * PayrollSystem == 0 || 1
	 * @param itemCode
	 * @param holiday
	 * @param payDay
	 * @param payCalBasic
	 * @return QSTDT_PAYMENT_DETAIL.VAL
	 */
	private double getPayValueByMonthlyDaily(String itemCode, HolidayPaid holiday, PaymentDateMaster payDay, PaymentCalculationBasicInformation payCalBasic) {
		// 	get ItemCode in CCAST_BASIC_CALC
		switch (itemCode) {
		case "F206":
			return holiday.getRemainDays().doubleValue();
			
		case "F212":
			return holiday.getRemainTime().doubleValue();
			
		case "F201":
			return payDay.getNeededWorkDay();
			
		case "F202":
			return payDay.getNeededWorkDay();
			
		case "F203":
			return payDay.getNeededWorkDay() * payCalBasic.getBaseHours().intValue();
				
		default:
			return 0;
		}
		
	}

}
