package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Transactional
public class AddProcessingSegmentCommandHandler extends CommandHandler<ProcessingSegmentCommand> {

	@Inject
	ValPayDateSetRepository valPayDateSetRepository;

	@Inject
	ProcessInformationRepository processInformationRepository;

	@Inject
	SpecPrintYmSetRepository specPrintYmSetRepository;

	@Inject
	SetDaySupportRepository setDaySupportRepository;

	@Inject
	CurrProcessDateRepository currProcessDateRepository;

	@Inject
	EmpTiedProYearRepository empTiedProYearRepository;

	@Inject
	private SetDaySupportFinder setDaySupportFinder;

	

	@Override
	protected void handle(CommandHandlerContext<ProcessingSegmentCommand> commandHandlerContext) {
		String cid = AppContexts.user().companyId();
		ProcessingSegmentCommand addCommand = commandHandlerContext.getCommand();

		

		this.processInformationRepository
				.add(new ProcessInformation(cid, addCommand.getProcessInformation().getProcessCateNo(),
						addCommand.getProcessInformation().getDeprecatCate(),
						addCommand.getProcessInformation().getProcessDivisionName()));

		this.valPayDateSetRepository.add(new ValPayDateSet(cid, addCommand.getValPayDateSet().getProcessCateNo(),
				addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getProcessMonth(),
				addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getDisposalDay(),
				addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth(),
				addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate(),
				addCommand.getValPayDateSet().getBasicSetting().getMonthlyPaymentDate().getDatePayMent(),
				addCommand.getValPayDateSet().getBasicSetting().getWorkDay(),
				addCommand.getValPayDateSet().getAdvancedSetting().getDetailPrintingMon().getPrintingMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSalaryInsuColMon().getMonthCollected(),
				addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getRefeDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getEmpInsurStanDate().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getRefeDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseYear(),
				addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseYear(),
				addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getRefeDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate(),
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseMonth(),
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseYear(),
				addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getRefeDate()));

		addSpecPrintYmSet(addCommand);
		addSetDaySupport(addCommand);
		addCurrProcessDate(addCommand);

	}

