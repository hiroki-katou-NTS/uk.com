package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.TaxAtr;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.SumScopeAtr;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.BonusTaxRate;
import nts.uk.ctx.pr.proto.dom.paymentdata.CalcFlag;
import nts.uk.ctx.pr.proto.dom.paymentdata.Comment;
import nts.uk.ctx.pr.proto.dom.paymentdata.DependentNumber;
import nts.uk.ctx.pr.proto.dom.paymentdata.MakeMethodFlag;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.ProcessingNo;
import nts.uk.ctx.pr.proto.dom.paymentdata.SparePayAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.SpecificationCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.TenureAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.position.PrintPositionCategory;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.AgeContinuationInsureAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.EmploymentInsuranceAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.HealthInsuranceAverageEarn;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.HealthInsuranceGrade;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.InsuredAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.PensionAverageEarn;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.PensionInsuranceGrade;
import nts.uk.ctx.pr.proto.dom.paymentdata.insure.WorkInsuranceCalculateAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentCalculationBasicInformationRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.residence.ResidenceCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.residence.ResidenceName;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDataCheckService;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailParam;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailService;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContractRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaidRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.primitive.PersonId;

/**
 * Command Handler: add payment data.
 * 
 * @author chinhbv
 *
 */
@RequestScoped
@Transactional
public class CreatePaymentDataCommandHandler extends CommandHandler<CreatePaymentDataCommand> {

	@Inject
	private PaymentDetailService paymentDetailService;
	@Inject
	private PaymentDataCheckService paymentDataCheckService;
	@Inject
	private PersonalAllotSettingRepository personalAllotSettingRepo;
	@Inject
	private CompanyAllotSettingRepository companyAllotSettingRepo;
	@Inject
	private HolidayPaidRepository holidayPaidRepo;
	@Inject
	private PersonalEmploymentContractRepository personalEmploymentContractRepo;
	@Inject
	private PaymentCalculationBasicInformationRepository payCalBasicInfoRepo;
	@Inject
	private LayoutMasterRepository layoutMasterRepo;
	@Inject
	private PaymentDateMasterRepository payDateMasterRepo;
	@Inject
	private LayoutMasterDetailRepository layoutDetailMasterRepo;
	@Inject
	private PaymentDataRepository paymentDataRepo;
	@Inject
	private LayoutMasterLineRepository layoutMasterLineRepo;
	@Inject
	private LayoutMasterCategoryRepository layoutMasterCateRepo;
	

	@Override
	protected void handle(CommandHandlerContext<CreatePaymentDataCommand> context) {
		// get context
		LoginUserContext loginInfo = AppContexts.user();

		// get command
		CreatePaymentDataCommand command = context.getCommand();

		// get base date
		GeneralDate currentDate = GeneralDate.today();
		YearMonth baseYearMonth = YearMonth.of(currentDate.year(), currentDate.month());
				
		// get basic calculate
		PaymentCalculationBasicInformation payCalBasicInfo = payCalBasicInfoRepo.find(loginInfo.companyCode())
				.orElseThrow(() -> new RuntimeException("PaymentCalculationBasicInformation not found"));
		
		// get pay day
		PaymentDateMaster payDay = payDateMasterRepo.find(loginInfo.companyCode(), PayBonusAtr.SALARY.value,
				command.getProcessingYearMonth(), SparePayAtr.NORMAL.value, command.getProcessingNo())
					.orElseThrow(() -> new RuntimeException("PaymentDateMaster not found"));
			
		// calculate personal
		calculatePersonalPayment(
				loginInfo,
				YearMonth.of(command.getProcessingYearMonth()),
				command.getProcessingNo(),
				baseYearMonth,
				payCalBasicInfo,
				payDay,
				command.getPersonId());
	}

