package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountNumberOfPeopleByAttributeService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 属性ごとに人数を集計する
 * @author hoangnd
 *
 */
@Stateless
public class ScreenQueryAggrerateNumberPeople {

//	@Inject
//	private CountNumberOfPeopleByAttributeService.Require require;
	
	@Inject 
	private EmploymentRepository employmentRepository;
	
	@Inject
	private ClassificationRepository classificationRepository;
	
	@Inject 
	private JobTitleInfoRepository jobTitleInfoRepository;
	
	public AggrerateNumberPeopleDto aggrerate(
			GeneralDate baseDate,
			List<IntegrationOfDaily> dailyWorks,
			WorkplaceCounterCategory workplaceCounterOp
			) {
		
		AggrerateNumberPeopleDto output = new AggrerateNumberPeopleDto();
		String companyId = AppContexts.user().companyId();
		if (workplaceCounterOp == WorkplaceCounterCategory.EMPLOYMENT_PEOPLE) { // 1: 職場計カテゴリ == 雇用人数
			// 1.1: 雇用別に集計する(Require, List<日別勤怠(Work)>)
			
			Map<GeneralDate, Map<EmploymentCode, BigDecimal>> countEachEpl 
				= CountNumberOfPeopleByAttributeService.countingEachEmployments(null, dailyWorks);
			
			List<String> empCodes = countEachEpl.entrySet()
						.stream()
						.map(x -> x.getValue())
						.map(x -> x.entrySet().stream().map(y -> y.getKey().v()).collect(Collectors.toList()))
						.flatMap(list -> list.stream())
						.distinct()
						.collect(Collectors.toList());
			//1.2 : <call>
			List<Employment> employments = employmentRepository.findByEmpCodes(
					companyId,
					empCodes);
			
			
			
			Map<GeneralDate, Map<Employment, BigDecimal>> employmentOutput = 
					countEachEpl.entrySet()
						.stream()
						.collect(Collectors.toMap(
								e -> (GeneralDate) e.getKey(),
								e -> ((Map<EmploymentCode, BigDecimal>)e.getValue()).entrySet()
											.stream()
											.collect(Collectors.toMap(
													f -> employments.stream().filter(x -> x.getEmploymentCode().v().equals(f.getKey().v())).findFirst().orElse(null),
													f -> f.getValue()))
								
								));
			
			output.employment = employmentOutput;
			
			
		} else if (workplaceCounterOp == WorkplaceCounterCategory.CLASSIFICATION_PEOPLE) { // 職場計カテゴリ == 分類人数
			//2.1:  分類別に集計する(Require, List<日別勤怠(Work)>)
			Map<GeneralDate, Map<ClassificationCode, BigDecimal>> countEachClassification = 
					CountNumberOfPeopleByAttributeService.countingEachClassification(null, dailyWorks);
			//2.2: <call>
			List<Classification> classifications = classificationRepository.getClassificationByCodes(
					companyId, 
					countEachClassification.entrySet()
					.stream()
					.map(x -> x.getValue())
					.map(x -> x.entrySet().stream().map(y -> y.getKey().v()).collect(Collectors.toList()))
					.flatMap(list -> list.stream())
					.distinct()
					.collect(Collectors.toList()));
			
			Map<GeneralDate, Map<Classification, BigDecimal>> classificationOutput = 
					countEachClassification.entrySet()
					.stream()
					.collect(Collectors.toMap(
							e -> (GeneralDate) e.getKey(),
							e -> ((Map<ClassificationCode, BigDecimal>)e.getValue()).entrySet()
										.stream()
										.collect(Collectors.toMap(
												f -> classifications.stream().filter(x -> x.getClassificationCode().v().equals(f.getKey().v())).findFirst().orElse(null),
												f -> f.getValue()))
							
							));
			
			output.classification = classificationOutput;
			
			
		} else if (workplaceCounterOp == WorkplaceCounterCategory.POSITION_PEOPLE) { // 職場計カテゴリ == 職位人数
			// 3.1: 職位別に集計する(Require, List<日別勤怠(Work)>)
			Map<GeneralDate, Map<String, BigDecimal>> countEachJob =
					CountNumberOfPeopleByAttributeService.countingEachJobTitle(null, dailyWorks);
			
			// 3.2: <call>
			List<JobTitleInfo> jobTitleInfos = jobTitleInfoRepository.findByIds(
					companyId,
					countEachJob.entrySet()
					.stream()
					.map(x -> x.getValue())
					.map(x -> x.entrySet().stream().map(y -> y.getKey()).collect(Collectors.toList()))
					.flatMap(list -> list.stream())
					.distinct()
					.collect(Collectors.toList()),
					baseDate);
			Map<GeneralDate, Map<JobTitleInfo, BigDecimal>> jobTitileInfoOutput =
					countEachJob.entrySet()
					.stream()
					.collect(Collectors.toMap(
							e -> (GeneralDate) e.getKey(),
							e -> ((Map<String, BigDecimal>)e.getValue()).entrySet()
										.stream()
										.collect(Collectors.toMap(
												f -> jobTitleInfos.stream().filter(x -> x.getJobTitleCode().v().equals(f.getKey())).findFirst().orElse(null),
												f -> f.getValue()))
							
							));
			output.jobTitleInfo = jobTitileInfoOutput;
		}
		
		return output;
		
		
	}
}