	public void addSpecPrintYmSet(ProcessingSegmentCommand addCommand) {
		String cid = AppContexts.user().companyId();
		int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();
		int currentYear = GeneralDate.today().year();

		int montOption = addCommand.getValPayDateSet().getAdvancedSetting().getDetailPrintingMon().getPrintingMonth();

		YearMonth processDate = new YearMonth((currentYear - 1) * 100 + 12);
		YearMonth printDate = new YearMonth((currentYear - 1) * 100 + 12);

		if (montOption == PreviousMonthClassification.THIS_MONTH.value) {
			for (int i = 1; i < 25; i++) {

				this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, processDate.addMonths(i).v(),
						printDate.addMonths(i).v()));
			}
		}
		if (montOption == PreviousMonthClassification.LAST_MONTH.value) {
			for (int i = 1; i < 25; i++) {
				this.specPrintYmSetRepository.add(new SpecPrintYmSet(cid, processCateNo, processDate.addMonths(1).v(),
						printDate.addMonths(i-1).v()));
			}
		}	
	}

	public void addSetDaySupport(ProcessingSegmentCommand addCommand) {
		String cid = AppContexts.user().companyId();
		int currentYear = GeneralDate.today().year();

		int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();
		// basic
		int payMentDate = addCommand.getValPayDateSet().getBasicSetting().getMonthlyPaymentDate().getDatePayMent();
		int refeDate = addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate()
				.getRefeDate();
		int refeMonth = addCommand.getValPayDateSet().getBasicSetting().getEmployeeExtractionReferenceDate()
				.getRefeMonth();
		int disposalDay = addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getDisposalDay();
		int processMonth = addCommand.getValPayDateSet().getBasicSetting().getAccountingClosureDate().getProcessMonth();
		// advanc
		int monthCollected = addCommand.getValPayDateSet().getAdvancedSetting().getSalaryInsuColMon()
				.getMonthCollected();

		int baseYear = addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseYear();
		int baseMonth = addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getBaseMonth();
		int baseDate = addCommand.getValPayDateSet().getAdvancedSetting().getSociInsuStanDate().getRefeDate();

		int referDateEmploymentInsuranceStanDate = addCommand.getValPayDateSet().getAdvancedSetting()
				.getEmpInsurStanDate().getRefeDate();
		int baseMonthEmploymentInsuranceStanDate = addCommand.getValPayDateSet().getAdvancedSetting()
				.getEmpInsurStanDate().getBaseMonth();

		int timeCloseDate = addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getTimeCloseDate();
		int refeDateClose = addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getRefeDate();
		int baseMonthClose = addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseMonth();
		int baseYearClose = addCommand.getValPayDateSet().getAdvancedSetting().getCloseDate().getBaseYear();

		int inComRefeMonth = addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseMonth();
		int inComRefeYear = addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getBaseYear();
		int inComRefeDate = addCommand.getValPayDateSet().getAdvancedSetting().getIncomTaxBaseYear().getRefeDate();

		BigDecimal numberWorkDay = addCommand.getValPayDateSet().getBasicSetting().getWorkDay();

		YearMonth startMonth = new YearMonth((currentYear - 1) * 100 + 12);

		for (int i = 1; i < 25; i++) {
			YearMonth yearMonth=startMonth.addMonths(i);
			GeneralDate empInsurdStanDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
					(referDateEmploymentInsuranceStanDate == DateSelectClassification.LAST_DAY_MONTH.value)
							? GeneralDate.today().lastDateInMonth() : referDateEmploymentInsuranceStanDate);
			if (baseMonthEmploymentInsuranceStanDate > i) {
				empInsurdStanDate.addYears(-1);
				empInsurdStanDate = GeneralDate.ymd(empInsurdStanDate.year(), baseMonthEmploymentInsuranceStanDate,
						empInsurdStanDate.day());
			}
			GeneralDate closureDateAccounting = convertDate(GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
					(disposalDay == DateSelectClassification.LAST_DAY_MONTH.value)
							? GeneralDate.today().lastDateInMonth() : disposalDay));
			if (processMonth == PreviousMonthClassification.LAST_MONTH.value) {
				closureDateAccounting.addMonths(-1);
				convertDate(closureDateAccounting);
			}

			GeneralDate paymentDate = convertDate(GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
					(payMentDate == DateSelectClassification.LAST_DAY_MONTH.value)
							? GeneralDate.today().lastDateInMonth() : payMentDate));

			GeneralDate empExtraRefeDate = convertDate(GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
					(refeDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth()
							: refeDate));
			if (refeMonth == PreviousMonthClassification.LAST_MONTH.value) {
				empExtraRefeDate.addMonths(-1);
				convertDate(empExtraRefeDate);
			}

			GeneralDate socialInsurdStanDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(),
					(baseDate == DateSelectClassification.LAST_DAY_MONTH.value) ? GeneralDate.today().lastDateInMonth()
							: baseDate);
			if (baseMonth == InsuranceStanMonthClassification.LAST_MONTH.value)
				socialInsurdStanDate.addMonths(-1);
			else if (baseMonth == InsuranceStanMonthClassification.MONTH.value)
				socialInsurdStanDate.addMonths(0);
			else
				socialInsurdStanDate = GeneralDate.ymd(currentYear + baseYear - 1, baseMonth - 1,
						(baseDate == DateSelectClassification.LAST_DAY_MONTH.value)
								? GeneralDate.today().lastDateInMonth() : baseDate);
			;

			YearMonth socialInsurdCollecMonth = yearMonth;
			socialInsurdCollecMonth.addMonths(monthCollected - 2);

			GeneralDate closeDateTime = empExtraRefeDate;
			if (timeCloseDate == 1) {
				closeDateTime.addYears(baseYearClose - 1);
				closeDateTime.addMonths(baseMonthClose - 2);
				closeDateTime = GeneralDate.ymd(closeDateTime.year(), closeDateTime.month(),
						(refeDateClose == DateSelectClassification.LAST_DAY_MONTH.value)
								? GeneralDate.today().lastDateInMonth() : refeDateClose);
			}

			GeneralDate incomeTaxDate = GeneralDate.ymd(currentYear, inComRefeMonth,
					(inComRefeDate == DateSelectClassification.LAST_DAY_MONTH.value)
							? GeneralDate.today().lastDateInMonth() : inComRefeDate);
			incomeTaxDate.addYears(inComRefeYear - 1);

			

			this.setDaySupportRepository.add(new SetDaySupport(cid, processCateNo, yearMonth.v(), closeDateTime,
					empInsurdStanDate, closureDateAccounting, paymentDate, empExtraRefeDate, socialInsurdStanDate,
					socialInsurdCollecMonth.v(), incomeTaxDate, numberWorkDay));
		}

	}

	public void addCurrProcessDate(ProcessingSegmentCommand addCommand) {
		String cid = AppContexts.user().companyId();
		/*
		 * List<SetDaySupportDto> setDaySupportDtoList =
		 * this.setDaySupportFinder.getAllSetDaySupport(); GeneralDate
		 * currentDay = GeneralDate.today();
		 * 
		 * for (int i = 0; i < setDaySupportDtoList.size(); i++) {
		 * 
		 * if (setDaySupportDtoList.get(i).getPaymentDate().yearMonth().v() ==
		 * currentDay.yearMonth().v() && currentDay.day() <
		 * setDaySupportDtoList.get(i).getPaymentDate().day()) currTreatYear =
		 * currentDay.yearMonth().v(); }
		 */
		GeneralDate currentDay = GeneralDate.today();
		int currTreatYear = currentDay.yearMonth().v();
		//String cid = addCommand.getValPayDateSet().getCid();
		int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();
		this.currProcessDateRepository.add(new CurrProcessDate(cid, processCateNo, currTreatYear));

	}

	public void addEmpTiedProYear(ProcessingSegmentCommand addCommand) {
		String cid = AppContexts.user().companyId();
		int processCateNo = addCommand.getValPayDateSet().getProcessCateNo();

		this.empTiedProYearRepository.add(new EmpTiedProYear(cid, processCateNo, new ArrayList<EmploymentCode>()));

	}

	public GeneralDate convertDate(GeneralDate convertDate) {

		if (convertDate.dayOfWeek() == 6)
			return convertDate.addDays(-1);
		else if (convertDate.dayOfWeek() == 7)
			return convertDate.addDays(-2);
		else
			return convertDate;

	}

}