	/**
	 * Calculate personal payment. 
	 */
	private void calculatePersonalPayment(
			LoginUserContext loginInfo,
			YearMonth processingYearMonth,
			int processingNo,
			YearMonth baseYearMonth,
			PaymentCalculationBasicInformation payCalBasicInfo,
			PaymentDateMaster payDay,
			String personId) {
		// check exists
		boolean isExists = paymentDataCheckService.isExists(loginInfo.companyCode(), personId, PayBonusAtr.SALARY, processingYearMonth.v());
		if (isExists) {
			throw new RuntimeException("既にデータが存在します。");
		}
		
		//
		// Start calculate payment
		//
		PersonalEmploymentContract employmentContract = personalEmploymentContractRepo.findActive(
				loginInfo.companyCode(), personId, GeneralDate.today().localDate())
					.orElseThrow(() -> new RuntimeException("PersonalEmploymentContract not found"));
		HolidayPaid holiday = holidayPaidRepo.find(loginInfo.companyCode(), personId)
					.orElseThrow(() -> new RuntimeException("HolidayPaid not found")); 
		
		// get allot personal setting
		PersonalAllotSetting personalAllotSetting = getPersonalAllotSetting(loginInfo.companyCode(), personId,
				processingYearMonth.v());

		// get layout master
		LayoutMaster layoutHead = layoutMasterRepo.getLayout(loginInfo.companyCode(),
				processingYearMonth.v(), personalAllotSetting.getPaymentDetailCode().v())
					.orElseThrow(() -> new RuntimeException("LayoutMaster not found"));
		
		// get personal allot setting
		String stmtCode = personalAllotSetting.getPaymentDetailCode().v();

		// get layout detail master
		List<LayoutMasterDetail> layoutMasterDetailList = layoutDetailMasterRepo.getDetails(loginInfo.companyCode(),
						stmtCode, personalAllotSetting.getStartDate().v());
		// get layout master line
		List<LayoutMasterLine> lineList = layoutMasterLineRepo.getLines(loginInfo.companyCode(), stmtCode, processingYearMonth.v());
		
		PaymentDetailParam param = new PaymentDetailParam(
				loginInfo.companyCode(),
				new PersonId(personId),
				baseYearMonth,
				processingYearMonth,
				holiday,
				employmentContract,
				payCalBasicInfo,
				payDay,
				personalAllotSetting,
				layoutMasterDetailList,
				lineList);

		// calculate payment detail
		Map<CategoryAtr, List<DetailItem>> payDetail = paymentDetailService.calculatePayValue(param);
		
		// get layout master detail
		List<LayoutMasterDetail> layoutDetailMasterList = layoutMasterDetailList.stream()
				.filter(x -> CategoryAtr.PAYMENT == x.getCategoryAtr() && SumScopeAtr.INCLUDED == x.getSumScopeAtr())
				.collect(Collectors.toList());

		List<DetailItem> detailPaymentList = Helper.createDetailsOfPayment(payDetail, layoutDetailMasterList);
		List<DetailItem> detailDeductionList = Helper.createDetailsOfDeduction(payDetail, layoutDetailMasterList);
		List<PrintPositionCategory> positionCategoryList = Helper.createPositionCategory(payDetail, lineList);
		
		Payment paymentHead = this.toDomain(
				loginInfo.companyCode(), personId, processingNo, processingYearMonth, stmtCode,
				detailPaymentList, detailDeductionList, payDetail, positionCategoryList);
		
		paymentDataRepo.add(paymentHead);
	}

	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	private Payment toDomain(String companyCode, String personId, int processingNo, YearMonth processingYearMonth, String stmtCode,
			List<DetailItem> detailPaymentList, List<DetailItem> detailDeductionList,
			Map<CategoryAtr, List<DetailItem>> payDetail, List<PrintPositionCategory> positionCategoryList) {
		Payment payment = new Payment(
				new CompanyCode(companyCode), 
				new PersonId(personId),
				new ProcessingNo(processingNo), 
				PayBonusAtr.SALARY, 
				processingYearMonth, 
				SparePayAtr.NORMAL,
				GeneralDate.today(),
				new SpecificationCode(stmtCode), 
				new ResidenceCode("000001"), 
				new ResidenceName("住民税納付先"), 
				new HealthInsuranceGrade(5), 
				new HealthInsuranceAverageEarn(98000), 
				AgeContinuationInsureAtr.NOT_TARGET, 
				TenureAtr.TENURE,
				TaxAtr.TAXATION,
				new PensionInsuranceGrade(1), 
				new PensionAverageEarn(98000), 
				EmploymentInsuranceAtr.A, 
				new DependentNumber(0),
				WorkInsuranceCalculateAtr.FULL_TIME_EMPLOYEE, 
				InsuredAtr.GENERAL_INSURED_PERSON,
				new BonusTaxRate(0),
				CalcFlag.CALCULATED, 
				MakeMethodFlag.INITIAL_DATA, 
				new Comment(""));

		payment.setDetailPaymentItems(detailPaymentList);
		payment.setDetailDeductionItems(detailDeductionList);
		payment.setDetailPersonalTimeItems(payDetail.get(CategoryAtr.PERSONAL_TIME));
		payment.setDetailArticleItems(payDetail.get(CategoryAtr.ARTICLES));
		payment.setPositionCategoryItems(positionCategoryList);
		
		return payment;
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
