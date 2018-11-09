package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyEndMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyStartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayGrantDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayPeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DefaultExtractionRangeService implements ExtractionRangeService {

	@Inject
	private AlarmPatternSettingRepository alarmRepo;
	@Inject
	private ClosureService closureService;
	@Inject
	private PublicHolidaySettingRepository publicHolidaySettingRepo;
	@Inject
	private AgreementOperationSettingAdapter agreementOperationSettingAdapter;
	

	@Override
	public List<CheckConditionTimeDto> getPeriodByCategory(String alarmCode, String companyId, int closureId,
			Integer processingYm) {
		List<CheckConditionTimeDto> result = new ArrayList<CheckConditionTimeDto>();
		List<CheckCondition> checkConList = alarmRepo.getCheckCondition(companyId, alarmCode);

		checkConList.forEach(c -> {
			if (c.isDaily()) {
				CheckConditionTimeDto daily = this.getDailyTime(c, closureId, new YearMonth(processingYm));
				result.add(daily);
				
			} else if (c.is4W4D()) {
				CheckConditionTimeDto schedual_4week = this.get4WeekTime(c, closureId, new YearMonth(processingYm), companyId);
				result.add(schedual_4week);
				
			} else if(c.isMonthly() || c.isMultipleMonth()) {
				CheckConditionTimeDto other = this.getMonthlyTime(c,closureId,new YearMonth(processingYm));
				result.add(other);
				
			} else if(c.isAgrrement()) {
				result.addAll(this.getAgreementTime(c, closureId, new YearMonth(processingYm)));
			}

		});
		
        Collections.sort(result, Comparator.comparing(CheckConditionTimeDto::getCategory)
                .thenComparing(CheckConditionTimeDto::getTabOrder));
		return result;
	}
	
	
	private List<CheckConditionTimeDto> getAgreementTime(CheckCondition c, int closureId, YearMonth yearMonth){
		List<CheckConditionTimeDto> result = new ArrayList<CheckConditionTimeDto>();
		
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date startDate = null;
		Date endDate = null;
//		String startMonth =null;
//		String endMonth = null;
		YearMonth startMonth = yearMonth;
		YearMonth endMonth = yearMonth;
		int year = 0;
		int checkMonth = 1;
		for(ExtractionRangeBase extractBase : c.getExtractPeriodList()) {
			
			if(extractBase instanceof ExtractionPeriodDaily) {
				ExtractionPeriodDaily extraction = (ExtractionPeriodDaily) extractBase;				
				CheckConditionPeriod period  = this.getPeriodDaily(extraction, closureId, yearMonth);
				startDate = period.getStartDate();
				endDate = period.getEndDate();
				CheckConditionTimeDto dailyDto = new CheckConditionTimeDto(c.getAlarmCategory().value, textAgreementTime(1), formatter.format(startDate), formatter.format(endDate), null, null);
				dailyDto.setTabOrder(1);
				dailyDto.setPeriod36Agreement(Period.One_Week.value);
				result.add(dailyDto);
				
			}else if(extractBase instanceof ExtractionPeriodMonth) {
				ExtractionPeriodMonth extraction = (ExtractionPeriodMonth) extractBase;
				
				if(extraction.getStartMonth().isDesignateMonth()) {
					startMonth = yearMonth.addMonths((-1)*extraction.getStartMonth().getStrMonthNo().get().getMonthNo());
				}else {
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
					startMonth = YearMonth.of(currentYear, extraction.getStartMonth().getFixedMonthly().get().getDesignatedMonth());
				}
				
				if(extraction.getEndMonth().isSpecifyClose()) {
					endMonth = yearMonth.addMonths((-1)*extraction.getEndMonth().getEndMonthNo().get().getMonthNo());
				}else {
					endMonth = yearMonth.addMonths((-1)*extraction.getEndMonth().getEndMonthNo().get().getMonthNo());
				}
				CheckConditionTimeDto monthDto = new CheckConditionTimeDto(c.getAlarmCategory().value, textAgreementTime(extraction.getNumberOfMonth().value +2), null, null, startMonth.toString(), endMonth.toString());
				monthDto.setTabOrder(extraction.getNumberOfMonth().value +2);
				if (checkMonth == 1) {
					monthDto.setPeriod36Agreement(Period.One_Month.value);
				} else if (checkMonth == 2) {
					monthDto.setPeriod36Agreement(Period.Two_Month.value);
				} else if (checkMonth == 3) {
					monthDto.setPeriod36Agreement(Period.Three_Month.value);
				}
				checkMonth++;
				result.add(monthDto);
				
			}else if(extractBase instanceof AYear) {
				AYear extraction = (AYear) extractBase;
				Optional<AgreementOperationSettingImport> agreementOperationSettingImport =  agreementOperationSettingAdapter.find(AppContexts.user().companyId());
				int firstMonth = 0;
				if (agreementOperationSettingImport.isPresent()){
					firstMonth = agreementOperationSettingImport.get().getStartingMonth().value + 1;
				}
				int yearMonthYear = yearMonth.year();
				if(extraction.isToBeThisYear()){
					if(firstMonth <= yearMonth.month()) {
						startMonth = YearMonth.of(yearMonthYear, firstMonth);
						firstMonth = firstMonth - 1;
						endMonth = yearMonth.addMonths(firstMonth);
						year = yearMonthYear;
					}else {
						year = yearMonthYear - 1;
						startMonth = YearMonth.of(yearMonthYear - 1, firstMonth);
						firstMonth = firstMonth - 1;
						YearMonth endMonthtemp= YearMonth.of( year,yearMonth.month());
						endMonth = endMonthtemp.addMonths(firstMonth);
						
					}
				}else {
					startMonth = YearMonth.of(yearMonthYear, firstMonth);
					firstMonth = firstMonth - 1;
					endMonth = YearMonth.of(yearMonthYear + 1, firstMonth);
					year = extraction.getYear();
				}
				CheckConditionTimeDto yearDto = new CheckConditionTimeDto(c.getAlarmCategory().value, textAgreementTime(5), null, null, startMonth.toString(), endMonth.toString(), year );
				yearDto.setTabOrder(5);
				yearDto.setPeriod36Agreement(Period.Yearly.value);
				result.add(yearDto);
			}
		}
		
		result.sort((a, b) ->  a.getCategoryName().compareTo(b.getCategoryName()));
		return result;
	}
	
	private String textAgreementTime(int tabOrder) {
		switch(tabOrder) {
			case 1 : return TextResource.localize("KAL010_208") + "　" + TextResource.localize("KAL004_69");
			case 2 : return TextResource.localize("KAL010_208") + "　" + TextResource.localize("KAL004_70"); 
			case 3 : return TextResource.localize("KAL010_208") + "　" + TextResource.localize("KAL004_71"); 
			case 4 : return TextResource.localize("KAL010_208") + "　" + TextResource.localize("KAL004_72"); 
			case 5 : return TextResource.localize("KAL010_208") + "　" + TextResource.localize("KAL004_73"); 
			default : return "";
		}
		
	}
	

	public CheckConditionTimeDto getDailyTime(CheckCondition c, int closureId, YearMonth yearMonth) {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date startDate = null;
		Date endDate = null;
	
		ExtractionPeriodDaily extraction = (ExtractionPeriodDaily) c.getExtractPeriodList().get(0);
		
		CheckConditionPeriod period = this.getPeriodDaily(extraction, closureId, yearMonth);
		startDate = period.getStartDate();
		endDate = period.getEndDate();
		
		return new CheckConditionTimeDto(c.getAlarmCategory().value,
				EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(), formatter.format(startDate),
				formatter.format(endDate), null, null);
	}
	
	
	public CheckConditionTimeDto getMonthlyTime(CheckCondition c, int closureId, YearMonth yearMonth) {
		//DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		YearMonth startMonthly = yearMonth;
		YearMonth endMonthly = yearMonth;
		ExtractionPeriodMonth extractionPeriodMonth =  (ExtractionPeriodMonth) c.getExtractPeriodList().get(0);
		if(extractionPeriodMonth.getStartMonth().getSpecifyStartMonth().value == SpecifyStartMonth.DESIGNATE_CLOSE_START_MONTH.value) {
			startMonthly = yearMonth.addMonths((-1)*extractionPeriodMonth.getStartMonth().getStrMonthNo().get().getMonthNo());
		}
		if(extractionPeriodMonth.getEndMonth().getSpecifyEndMonth().value == SpecifyEndMonth.SPECIFY_CLOSE_END_MONTH.value ) {
			endMonthly = yearMonth.addMonths((-1)*extractionPeriodMonth.getEndMonth().getEndMonthNo().get().getMonthNo());
		}
		
		return new CheckConditionTimeDto(c.getAlarmCategory().value,
				EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(), null,
				null, startMonthly.toString(), endMonthly.toString());
	}
	
	
	private CheckConditionPeriod getPeriodDaily(ExtractionPeriodDaily extraction, int closureId, YearMonth yearMonth ) {
		
		Date startDate =null;
		Date endDate =null;
		YearMonth startMonthly = yearMonth;
		YearMonth endMonthly = yearMonth;
		// Calculate start date
		if (extraction.getStartDate().getStartSpecify() == StartSpecify.DAYS) {
			Calendar calendar = Calendar.getInstance();
			if (extraction.getStartDate().getStrDays().get().getDayPrevious() == PreviousClassification.BEFORE)
				calendar.add(Calendar.DAY_OF_YEAR, -extraction.getStartDate().getStrDays().get().getDay().v());
			else
				calendar.add(Calendar.DAY_OF_YEAR, extraction.getStartDate().getStrDays().get().getDay().v());
				startDate = calendar.getTime();

		} else {
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, startMonthly.addMonths((-1)*extraction.getStartDate().getStrMonth().get().getMonth()));
			startDate = datePeriod.start().date();
		}

		// Calculate endDate
		if (extraction.getEndDate().getEndSpecify() == EndSpecify.DAYS) {
			Calendar calendar = Calendar.getInstance();
			if (extraction.getEndDate().getEndDays().get().getDayPrevious() == PreviousClassification.BEFORE)
				calendar.add(Calendar.DAY_OF_YEAR, -extraction.getEndDate().getEndDays().get().getDay().v());
			else
				calendar.add(Calendar.DAY_OF_YEAR, extraction.getEndDate().getEndDays().get().getDay().v());

			endDate = calendar.getTime();
		} else {
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, endMonthly.addMonths((-1)*extraction.getEndDate().getEndMonth().get().getMonth()));
			endDate = datePeriod.end().date();
		}
		
		  
		return new CheckConditionPeriod(startDate, endDate);
		
	}

	public CheckConditionTimeDto get4WeekTime(CheckCondition c, int closureId, YearMonth yearMonth, String companyId) {
		LocalDate sDate = null;
		LocalDate eDate = null;

		ExtractionPeriodUnit periodUnit = (ExtractionPeriodUnit) c.getExtractPeriodList().get(0);
		
		LocalDate countingDate;
		boolean isYMD;
		
		// Get from PublicHolidaySetting domain
		Optional<PublicHolidaySetting> publicHolidaySettingOpt = publicHolidaySettingRepo.findByCID(companyId);
		if (!publicHolidaySettingOpt.isPresent())
			return new CheckConditionTimeDto(c.getAlarmCategory().value,
					EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(), null, null, null, null);
		
		if(publicHolidaySettingOpt.get().getPublicHdManagementClassification()==PublicHolidayManagementClassification._1_MONTH) {
			PublicHolidayGrantDate publicHolidayGrantDate = (PublicHolidayGrantDate)publicHolidaySettingOpt.get().getPublicHdManagementStartDate();
			
			if(publicHolidayGrantDate.getPeriod()==PublicHolidayPeriod.FIRST_DAY_TO_LAST_DAY) {
				String processingMonth = yearMonth.toString();				
				sDate = LocalDate.of(Integer.valueOf(processingMonth.substring(0, 4)).intValue(), Integer.valueOf(processingMonth.substring(4, 6)).intValue(), 1);
				eDate = sDate.plusMonths(1);
				eDate = eDate.minusDays(1);
				
			}else {
				DatePeriod datePeriod = closureService.getClosurePeriod(closureId, yearMonth);
				sDate = datePeriod.start().localDate();
				eDate = datePeriod.end().localDate();								
			}
			
		}else {
			PublicHoliday publicHoliday = (PublicHoliday) publicHolidaySettingOpt.get().getPublicHdManagementStartDate();
			if (publicHoliday.getDetermineStartDate() == DayOfPublicHoliday.DESIGNATE_BY_YEAR_MONTH_DAY) {
				countingDate= publicHoliday.getDate().localDate();
				isYMD = true;
			}else {			
				String dayMonth= publicHoliday.getDayMonth().toString();
				if(dayMonth.length()<4) dayMonth ="0" +dayMonth;
				countingDate = LocalDate.of(LocalDate.now().getYear(), Integer.valueOf(dayMonth.substring(0, 2)).intValue(),
						Integer.valueOf(dayMonth.substring(2, 4)).intValue());
				isYMD= false;
			}
			LocalDate currentDate = LocalDate.now();
			if (isYMD) {
				long totalDays = ChronoUnit.DAYS.between(countingDate, currentDate);
				long index = totalDays / 28;
	
				switch (periodUnit.getSegmentationOfCycle()) {
				case ThePreviousCycle:
					index--;
				case Period:
					break;
				case TheNextCycle:
					index++;
				default:
					break;
				}
				if (index == -1)
					throw new RuntimeException("1周期目の期間がシステム日付を含む期間となりますが周期の区分 は 実行日を含む周期の前周期！");
	
				sDate = countingDate.plusDays(index * 28);
				eDate = sDate.plusDays(27);
	
			} else {
				long totalDays = ChronoUnit.DAYS.between(countingDate, currentDate);
				long index = totalDays / 28;
	
				switch (periodUnit.getSegmentationOfCycle()) {
				case ThePreviousCycle:
					index--;
				case Period:
					break;
				case TheNextCycle:
					index++;
				default:
					break;
				}
	
				if (index == -1) {
					countingDate.minusYears(1);
					index = 13;
					sDate = countingDate.plusDays(index * 28);
					eDate = LocalDate.of(countingDate.getYear(), 12, 31);
				} else if (index == 14) {
					countingDate.plusYears(1);
					index = 0;
					sDate = countingDate.plusDays(index * 28);
					eDate = sDate.plusDays(27);
				} else {
					sDate = countingDate.plusDays(index * 28);
					eDate = sDate.plusDays(27);
				}
	
			}
		}
		return new CheckConditionTimeDto(c.getAlarmCategory().value,
				EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(),
				sDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
				eDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), null, null);
	}

}
