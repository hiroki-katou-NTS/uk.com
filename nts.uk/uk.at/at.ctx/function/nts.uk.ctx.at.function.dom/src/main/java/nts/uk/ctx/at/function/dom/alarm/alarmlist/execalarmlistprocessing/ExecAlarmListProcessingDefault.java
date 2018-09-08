package nts.uk.ctx.at.function.dom.alarm.alarmlist.execalarmlistprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractAlarmListService;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.CheckConditionTimeDto;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeService;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

@Stateless
public class ExecAlarmListProcessingDefault implements ExecAlarmListProcessingService {

	@Inject
	private AlarmListExtraProcessStatusRepository alarmListExtraProcessStatusRepo;
	
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject 
	private ExtractionRangeService extractionRangeService;
	
	@Inject
	private ExtractAlarmListService extractAlarmListService;
	
	@Override
	public boolean execAlarmListProcessing(String extraProcessStatusID,String companyId, List<String> workplaceIdList,
			List<String> listPatternCode, GeneralDateTime dateTime) {
		//ドメインモデル「アラームリスト抽出処理状況」を取得する
		Optional<AlarmListExtraProcessStatus> alarmListExtraProcessStatus =  alarmListExtraProcessStatusRepo.getAlListExtaProcessByID(extraProcessStatusID);
		//取得できなかった場合
		if(!alarmListExtraProcessStatus.isPresent())
			return false;
		
		//期間内に特定の職場（List）に所属している社員一覧を取得
		//TODO:RequestList466
		
		//「締め」を取得する
		List<Closure> listClosure = closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);
		
		//取得した社員を雇用毎に分ける
		//雇用毎に集計処理をする
		boolean checkException = false;
		try {
			for(Closure closure : listClosure) {
				//期間を算出する
				List<CheckConditionTimeDto> listCheckCondition = 
				extractionRangeService.getPeriodByCategory(
						listPatternCode.get(0), companyId,closure.getClosureId().value, 
						closure.getClosureMonth().getProcessingYm().v());
				List<PeriodByAlarmCategory> listPeriodByCategory = new ArrayList<>();
				for(CheckConditionTimeDto checkConditionTime : listCheckCondition) {
					if(checkConditionTime.getCategory() == AlarmCategory.SCHEDULE_4WEEK.value ||
							checkConditionTime.getCategory() == AlarmCategory.DAILY.value) {
						GeneralDate startDate =  GeneralDate.fromString(checkConditionTime.getStartDate(), "yyyy/MM/dd");
						GeneralDate endDate  =  GeneralDate.fromString(checkConditionTime.getStartDate(), "yyyy/MM/dd");
						PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
								checkConditionTime.getCategory(),
								checkConditionTime.getCategoryName(),
								startDate,
								endDate
								);
						listPeriodByCategory.add(periodByAlarmCategory);
					}else if(checkConditionTime.getCategory() == AlarmCategory.MONTHLY.value ||
							 	checkConditionTime.getCategory() == AlarmCategory.MULTIPLE_MONTH.value) {
						GeneralDate startDate =  GeneralDate.fromString(checkConditionTime.getStartDate()+"/01", "yyyy/MM/dd");
						GeneralDate endDate =  GeneralDate.fromString(checkConditionTime.getStartDate()+"/01", "yyyy/MM/dd").addMonths(1).addDays(-1);
						PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
								checkConditionTime.getCategory(),
								checkConditionTime.getCategoryName(),
								startDate,
								endDate
								);
						listPeriodByCategory.add(periodByAlarmCategory);
					}else if(checkConditionTime.getCategory() == AlarmCategory.AGREEMENT.value ) {
						if(checkConditionTime.getCategoryName().equals("36協定　1・2・4週間")){
							GeneralDate startDate =  GeneralDate.fromString(checkConditionTime.getStartDate(), "yyyy/MM/dd");
							GeneralDate endDate  =  GeneralDate.fromString(checkConditionTime.getStartDate(), "yyyy/MM/dd");
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(),
									checkConditionTime.getCategoryName(),
									startDate,
									endDate
									);
							listPeriodByCategory.add(periodByAlarmCategory);
						}else if(checkConditionTime.getCategoryName().equals("36協定　年間")){
							GeneralDate startDate = GeneralDate.fromString(checkConditionTime.getYear()+"/"+checkConditionTime.getStartDate().substring(5, 7)+"/01", "yyyy/MM/dd");
							GeneralDate endDate = GeneralDate.fromString(checkConditionTime.getYear()+"/"+checkConditionTime.getEndDate().substring(5, 7)+"/01", "yyyy/MM/dd").addYears(1).addMonths(-1);
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(),
									checkConditionTime.getCategoryName(),
									startDate,
									endDate
									);
							listPeriodByCategory.add(periodByAlarmCategory);
						}else {
							GeneralDate startDate =  GeneralDate.fromString(checkConditionTime.getStartDate()+"/01", "yyyy/MM/dd");
							GeneralDate endDate =  GeneralDate.fromString(checkConditionTime.getStartDate()+"/01", "yyyy/MM/dd").addMonths(1).addDays(-1);
							PeriodByAlarmCategory periodByAlarmCategory = new PeriodByAlarmCategory(
									checkConditionTime.getCategory(),
									checkConditionTime.getCategoryName(),
									startDate,
									endDate
									);
							listPeriodByCategory.add(periodByAlarmCategory);
						}
					}
				}
				//集計処理を実行する
				extractAlarmListService.extractAlarm(new ArrayList<>(), listPatternCode.get(0), listPeriodByCategory);
				
				//メールを送信する
				//TODO : Chờ bên 2nf chuyển hàm từ app về dom
				
			}
		} catch (Exception e) {
			checkException = true;
		} 
		AlarmListExtraProcessStatus alarmListExtra = alarmListExtraProcessStatus.get();
		int endTime = GeneralDateTime.now().hours()*60+GeneralDateTime.now().minutes();
		alarmListExtra.setEndDateAndEndTime(GeneralDate.today(), endTime);
		if(checkException) {
			alarmListExtra.setStatus(ExtractionState.ABNORMAL_TERMI);
			alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
			return false;
		}
		alarmListExtra.setStatus(ExtractionState.SUCCESSFUL_COMPLE);
		alarmListExtraProcessStatusRepo.updateAlListExtaProcess(alarmListExtra);
		return true;
		
	}

}
