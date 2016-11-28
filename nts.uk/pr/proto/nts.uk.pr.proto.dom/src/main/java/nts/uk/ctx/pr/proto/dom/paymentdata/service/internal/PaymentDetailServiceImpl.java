package nts.uk.ctx.pr.proto.dom.paymentdata.service.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
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
	private ItemMasterRepository itemMasterRepo;
	@Inject
	private PersonalWageRepository personalWageRepo;
	@Inject
	private PersonalCommuteFeeRepository personalCommuteRepo;

	@Override
	public Map<CategoryAtr, List<DetailItem>> calculatePayValue(PaymentDetailParam param) {
		
		// get personal allot setting
		String stmtCode = param.getPersonalAllotSetting().getPaymentDetailCode().v();

		// get layout detail master
		List<LayoutMasterDetail> layoutMasterDetailList = layoutDetailMasterRepo.getDetails(param.getCompanyCode(),
				stmtCode, param.getStartYearMonth());
		
		// get personal commute
		PersonalCommuteFee commute = personalCommuteRepo.find(
				param.getCompanyCode(), param.getPersonId().v(), param.getCurrentProcessingYearMonth().v())
					.orElseThrow(() -> new RuntimeException("PersonalCommuteFee not found"));
		
		// LAYOUT_DETAIL with CTR_ATR = 0
		val detailsOfCategoryPayment = layoutMasterDetailList.stream()
			.filter(l -> l.isCategoryPayment())
			.map(l -> createDetailOfCategoryPayment(param, l, commute))
			.collect(Collectors.toList());
		
		// LAYOUT_DETAIL with CTR_ATR = 1
		val detailsOfCategoryDeduction = layoutMasterDetailList.stream()
				.filter(l -> l.isCategoryDeduction())
				.map(l -> createDetailOfCategoryDeduction(param, l))
				.collect(Collectors.toList());
		
		// LAYOUT_DETAIL with CTR_ATR = 2
		val detailsOfCategoryPersonalTime = layoutMasterDetailList.stream()
				.filter(l -> l.isCategoryPersonalTime())
				.map(l -> createDetailOfCategoryPersonalTime(param, l))
				.collect(Collectors.toList());
		
		// LAYOUT_DETAIL with CTR_ATR = 3
		val detailsOfCategoryArticles = layoutMasterDetailList.stream()
				.filter(l -> l.isCategoryArticles())
				.map(l -> createDetailOfCategoryArticlesOrOther(l))
				.collect(Collectors.toList());
		
		// LAYOUT_DETAIL with CTR_ATR = 9
		val detailsOfCategoryOther = layoutMasterDetailList.stream()
				.filter(l -> l.isCategoryOther())
				.map(l -> createDetailOfCategoryArticlesOrOther(l))
				.collect(Collectors.toList());
		
		Map<CategoryAtr, List<DetailItem>> payDetail = new HashMap<>();
		payDetail.put(CategoryAtr.PAYMENT, detailsOfCategoryPayment);
		payDetail.put(CategoryAtr.DEDUCTION, detailsOfCategoryDeduction);
		payDetail.put(CategoryAtr.PERSONAL_TIME, detailsOfCategoryPersonalTime);
		payDetail.put(CategoryAtr.ARTICLES, detailsOfCategoryArticles);
		payDetail.put(CategoryAtr.OTHER, detailsOfCategoryOther);
		
		return payDetail;
	}
	
	/** Create data detail with Layout.Category = 0 **/
	private DetailItem createDetailOfCategoryPayment(
			PaymentDetailParam param,
			LayoutMasterDetail layout,
			PersonalCommuteFee commute) {
		
		// get calculate method
		if (layout.isCalMethodManualOrFormulaOrWageOrCommon()) {
			return DetailItem.createDataDetailItem(layout.getItemCode(), 0.0, layout.getCategoryAtr());
		} else if (layout.isCalMethodPesonalInfomation()) {
			
			ItemMaster itemMaster = getItemMaster(param, layout);
			
			// calculate pay value by tax
			return getPayValueByTax(param, layout, layout.getItemCode().v(),
					itemMaster, commute);
		} else {
			throw new RuntimeException("invalid layout");
		}
	}

	/** Create data detail with Layout.Category = 1 **/
	private DetailItem createDetailOfCategoryDeduction(
			PaymentDetailParam param,
			LayoutMasterDetail layout) {
		// sum
		//double sumCommuteAllowance = commute.sumCommuteAllowance(param.getCurrentProcessingYearMonth());
		if (layout.isCalMethodPesonalInfomation()) {
			ItemMaster itemMaster = getItemMaster(param, layout);
			// get personal wage
			PersonalWage personalWage = personalWageRepo.find(
					param.getCompanyCode(),
					param.getPersonId().v(),
					itemMaster.getCategoryAtr().value,
					this.getPersonalWageCode(itemMaster.getItemCode().v()),
					param.getCurrentProcessingYearMonth().v()).get();
			return DetailItem.createDataDetailItem(layout.getItemCode(), personalWage.getWageValue().doubleValue(), layout.getCategoryAtr());
		} else if (layout.isCalMethodManualOrFormulaOrWageOrCommonOrPaymentCanceled()) {
			return DetailItem.createDataDetailItem(layout.getItemCode(), 0.0, layout.getCategoryAtr());
		} else {
			throw new RuntimeException("システムエラー");
		}
	}
	
	/** Create data detail with Layout.Category = 2 **/
	private DetailItem createDetailOfCategoryPersonalTime(
			PaymentDetailParam param,
			LayoutMasterDetail layout) {
		
		double value;
		// PayrollSystem == 2 || 3
		if (param.getEmploymentContract().isPayrollSystemDailyOrDay()) {
			value = getPayValueByMonthlyDaily(layout.getItemCode(), param.getHoliday(), param.getPaymentDateMaster(),
					param.getPayCalBasicInfo());
			
		} else if (param.getEmploymentContract().isPayrollSystemDailyOrMonthly()) {
			// PayrollSystem == 0 || 1
			value = getPayValueByPayrollDayHours(layout.getItemCode(), param.getHoliday());
		} else {
			throw new RuntimeException("システムエラー");
		}
		
		return DetailItem.createDataDetailItem(layout.getItemCode(), value, layout.getCategoryAtr());
	}
	
	/** Create data detail with Layout.Category = 3 || 9 **/
	private DetailItem createDetailOfCategoryArticlesOrOther(LayoutMasterDetail layout) {
		return DetailItem.createDataDetailItem(layout.getItemCode(), 0.0, layout.getCategoryAtr());
	}

	/** Get item master **/
	private ItemMaster getItemMaster(PaymentDetailParam param, LayoutMasterDetail layout) {
		ItemMaster itemMaster = itemMasterRepo
				.getItemMaster(param.getCompanyCode(), layout.getCategoryAtr().value, layout.getItemCode().v())
				.get();
		return itemMaster;
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
	private DetailItem getPayValueByTax(PaymentDetailParam param,
			LayoutMasterDetail itemLayoutMasterDetail, String itemCode, ItemMaster itemMaster, PersonalCommuteFee commute) {
		
		// check tax attribute
		if (itemMaster.isTaxTaxationOrTaxFreeLimitOrTaxFreeUnLimit()) { // tax_atr = 0 || 1 || 2
			// get personal wage
			PersonalWage personalWage = personalWageRepo.find(
					param.getCompanyCode(),
					param.getPersonId().v(),
					itemMaster.getCategoryAtr().value,
					getPersonalWageCode(itemCode),
					param.getCurrentProcessingYearMonth().v()).get();
			
			return DetailItem.createDataDetailItem(itemLayoutMasterDetail.getItemCode(), personalWage.getWageValue().doubleValue(), itemLayoutMasterDetail.getCategoryAtr());
		} else if (itemMaster.isTaxCommutingoCostOrCommutingExpense()) { // tax_atr = 3 || 4
			
			return DetailItem.createDataDetailItem(
					itemLayoutMasterDetail.getItemCode(),
					commute.sumCommuteAllowance(
							itemLayoutMasterDetail.getCommuteAtr(),
							param.getCurrentProcessingYearMonth().v()),
					itemLayoutMasterDetail.getCategoryAtr());
			
		} else {
			throw new RuntimeException("Error system");
		}
	}

	/**
	 * PayrollSystem == 2 || 3
	 * 
	 * @param itemCode
	 * @param holiday
	 * @return QSTDT_PAYMENT_DETAIL.VAL
	 */
	private double getPayValueByPayrollDayHours(ItemCode itemCode, HolidayPaid holiday) {
		if (itemCode.isRemainDaysOfHoliday()) {
			return holiday.getRemainDays().doubleValue();
		} else if (itemCode.isRemainTimeOfHoliday()) {
			return holiday.getRemainTime().doubleValue();
		} else {
			return 0;
		}
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
	private double getPayValueByMonthlyDaily(ItemCode itemCode, HolidayPaid holiday, PaymentDateMaster payDay,
			PaymentCalculationBasicInformation payCalBasic) {
		
		if (itemCode.isRemainDaysOfHoliday()) {
			return holiday.getRemainDays().doubleValue();
		} else if (itemCode.isRemainTimeOfHoliday()) {
			return holiday.getRemainTime().doubleValue();
		} else if (itemCode.isNeededWorkDays()) {
			return payDay.getNeededWorkDay().doubleValue();
		} else if (itemCode.isNeededWorkTime()) {
			return payDay.getNeededWorkDay().doubleValue() * payCalBasic.getBaseHours().doubleValue();
		} else {
			return 0;
		}
	}
	
	/**
	 * Get personal wage code from item code.
	 * @param itemCode
	 * @return
	 */
	private String getPersonalWageCode(String itemCode) {
		if (itemCode == null || itemCode == "") {
			return itemCode;
		}
		
		return itemCode.substring(2);
	}
}
