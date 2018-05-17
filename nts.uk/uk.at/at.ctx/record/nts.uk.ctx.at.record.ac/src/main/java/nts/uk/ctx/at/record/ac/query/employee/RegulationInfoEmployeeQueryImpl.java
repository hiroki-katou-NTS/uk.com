package nts.uk.ctx.at.record.ac.query.employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.query.employee.EmployeeSearchInfoDto;
import nts.uk.ctx.at.record.dom.adapter.query.employee.HistoryCommonInfo;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.query.pub.person.EmployeeInfoDto;
import nts.uk.query.pub.person.EmployeeInfoPublisher;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class RegulationInfoEmployeeQueryImpl implements RegulationInfoEmployeeQueryAdapter {

	@Inject
	private RegulationInfoEmployeePub employeePub;

	@Inject
	private EmployeeInfoPublisher emInfoPub;

	@Override
	public List<RegulationInfoEmployeeQueryR> search(RegulationInfoEmployeeQuery query) {
		return employeePub.find(createQueryToFilterEmployees(query)).stream()
				.map(r -> RegulationInfoEmployeeQueryR.builder().employeeCode(r.getEmployeeCode())
						.employeeId(r.getEmployeeId()).employeeName(r.getEmployeeName())
						.workplaceCode(r.getWorkplaceCode()).workplaceId(r.getWorkplaceId())
						.workplaceName(r.getWorkplaceName()).build())
				.collect(Collectors.toList());
	}

	private EmployeeSearchQueryDto createQueryToFilterEmployees(RegulationInfoEmployeeQuery queryX) {
		GeneralDateTime baseDate = GeneralDateTime.localDateTime(queryX.getBaseDate().localDate().atStartOfDay());
		GeneralDateTime periodStart = GeneralDateTime.localDateTime(queryX.getPeriodStart().localDate().atStartOfDay());
		GeneralDateTime periodEnd = GeneralDateTime.localDateTime(queryX.getPeriodEnd().localDate().atStartOfDay());
		GeneralDateTime retireStart = GeneralDateTime.localDateTime(queryX.getRetireStart().localDate().atStartOfDay());
		GeneralDateTime retireEnd = GeneralDateTime.localDateTime(queryX.getRetireEnd().localDate().atStartOfDay());
		return EmployeeSearchQueryDto.builder().baseDate(baseDate).referenceRange(queryX.getReferenceRange())
				.filterByEmployment(queryX.getFilterByEmployment()).employmentCodes(queryX.getEmploymentCodes())
				.filterByDepartment(queryX.getFilterByDepartment()).departmentCodes(queryX.getDepartmentCodes())
				.filterByWorkplace(queryX.getFilterByWorkplace()).workplaceCodes(queryX.getWorkplaceCodes())
				.filterByClassification(queryX.getFilterByClassification())
				.classificationCodes(queryX.getClassificationCodes()).filterByJobTitle(queryX.getFilterByJobTitle())
				.jobTitleCodes(queryX.getJobTitleCodes()).filterByWorktype(queryX.getFilterByWorktype())
				.worktypeCodes(queryX.getWorktypeCodes()).periodStart(periodStart).periodEnd(periodEnd)
				.includeIncumbents(queryX.getIncludeIncumbents())
				.includeWorkersOnLeave(queryX.getIncludeWorkersOnLeave()).includeOccupancy(queryX.getIncludeOccupancy())
				.systemType(2).sortOrderNo(1).includeRetirees(queryX.getIncludeRetirees())
				.filterByClosure(queryX.getFilterByClosure()).retireStart(retireStart).retireEnd(retireEnd)
				.closureIds(queryX.getClosureIds()).build();
	}

	@Override
	public List<EmployeeSearchInfoDto> search(Collection<String> employeeIds, DatePeriod date) {
		EmployeeInfoDto query = new EmployeeInfoDto();
		query.employeeIds = new ArrayList<>(employeeIds);
		query.peroid = date;
		query.isFindWorkPlaceInfo = false;
		query.isFindBussinessTypeInfo = true;
		query.isFindClasssificationInfo = true;
		query.isFindEmploymentInfo = true;
		query.isFindJobTilteInfo = true;
		return emInfoPub.find(query).stream().map(e -> EmployeeSearchInfoDto.builder().employeeId(e.employeeId)
				.businessTypes(e.businessTypeHistorys.stream()
						.map(b -> HistoryCommonInfo.builder().code(b.businessTypeCode).employeeId(b.employeeId)
								.range(new DatePeriod(b.startDate, b.endDate)).build())
						.collect(Collectors.toList()))
				.classifications(e.classificationHistorys.stream()
						.map(b -> HistoryCommonInfo.builder().code(b.classificationCode).employeeId(b.employeeId)
								.range(new DatePeriod(GeneralDate.localDate(b.startDate.toLocalDate()),
										GeneralDate.localDate(b.endDate.toLocalDate())))
								.build())
						.collect(Collectors.toList()))
				.employments(e.employmentHistorys.stream()
						.map(b -> HistoryCommonInfo.builder().code(b.employmentCode).employeeId(b.employeeId)
								.range(new DatePeriod(GeneralDate.localDate(b.startDate.toLocalDate()),
										GeneralDate.localDate(b.endDate.toLocalDate()))).build())
						.collect(Collectors.toList()))
				.jobTitles(e.jobTitleHistorys.stream()
						.map(b -> HistoryCommonInfo.builder().code(b.jobId).employeeId(b.employeeId)
								.range(new DatePeriod(GeneralDate.localDate(b.startDate.toLocalDate()),
										GeneralDate.localDate(b.endDate.toLocalDate()))).build())
						.collect(Collectors.toList()))
				.workplaces(e.workPlaceHistorys.stream()
						.map(b -> HistoryCommonInfo.builder().code(b.workplaceId).employeeId(b.employeeId)
								.range(new DatePeriod(GeneralDate.localDate(b.startDate.toLocalDate()),
										GeneralDate.localDate(b.endDate.toLocalDate()))).build())
						.collect(Collectors.toList()))
				.build()).collect(Collectors.toList());
	}

}
