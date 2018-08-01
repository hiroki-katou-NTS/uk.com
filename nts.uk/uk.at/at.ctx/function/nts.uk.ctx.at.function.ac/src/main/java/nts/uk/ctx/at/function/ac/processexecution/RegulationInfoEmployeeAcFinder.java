package nts.uk.ctx.at.function.ac.processexecution;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapterImport;
import nts.uk.ctx.at.function.dom.adapter.SortingConditionOrderImport;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegularSortingType;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto.EmployeeSearchQueryDtoBuilder;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.query.pub.employee.SortingConditionOrderDto;

@Stateless
public class RegulationInfoEmployeeAcFinder implements RegulationInfoEmployeeAdapter {
	 
	@Inject
	private RegulationInfoEmployeePub egulationInfoEmployeePub;

	@Override
	public List<RegulationInfoEmployeeAdapterDto> find(RegulationInfoEmployeeAdapterImport r) {
		List<RegulationInfoEmployeeExport> regulationInfoEmployees = this.egulationInfoEmployeePub.find(this.convertEmployeeSearchQueryDto(r));
	return	regulationInfoEmployees.stream().map(x->convertDto(x)).collect(Collectors.toList());
	}
	
	
	private EmployeeSearchQueryDto convertEmployeeSearchQueryDto(RegulationInfoEmployeeAdapterImport r){
		 EmployeeSearchQueryDtoBuilder eBuilder = EmployeeSearchQueryDto.builder().baseDate(r.getBaseDate()).referenceRange(r.getReferenceRange()).filterByEmployment(r.getFilterByEmployment()).employmentCodes(r.getEmploymentCodes()).filterByDepartment(r.getFilterByDepartment()).departmentCodes(r.getDepartmentCodes()).filterByWorkplace(r.getFilterByWorkplace()).workplaceCodes(r.getWorkplaceCodes()).filterByClassification(r.getFilterByClassification()).classificationCodes(r.getClassificationCodes()).filterByJobTitle(r.getFilterByJobTitle()).jobTitleCodes(r.getJobTitleCodes()).filterByWorktype(r.getFilterByWorktype()).periodStart(GeneralDateTime.legacyDateTime(r.getPeriodStart().date())).periodEnd(GeneralDateTime.legacyDateTime(r.getPeriodEnd().date())).includeIncumbents(r.getIncludeIncumbents()).includeWorkersOnLeave(r.getIncludeWorkersOnLeave()).includeOccupancy(r.getIncludeOccupancy()).includeRetirees(r.getIncludeRetirees()).includeAreOnLoan(r.getIncludeAreOnLoan()).includeGoingOnLoan(r.getIncludeGoingOnLoan()).retireStart(GeneralDateTime.legacyDateTime(r.getRetireStart().date())).retireEnd(GeneralDateTime.legacyDateTime(r.getRetireEnd().date())).sortOrderNo(r.getSortOrderNo()).nameType(r.getNameType()).systemType(r.getSystemType()).filterByClosure(r.isFilterByClosure());
		 return eBuilder.build();
	}
	private RegulationInfoEmployeeAdapterDto convertDto(RegulationInfoEmployeeExport r){
		 RegulationInfoEmployeeAdapterDto regulationInfoEmployeeAdapterDto = new RegulationInfoEmployeeAdapterDto();
		 regulationInfoEmployeeAdapterDto.setEmployeeCode(r.getEmployeeCode());
		 regulationInfoEmployeeAdapterDto.setEmployeeId(r.getEmployeeId());
		 regulationInfoEmployeeAdapterDto.setEmployeeName(r.getEmployeeName());
		 regulationInfoEmployeeAdapterDto.setWorkplaceCode(r.getWorkplaceCode());
		 regulationInfoEmployeeAdapterDto.setWorkplaceId(r.getWorkplaceId());
		 regulationInfoEmployeeAdapterDto.setWorkplaceName(r.getWorkplaceName());
		 return regulationInfoEmployeeAdapterDto;
	}

	public List<String> sortEmployee(String comId, List<String> sIds, Integer systemType, Integer orderNo,
			Integer nameType, GeneralDateTime referenceDate) {
		return egulationInfoEmployeePub.sortEmployee(comId, sIds, systemType, orderNo, nameType, referenceDate);
	}

	@Override
	public List<String> sortEmployee(String comId, List<String> sIds, List<SortingConditionOrderImport> ordersImport,
			GeneralDateTime referenceDate) {
		List<SortingConditionOrderDto> orders = ordersImport.stream().map(x -> toSortingConditionOrderDto(x))
				.collect(Collectors.toList());
		return egulationInfoEmployeePub.sortEmployee(comId, sIds, orders, referenceDate);
	}

	private SortingConditionOrderDto toSortingConditionOrderDto(SortingConditionOrderImport orderImport) {
		SortingConditionOrderDto order = new SortingConditionOrderDto();
		order.setOrder(orderImport.getOrder());
		order.setType(EnumAdaptor.valueOf(orderImport.getType().value, RegularSortingType.class));
		return order;
	}
}
