package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.recordcheck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckService;
import nts.uk.ctx.at.record.app.service.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckService.ErrorRecord;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationEmployeeInfoR;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.AlCheckTargetCondition;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.ErAlSubjectFilterConditionDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckServicePub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeQueryResult;

@Stateless
public class ErAlWorkRecordCheckServicePubImpl implements ErAlWorkRecordCheckServicePub {

	@Inject
	private ErAlWorkRecordCheckService checkService;
	
	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationFinder;
	

	@Override
	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds,
			List<String> EACheckIDs) {
		List<ErrorRecord> result = this.checkService.checkWithRecord(workingDate, employeeIds, EACheckIDs);
		
		return result.stream().collect(Collectors.groupingBy(c -> c.getErAlId(), 
				Collectors.collectingAndThen(Collectors.toList(), 
						list -> list.stream().collect(Collectors.toMap(c -> c.getEmployeeId(), c -> true)))));
	}

	@Override
	public List<ErrorRecordExport> check(List<String> EACheckIDs, DatePeriod workingDate, Collection<String> employeeIds) {
		return this.checkService.checkWithRecord(workingDate, employeeIds, EACheckIDs)
				.stream().map(c -> new ErrorRecordExport(c.getDate(), c.getEmployeeId(), c.getErAlId(),c.getCheckedValue())).collect(Collectors.toList());
	}
	
	@Override
	public List<ErrorRecordExport> check(List<String> EACheckIDs, GeneralDate workingDate, Collection<String> employeeIds) {
		return this.checkService.checkWithRecord(workingDate, employeeIds, EACheckIDs)
				.stream().map(c -> new ErrorRecordExport(c.getDate(), c.getEmployeeId(), c.getErAlId())).collect(Collectors.toList());
	}
	
	@Override
	public List<RegulationInfoEmployeeQueryResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, String EACheckID) {
		return this.checkService.filterEmployees(workingDate, employeeIds, EACheckID).stream().map(r -> mapTo(r))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, List<RegulationInfoEmployeeQueryResult>> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, List<String> EACheckIDs) {

		return this.checkService.filterEmployees(workingDate, employeeIds, EACheckIDs).entrySet().stream()
				.collect(Collectors.toMap(c -> c.getKey(),
						c -> c.getValue().stream().map(r -> mapTo(r)).collect(Collectors.toList())));
	}

	@Override
	public List<RegulationInfoEmployeeQueryResult> filterEmployees(GeneralDate workingDate,
			Collection<String> employeeIds, ErAlSubjectFilterConditionDto condition) {
		AlCheckTargetCondition filterCondition = new AlCheckTargetCondition(condition.getFilterByBusinessType(), 
				condition.getFilterByJobTitle(), condition.getFilterByEmployment(), condition.getFilterByClassification(), 
				condition.getLstBusinessTypeCode(), condition.getLstJobTitleId(), condition.getLstEmploymentCode(), condition.getLstClassificationCode());
		return this.checkService.filterEmployees(workingDate, employeeIds, filterCondition).stream().map(r -> mapTo(r))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, List<RegulationEmployeeInfoR>> filterEmployees(DatePeriod targetPeriod, Collection<String> employeeIds,
			List<ErAlSubjectFilterConditionDto> conditions) {
		List<String> empIds = new ArrayList<>(employeeIds);
		//所属情報
		List<AffiliationInforOfDailyPerfor> affiliations = affiliationFinder.finds(empIds, targetPeriod);
		
		return conditions.stream().collect(Collectors.toMap(c -> c.getErrorAlarmId(), c -> {
			return affiliations.stream().map(a -> {
				if(canCheck(a, c)){
					return RegulationEmployeeInfoR
							.builder()
							.employeeId(a.getEmployeeId())
							.targetDate(a.getYmd())
							.errorAlarmID(c.getErrorAlarmId())
							.businessTypeCode(a.getAffiliationInfor().getBusinessTypeCode().map(af -> af.v()).orElse(null))
							.employmentCode(a.getAffiliationInfor().getEmploymentCode().v())
							.jobTitleId(a.getAffiliationInfor().getJobTitleID())
							.classificationCode(a.getAffiliationInfor().getClsCode().v())
							.build();
				}
				return null;
			}).filter(a -> a != null).collect(Collectors.toList());
		}));
	}

	@Override
	public Map<ErAlSubjectFilterConditionDto, List<RegulationInfoEmployeeQueryResult>> filterEmployees(Collection<String> employeeIds,
			List<ErAlSubjectFilterConditionDto> condition, GeneralDate workingDate) {
		return condition.stream().collect(Collectors.toMap(c -> c, c -> filterEmployees(workingDate, employeeIds, c)));
	}

	private RegulationInfoEmployeeQueryResult mapTo(RegulationInfoEmployeeQueryR r) {
		return RegulationInfoEmployeeQueryResult.builder().employeeCode(r.getEmployeeCode())
				.employeeId(r.getEmployeeId()).employeeName(r.getEmployeeName()).workplaceCode(r.getWorkplaceCode())
				.workplaceId(r.getWorkplaceId()).workplaceName(r.getWorkplaceName()).build();
	}
	
	private boolean canCheck(AffiliationInforOfDailyPerfor affiliation, ErAlSubjectFilterConditionDto checkCondition){
		if(isTrue(checkCondition.getFilterByBusinessType())){
			if(!checkCondition.getLstBusinessTypeCode().contains(affiliation.getAffiliationInfor().getBusinessTypeCode().map(c -> c.v()).orElse(null))){
				return false;
			}
		}
		if(isTrue(checkCondition.getFilterByEmployment())){
			if(!checkCondition.getLstEmploymentCode().contains(affiliation.getAffiliationInfor().getEmploymentCode().v())){
				return false;
			}
		}
		if(isTrue(checkCondition.getFilterByClassification())){
			if(!checkCondition.getLstClassificationCode().contains(affiliation.getAffiliationInfor().getClsCode().v())){
				return false;
			}
		}
		if(isTrue(checkCondition.getFilterByJobTitle())){
			if(!checkCondition.getLstJobTitleId().contains(affiliation.getAffiliationInfor().getJobTitleID())){
				return false;
			}
		}
		return true;
	}

	private boolean isTrue(Boolean checkCondition) {
		return checkCondition != null && checkCondition;
	}

}
