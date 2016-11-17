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
import nts.uk.ctx.pr.proto.dom.itemmaster.TaxAtr;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.CalculationMethod;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailParam;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailService;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFee;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWageRepository;
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
	private PersonalWageRepository personalWageRepo;

	@Override
	public Map<CategoryAtr, DetailItem> calculatePayValue(PaymentDetailParam param) {
		Map<CategoryAtr, DetailItem> payDetail = new HashMap<>();
		// get allot personal setting
		PersonalAllotSetting personalAllotSetting = getPersonalAllotSetting(param.getCompanyCode(),
				param.getPersonId().v(), param.getBaseYearMonth());

		String stmtCode = personalAllotSetting.getPaymentDetailCode().v();
		int startYearMonth = personalAllotSetting.getStartDate().v();

		// get layout master
		LayoutMaster layoutHead = layoutMasterRepo.getLayout(param.getCompanyCode(), stmtCode, startYearMonth).get();

		// get layout category
		List<LayoutMasterCategory> categories = layoutCategoryMasterRepo.getCategories(param.getCompanyCode(), stmtCode,
				startYearMonth);

		// get layout lines
		List<LayoutMasterLine> layoutLines = layoutLineMasterRepo.getLines(param.getCompanyCode(), stmtCode,
				startYearMonth);

		// get layout detail master
		List<LayoutMasterDetail> layoutMasterDetailList = layoutDetailMasterRepo.getDetails(param.getCompanyCode(),
				stmtCode, startYearMonth);

		// get personal commute
		PersonalCommuteFee personalCommute = param.getPersonalCommute();

		for (LayoutMasterDetail itemLayoutMasterDetail : layoutMasterDetailList) {
			DetailItem detail = null;

			// get item code
			String itemCode = itemLayoutMasterDetail.getItemCode().v();

			// get item
			ItemMaster itemMaster = itemMasterRepo
					.getItemMaster(param.getCompanyCode(), itemLayoutMasterDetail.getCategoryAtr().value, itemCode)
					.get();

			// PayrollSystem == 2 || 3
			if (param.getEmploymentContract().isPayrollSystemDailyOrDay()
					&& itemLayoutMasterDetail.getCategoryAtr().value == 2) {
				detail = getPayValueByMonthlyDaily(itemCode, param.getHoliday(), param.getPaymentDateMaster(),
						param.getPayCalBasicInfo());
			} else if (param.getEmploymentContract().isPayrollSystemDailyOrMonthly()
					&& itemLayoutMasterDetail.getCategoryAtr().value == 2) {
				// PayrollSystem == 0 || 1
				detail = getPayValueByPayrollDayHours(itemCode, param.getHoliday());
			}
			
			// get calculate method
			CalculationMethod calculationMethod = itemLayoutMasterDetail.getCalculationMethod();
			if ((calculationMethod == CalculationMethod.MANUAL_ENTRY 
					|| calculationMethod == CalculationMethod.FORMULA 
					|| calculationMethod == CalculationMethod.WAGE_TABLE 
					|| calculationMethod == CalculationMethod.COMMON_AMOUNT_MONEY)
				 && CategoryAtr.PAYMENT == itemLayoutMasterDetail.getCategoryAtr()) {
				detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), 0.0, null, null, null);
			} else if (calculationMethod == CalculationMethod.PERSONAL_INFORMATION) {
				if (itemMaster.getCategoryAtr() == CategoryAtr.PAYMENT && itemMaster.getItemCode() == itemLayoutMasterDetail.getItemCode()) {
					// check tax attribute
					if (itemMaster.getTaxAtr() == TaxAtr.TAXATION || itemMaster.getTaxAtr() == TaxAtr.TAX_FREE_LIMIT || itemMaster.getTaxAtr() == TaxAtr.TAX_FREE_UN_LIMIT) {
						// get personal wage
						PersonalWage personalWage = personalWageRepo.find(param.getCompanyCode(), param.getPersonId().v(), itemMaster.getCategoryAtr().value, itemCode, startYearMonth).get();
						
						detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), personalWage.getWageValue().doubleValue(), null, null, null);
					} else if (itemMaster.getTaxAtr() == TaxAtr.COMMUTING_COST || itemMaster.getTaxAtr() == TaxAtr.COMMUTING_EXPENSE) {
						//
						
						
						
						
					} else {
						throw new RuntimeException("Error system");
					}
				}
			}

			payDetail.put(itemLayoutMasterDetail.getCategoryAtr(), detail);
		}

		return payDetail;
	}

	/**
	 * PayrollSystem == 2 || 3
	 * 
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
	 * 
	 * @param itemCode
	 * @param holiday
	 * @param payDay
	 * @param payCalBasic
	 * @return QSTDT_PAYMENT_DETAIL.VAL
	 */
	private DetailItem getPayValueByMonthlyDaily(String itemCode, HolidayPaid holiday, PaymentDateMaster payDay,
			PaymentCalculationBasicInformation payCalBasic) {
		double value = 0;

		switch (itemCode) {
		case "F206":
			value = holiday.getRemainDays().doubleValue();

		case "F212":
			value = holiday.getRemainTime().doubleValue();

		case "F201":
			value = payDay.getNeededWorkDay().doubleValue();

		case "F202":
			value = payDay.getNeededWorkDay().doubleValue();

		case "F203":
			value = payDay.getNeededWorkDay().doubleValue() * payCalBasic.getBaseHours().doubleValue();
		}

		return new DetailItem(new ItemCode(itemCode), value, null, null, null);
	}

	/**
	 * Get allot setting by personal
	 * 
	 * @return
	 */
	private PersonalAllotSetting getPersonalAllotSetting(String companyCode, String personId, int baseYearMonth) {
		PersonalAllotSetting result = null;

		Optional<PersonalAllotSetting> personalAllotSettingOp = personalAllotSettingRepo.find(companyCode, personId,
				baseYearMonth);

		if (!personalAllotSettingOp.isPresent()) {
			// get allot company setting
			CompanyAllotSetting companyAllotSetting = companyAllotSettingRepo.find(companyCode, baseYearMonth).get();

			result = new PersonalAllotSetting(new CompanyCode(companyCode), new PersonId(personId),
					companyAllotSetting.getStartDate(), companyAllotSetting.getEndDate(),
					companyAllotSetting.getBonusDetailCode(), companyAllotSetting.getPaymentDetailCode());
		} else {
			result = personalAllotSettingOp.get();
		}

		return result;
	}

}
