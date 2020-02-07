package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeBasicInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeInformationImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.SearchCondition;

public interface SyEmploymentService {

	List<EmploymentInfoImport> getEmploymentInfo(String cid, Optional<Boolean> getEmploymentName,Optional<Boolean> getEmpExternalCode,
			Optional<Boolean> getMemo,Optional<Boolean> getempCommonMasterID,Optional<Boolean> getempCommonMasterItemID);
	
	List<EmployeeBasicInfoImport> getEmploymentBasicInfo(List<SearchCondition> searchCondition, GeneralDate baseDate, String cid);
	
	List<EmployeeInformationImport> getEmployeeInfor(List<String> employeeIds, GeneralDate referenceDate, boolean toGetWorkplace,
			boolean toGetDepartment, boolean toGetPosition, boolean toGetEmployment, boolean toGetClassification,
			boolean toGetEmploymentCls);
	
	List<EmploymentDateDto> getClosureDate(String companyId);
}
