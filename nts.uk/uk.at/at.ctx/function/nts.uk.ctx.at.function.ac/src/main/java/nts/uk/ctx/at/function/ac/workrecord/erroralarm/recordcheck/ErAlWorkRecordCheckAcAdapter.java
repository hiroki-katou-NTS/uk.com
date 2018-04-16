package nts.uk.ctx.at.function.ac.workrecord.erroralarm.recordcheck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeResult;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.ErAlSubjectFilterConditionDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.ErAlWorkRecordCheckServicePub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.recordcheck.RegulationInfoEmployeeQueryResult;

@Stateless
public class ErAlWorkRecordCheckAcAdapter implements ErAlWorkRecordCheckAdapter {
	@Inject
	private ErAlWorkRecordCheckServicePub erAlWorkRecordCheckServicePub;

	@Override
	public Map<String, Map<String, Boolean>> check(GeneralDate workingDate, Collection<String> employeeIds, List<String> EACheckIDs) {
		return erAlWorkRecordCheckServicePub.check(workingDate, employeeIds, EACheckIDs);
	}

	@Override
	public Map<String, List<RegulationInfoEmployeeResult>> filterEmployees(GeneralDate workingDate, Collection<String> employeeIds, List<String> EACheckIDs) {
		Map<String, List<RegulationInfoEmployeeResult>> result = new HashMap<String, List<RegulationInfoEmployeeResult>>();

		Map<String, List<RegulationInfoEmployeeQueryResult>> mapRegularInfoEmployee = erAlWorkRecordCheckServicePub.filterEmployees(workingDate, employeeIds, EACheckIDs);
		List<String> erAlCheckIDs = new ArrayList<String>(mapRegularInfoEmployee.keySet());

		for (String erAlarmCheckID : erAlCheckIDs)
			result.put(erAlarmCheckID, mapRegularInfoEmployee.get(erAlarmCheckID).stream().map(e -> buildImport(e)).collect(Collectors.toList()));

		return result;
	}

	private RegulationInfoEmployeeResult buildImport(RegulationInfoEmployeeQueryResult export) {

		return RegulationInfoEmployeeResult.builder().employeeCode(export.getEmployeeCode()).employeeId(export.getEmployeeId()).employeeName(export.getEmployeeName())
				.workplaceCode(export.getWorkplaceCode()).workplaceId(export.getWorkplaceId()).workplaceName(export.getWorkplaceName()).build();
	}

	@Override
	public List<RegulationInfoEmployeeResult> filterEmployees(GeneralDate workingDate, Collection<String> employeeIds, AlarmCheckTargetCondition condition) {

		ErAlSubjectFilterConditionDto filterCondition = ErAlSubjectFilterConditionDto.builder().filterByBusinessType(condition.isFilterByBusinessType())
				.filterByClassification(condition.isFilterByClassification()).filterByEmployment(condition.isFilterByEmployment()).filterByJobTitle(condition.isFilterByJobTitle())
				.lstBusinessTypeCode(condition.getLstBusinessTypeCode()).lstClassificationCode(condition.getLstClassificationCode()).lstEmploymentCode(condition.getLstEmploymentCode())
				.lstJobTitleId(condition.getLstJobTitleId()).build();
		return erAlWorkRecordCheckServicePub.filterEmployees(workingDate, employeeIds, filterCondition).stream().map(c -> buildImport(c)).collect(Collectors.toList());
	}

}
