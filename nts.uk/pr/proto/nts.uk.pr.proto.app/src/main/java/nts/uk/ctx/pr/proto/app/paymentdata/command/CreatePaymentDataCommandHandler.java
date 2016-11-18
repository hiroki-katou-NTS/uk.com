package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.Date;
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
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.SparePayAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentCalculationBasicInformationRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
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

	@Override
	protected void handle(CommandHandlerContext<CreatePaymentDataCommand> context) {
		// get context
		LoginUserContext loginInfo = AppContexts.user();

		// get command
		CreatePaymentDataCommand command = context.getCommand();

		// get base date
		GeneralDate currentDate = GeneralDate.today();
		YearMonth baseYearMonth = YearMonth.of(currentDate.year(), currentDate.month());

		// get PayrollSystem
		Map<String, PersonalEmploymentContract> employmentContracts = getPersonalEmploymentContract(
				loginInfo.companyCode(), command.getPersonIdList(), currentDate.date());

		// get holiday
		Map<String, HolidayPaid> holidays = getHoliday(loginInfo.companyCode(), command.getPersonIdList());

		// get basic calculate
		PaymentCalculationBasicInformation payCalBasicInfo = payCalBasicInfoRepo.find(loginInfo.companyCode()).get();

		// get pay day
		PaymentDateMaster payDay = payDateMasterRepo.find(loginInfo.companyCode(), PayBonusAtr.SALARY.value,
				command.getProcessingYearMonth(), SparePayAtr.NORMAL.value, command.getProcessingNo()).get();

		// calculate personal
		for (String personId : command.getPersonIdList()) {
			PersonalEmploymentContract employmentContract = employmentContracts.get(personId);
			HolidayPaid holiday = holidays.get(personId);

			// get allot personal setting
			PersonalAllotSetting personalAllotSetting = getPersonalAllotSetting(loginInfo.companyCode(), personId,
					command.getProcessingYearMonth());

			// get layout master
			LayoutMaster layoutHead = layoutMasterRepo
					.getLayout(loginInfo.companyCode(), command.getProcessingYearMonth(), personalAllotSetting.getPaymentDetailCode().v()).get();

			PaymentDetailParam param = new PaymentDetailParam(loginInfo.companyCode(), new PersonId(personId),
					baseYearMonth.v(), command.getProcessingYearMonth());
			param.setHoliday(holiday);
			param.setPayCalBasicInfo(payCalBasicInfo);
			param.setEmploymentContract(employmentContract);
			param.setPaymentDateMaster(payDay);
			param.setPersonalAllotSetting(personalAllotSetting);

			Map<CategoryAtr, DetailItem> payDetail = paymentDetailService.calculatePayValue(param);
			
			
		}
	}

	/**
	 * Convert to domain object.
	 * 
	 * @return domain
	 */
	private Payment toDomain() {
		return null;
	}

	/**
	 * Get group holiday by person id
	 * 
	 * @return
	 */
	private Map<String, HolidayPaid> getHoliday(String companyCode, List<String> personIdList) {
		// get holiday
		List<HolidayPaid> holidayList = holidayPaidRepo.findAll(companyCode, personIdList);

		return holidayList.stream().collect(Collectors.toMap(x -> {
			return x.getPersonId().v();
		}, x -> x));
	}

	/**
	 * Get group personal employment contract.
	 * 
	 * @param companyCode
	 * @param personIdList
	 * @param date
	 * @return
	 */
	private Map<String, PersonalEmploymentContract> getPersonalEmploymentContract(String companyCode,
			List<String> personIdList, Date date) {
		List<PersonalEmploymentContract> personalEmploymentContractList = personalEmploymentContractRepo
				.findAll(companyCode, personIdList, date);

		return personalEmploymentContractList.stream().collect(Collectors.toMap(x -> {
			return x.getPersonId().v();
		}, x -> x));
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
