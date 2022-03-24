package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Builder;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountNumberOfPeopleByAttributeService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.EmploymentDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 属性ごとに人数を集計する
 * @author hoangnd
 *
 */
@Stateless
public class ScreenQueryAggregateNumberPeople {
	
	@Inject 
	private EmploymentRepository employmentRepository;
	
	@Inject
	private ClassificationRepository classificationRepository;
	
	@Inject 
	private JobTitleInfoRepository jobTitleInfoRepository;
	
	@Inject
	private BasicScheduleService service;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private FixedWorkSettingRepository fixedWorkSet;
	
	@Inject
	private FlowWorkSettingRepository flowWorkSet;
	
	@Inject
	private FlexWorkSettingRepository flexWorkSet;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;
	
	public AggregateNumberPeopleDto aggrerate(
			GeneralDate baseDate,
			List<IntegrationOfDaily> dailyWorks,
			WorkplaceCounterCategory workplaceCounterOp
			) {
		String companyId = AppContexts.user().companyId();
		RequireImpl require = RequireImpl.builder()
									.service(service)
									.workTypeRepository(workTypeRepository)
									.workTimeSettingRepository(workTimeSettingRepository)
									.fixedWorkSet(fixedWorkSet)
									.flowWorkSet(flowWorkSet)
									.flexWorkSet(flexWorkSet)
									.predetemineTimeSet(predetemineTimeSet)
									.build();
		AggregateNumberPeopleDto output = new AggregateNumberPeopleDto();
		if (workplaceCounterOp == WorkplaceCounterCategory.EMPLOYMENT_PEOPLE) { // 1: 職場計カテゴリ == 雇用人数
			// 1.1: 雇用別に集計する(Require, List<日別勤怠(Work)>)
			
			Map<GeneralDate, Map<EmploymentCode, BigDecimal>> countEachEpl 
				= CountNumberOfPeopleByAttributeService.countingEachEmployments(require, companyId, dailyWorks);
			
			List<String> empCodes = countEachEpl.entrySet()
						.stream()
						.map(x -> x.getValue())
						.map(x -> x.entrySet()
								   .stream()
								   .map(y -> y.getKey().v())
								   .collect(Collectors.toList()))
						.flatMap(list -> list.stream())
						.distinct()
						.collect(Collectors.toList());
			//1.2 : <call>
			List<Employment> employments = 
					employmentRepository.findByEmpCodes(
											companyId,
											empCodes);
			
			
			
			Map<GeneralDate, Map<EmploymentDto, BigDecimal>> employmentOutput = 
					countEachEpl.entrySet()
						.stream()
						.collect(Collectors.toMap(
								e -> e.getKey(),
								e -> e.getValue().entrySet()
												.stream()
												.collect(Collectors.toMap(
														f -> employments
																.stream()
																.filter(x -> x.getEmploymentCode().v().equals(f.getKey().v()))
																.map(x -> new EmploymentDto(
																				x.getCompanyId().v(),
																				x.getEmploymentCode().v(),
																				x.getEmploymentName().v(),
																				x.getEmpExternalCode().v(),
																				x.getMemo().v()
																				))
																.findFirst().orElse(null),
														f -> f.getValue()))
												.entrySet()
												.stream()
												.filter(x -> x.getKey() != null)
												.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
								
								));
			
			output.employment = employmentOutput;
			
			
		} else if (workplaceCounterOp == WorkplaceCounterCategory.CLASSIFICATION_PEOPLE) { // 職場計カテゴリ == 分類人数
			//2.1:  分類別に集計する(Require, List<日別勤怠(Work)>)
			Map<GeneralDate, Map<ClassificationCode, BigDecimal>> countEachClassification = 
					CountNumberOfPeopleByAttributeService.countingEachClassification(require, companyId, dailyWorks);
			//2.2: <call>
			List<Classification> classifications = classificationRepository.getClassificationByCodes(
					companyId, 
					countEachClassification.entrySet()
					.stream()
					.map(x -> x.getValue())
					.map(x -> x.entrySet().stream().map(y -> y.getKey().v()).collect(Collectors.toList()))
					.flatMap(list -> list.stream())
					.distinct()
					.collect(Collectors.toList()))
					.stream()
					.collect(Collectors.toList());
			
			Map<GeneralDate, Map<ClassificationDto, BigDecimal>> classificationOutput = 
					countEachClassification
						.entrySet()
						.stream()
						.collect(Collectors.toMap(
								e -> e.getKey(),
								e -> e.getValue().entrySet()
											.stream()
											.collect(Collectors.toMap(
													f -> classifications.stream()
																		.filter(x -> x.getClassificationCode().v().equals(f.getKey().v()))
																		.map(x -> ClassificationDto.fromDomain(x))
																		.findFirst()
																		.orElse(null),
													f -> f.getValue()))
											.entrySet()
											.stream()
											.filter(x -> x.getKey() != null)
											.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
								
								));
			
			output.classification = classificationOutput;
			
			
		} else if (workplaceCounterOp == WorkplaceCounterCategory.POSITION_PEOPLE) { // 職場計カテゴリ == 職位人数
			// 3.1: 職位別に集計する(Require, List<日別勤怠(Work)>)
			Map<GeneralDate, Map<String, BigDecimal>> countEachJob =
					CountNumberOfPeopleByAttributeService.countingEachJobTitle(require, companyId, dailyWorks);
			
			// 3.2: <call>
			List<JobTitleInfo> jobTitleInfos = 
					jobTitleInfoRepository
							.findByIds( 
								companyId,
								countEachJob
									.entrySet()
									.stream()
									.map(x -> x.getValue())
									.map(x -> x.entrySet()
											   .stream()
											   .map(y -> y.getKey())
											   .collect(Collectors.toList()))
									.flatMap(list -> list.stream())
									.distinct()
									.collect(Collectors.toList()),
							baseDate)
					.stream()
					.collect(Collectors.toList());
			
			Map<GeneralDate, Map<JobTitleInfoDto, BigDecimal>> jobTitileInfoOutput =
					countEachJob.entrySet()
					.stream()
					.collect(Collectors.toMap(
							e -> e.getKey(),
							e -> e.getValue()
										.entrySet()
										.stream()
										.collect(
												Collectors.toMap(
													f -> jobTitleInfos.stream()
																	  .filter(x -> x.getJobTitleId().equals(f.getKey()))
																	  .map(x -> JobTitleInfoDto.fromDomain(x))
																	  .findFirst()
																	  .orElse(null),
													f -> f.getValue()
												))
										.entrySet()
										.stream()
										.filter(x -> x.getKey() != null)
										.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
							
							));
			output.jobTitleInfo = jobTitileInfoOutput;
		}
		
		return output;
		
		
	}
	
	@Builder
	private static class RequireImpl implements CountNumberOfPeopleByAttributeService.Require {

		private BasicScheduleService service;
		
		private WorkTypeRepository workTypeRepository;
		
		private WorkTimeSettingRepository workTimeSettingRepository;
		
		private FixedWorkSettingRepository fixedWorkSet;
		
		private FlowWorkSettingRepository flowWorkSet;
		
		private FlexWorkSettingRepository flexWorkSet;
		
		private PredetemineTimeSettingRepository predetemineTimeSet;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			
			return service.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return this.workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return this.fixedWorkSet.findByKey(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return this.flowWorkSet.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return this.flexWorkSet.find(companyId, workTimeCode.v());
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return this.predetemineTimeSet.findByWorkTimeCode(companyId, workTimeCode.v());
		}

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return this.workTypeRepository.findByPK(companyId, workTypeCode.v());
		}
		
	}
}
