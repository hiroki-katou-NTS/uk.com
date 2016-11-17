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
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.PaymentCalculationBasicInformation;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentCalculationBasicInformationRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
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
 * @author chinhbv
 *
 */
@RequestScoped
@Transactional
public class CreatePaymentDataCommandHandler  extends CommandHandler<CreatePaymentDataCommand>{
	
	@Inject
	private PaymentDetailService paymentDetailService;
	@Inject
	private HolidayPaidRepository holidayPaidRepo;
	@Inject
	private PersonalEmploymentContractRepository personalEmploymentContractRepo;
	@Inject 
	private PaymentCalculationBasicInformationRepository payCalBasicInfoRepo;
		
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
		Map<String, PersonalEmploymentContract> employmentContracts = getPersonalEmploymentContract(loginInfo.companyCode(), command.getPersonIdList(), currentDate.date());
		
		// get holiday
		Map<String, HolidayPaid> holidays = getHoliday(loginInfo.companyCode(), command.getPersonIdList());
		
		// get basic calculate
		PaymentCalculationBasicInformation payCalBasicInfo = payCalBasicInfoRepo.find(loginInfo.companyCode()).get();
				
		for (String personId : command.getPersonIdList()) {
			PersonalEmploymentContract employmentContract = employmentContracts.get(personId);
			HolidayPaid holiday = holidays.get(personId);
			
			Map<CategoryAtr, DetailItem> payDetail = paymentDetailService.calculatePayValue(
					loginInfo.companyCode(), 
					new PersonId(personId), 
					baseYearMonth.v(), 
					holiday, employmentContract, payCalBasicInfo);
			
			
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
	 * @return
	 */
	private Map<String, HolidayPaid> getHoliday(String companyCode, List<String> personIdList) {
		// get holiday
		List<HolidayPaid> holidayList = holidayPaidRepo.findAll(companyCode, personIdList);
		
		return holidayList.stream().collect(Collectors.toMap(x -> { return x.getPersonId().v(); }, x -> x));
	}
	
	/**
	 * Get group personal employment contract.
	 * @param companyCode
	 * @param personIdList
	 * @param date
	 * @return
	 */
	private Map<String, PersonalEmploymentContract> getPersonalEmploymentContract(String companyCode, List<String> personIdList, Date date) {
		List<PersonalEmploymentContract> personalEmploymentContractList = personalEmploymentContractRepo.findAll(companyCode, personIdList, date);
		
		return personalEmploymentContractList.stream().collect(Collectors.toMap(x -> { return x.getPersonId().v(); }, x -> x));
	}
}
