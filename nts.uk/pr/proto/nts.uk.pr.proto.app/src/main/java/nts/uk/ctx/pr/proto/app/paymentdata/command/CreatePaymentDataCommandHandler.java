package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.SparePayAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentCalculationBasicInformationRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailParam;
import nts.uk.ctx.pr.proto.dom.paymentdata.service.PaymentDetailService;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFee;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFeeRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContract;
import nts.uk.ctx.pr.proto.dom.personalinfo.employmentcontract.PersonalEmploymentContractRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaidRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWageRepository;
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
	private HolidayPaidRepository holidayPaidRepo;
	@Inject
	private PersonalEmploymentContractRepository personalEmploymentContractRepo;
	@Inject
	private PaymentCalculationBasicInformationRepository payCalBasicInfoRepo;
	@Inject
	private PaymentDateMasterRepository payDateMasterRepo;
	@Inject
	private PersonalWageRepository personalWageRepo;
	@Inject
	private PersonalCommuteFeeRepository personalCommuteRepo;
	@Inject
	private PaymentDataRepository paymentDataRepo;

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

			PaymentDetailParam param = new PaymentDetailParam(loginInfo.companyCode(), new PersonId(personId),
					baseYearMonth.v(), command.getProcessingYearMonth());
			param.setHoliday(holiday);
			param.setPayCalBasicInfo(payCalBasicInfo);
			param.setEmploymentContract(employmentContract);
			param.setPaymentDateMaster(payDay);

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


}
