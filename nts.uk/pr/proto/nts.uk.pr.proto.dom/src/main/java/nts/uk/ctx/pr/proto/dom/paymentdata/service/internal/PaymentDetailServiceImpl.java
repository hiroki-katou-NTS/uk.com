package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
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
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFeeRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWageRepository;

@RequestScoped
public class PaymentDetailServiceImpl implements PaymentDetailService {

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
	@Inject
	private PersonalCommuteFeeRepository personalCommuteRepo;

	@Override
	public Map<CategoryAtr, List<DetailItem>> calculatePayValue(PaymentDetailParam param) {
		Map<CategoryAtr, List<DetailItem>> payDetail = new HashMap<>();
		
		// get personal allot setting
		String stmtCode = param.getPersonalAllotSetting().getPaymentDetailCode().v();
		int startYearMonth = param.getPersonalAllotSetting().getStartDate().v();

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
		PersonalCommuteFee commute = personalCommuteRepo.find(param.getCompanyCode(), param.getPersonId().v(), param.getCurrentProcessingYearMonth()).get();
					
		for (LayoutMasterDetail itemLayoutMasterDetail : layoutMasterDetailList) {
			List<DetailItem> details = new ArrayList<>();
			DetailItem detail = null;

			// get item code
			String itemCode = itemLayoutMasterDetail.getItemCode().v();

			// get item
			ItemMaster itemMaster = itemMasterRepo
					.getItemMaster(param.getCompanyCode(), itemLayoutMasterDetail.getCategoryAtr().value, itemCode)
					.get();
			
			//
			// LAYOUT_DETAIL with CTR_ATR = 2
			//
			
			if (itemLayoutMasterDetail.isCategoryPersonalTime()) {
				// PayrollSystem == 2 || 3
				if (param.getEmploymentContract().isPayrollSystemDailyOrDay()) {
					detail = getPayValueByMonthlyDaily(itemCode, param.getHoliday(), param.getPaymentDateMaster(),
							param.getPayCalBasicInfo());
				} else if (param.getEmploymentContract().isPayrollSystemDailyOrMonthly()) {
					// PayrollSystem == 0 || 1
					detail = getPayValueByPayrollDayHours(itemCode, param.getHoliday());
				}
			}
			
			//
			// LAYOUT_DETAIL with CTR_ATR = 0
			//
			
			if (itemLayoutMasterDetail.isCategoryPayment()) {
				// get calculate method
				if (itemLayoutMasterDetail.isCalMethodManualOrFormulaOrWageOrCommon()) {
					detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), 0.0, null, null, null);
				} else if (itemLayoutMasterDetail.isCalMethodPesonalInfomation()) {
					if (itemMaster.getItemCode() == itemLayoutMasterDetail.getItemCode()) {
						// calculate pay value by tax
						detail = getPayValueByTax(param, startYearMonth, itemLayoutMasterDetail, detail, itemCode,
								itemMaster, commute);
					}
				}
			}
			
			//
			// LAYOUT_DETAIL with CTR_ATR = 1
			//
			
			if (itemLayoutMasterDetail.isCategoryDeduction()) {
				// sum
				//double sumCommuteAllowance = commute.sumCommuteAllowance(param.getCurrentProcessingYearMonth());
				if (itemLayoutMasterDetail.isCalMethodPesonalInfomation()) {
					// get personal wage
					PersonalWage personalWage = personalWageRepo.find(param.getCompanyCode(), param.getPersonId().v(), itemMaster.getCategoryAtr().value, itemCode, param.getCurrentProcessingYearMonth()).get();
					detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), personalWage.getWageValue().doubleValue(), null, null, null);
				} else if (itemLayoutMasterDetail.isCalMethodManualOrFormulaOrWageOrCommonOrPaymentCanceled()) {
					detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), 0.0, null, null, null);
				} else {
					throw new RuntimeException("Error system");
				}
			}
			
			//
			// LAYOUT_DETAIL with CTR_ATR = 3 || 9
			//
			
			if (itemLayoutMasterDetail.isCategoryArticlesOrOther()) {
				detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), 0.0, null, null, null);
			}

			// check exists
			if (payDetail.containsKey(itemLayoutMasterDetail.getCategoryAtr())) {
				List<DetailItem> detailItems = payDetail.get(itemLayoutMasterDetail.getCategoryAtr());
				detailItems.add(detail);
				payDetail.remove(itemLayoutMasterDetail.getCategoryAtr());
			} 
			
			payDetail.put(itemLayoutMasterDetail.getCategoryAtr(), details);
		}

		return payDetail;
	}

	/**
	 * Calculate value by tax
	 * @param param
	 * @param startYearMonth
	 * @param itemLayoutMasterDetail
	 * @param detail
	 * @param itemCode
	 * @param itemMaster
	 * @return pay detail
	 */
	private DetailItem getPayValueByTax(PaymentDetailParam param, int startYearMonth,
			LayoutMasterDetail itemLayoutMasterDetail, DetailItem detail, String itemCode, ItemMaster itemMaster, PersonalCommuteFee commute) {
		// check tax attribute
		if (itemMaster.isTaxTaxationOrTaxFreeLimitOrTaxFreeUnLimit()) { // tax_atr = 0 || 1 || 2
			// get personal wage
			PersonalWage personalWage = personalWageRepo.find(param.getCompanyCode(), param.getPersonId().v(), itemMaster.getCategoryAtr().value, itemCode, param.getCurrentProcessingYearMonth()).get();
			
			detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), personalWage.getWageValue().doubleValue(), null, null, null);
		} else if (itemMaster.isTaxCommutingoCostOrCommutingExpense()) { // tax_atr = 3 || 4
			
			// check commute_attr
			if (CommuteAtr.TRANSPORTATION_EQUIPMENT == itemLayoutMasterDetail.getCommuteAtr()) { // = 0
				detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), commute.sumCommuteAllowance(CommuteAtr.TRANSPORTATION_EQUIPMENT, param.getCurrentProcessingYearMonth()), null, null, null);
			} else { // = 1
				detail = new DetailItem(itemLayoutMasterDetail.getItemCode(), commute.sumCommuteAllowance(CommuteAtr.TRANSPORTTION_FACILITIES, param.getCurrentProcessingYearMonth()), null, null, null);				
			}
			
		} else {
			throw new RuntimeException("Error system");
		}
		
		return detail;
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

}
