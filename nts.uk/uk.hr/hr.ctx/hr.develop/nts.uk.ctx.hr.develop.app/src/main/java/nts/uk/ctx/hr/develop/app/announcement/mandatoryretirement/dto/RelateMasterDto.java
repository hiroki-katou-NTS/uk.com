package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmmMastItImport;

@Data
public class RelateMasterDto {
	
	private String commonMasterName;
	
	private List<GrpCmmMastItImport> commonMasterItemList;
	
	private List<RetirePlanCourceDto> retirePlanCourseList;

	public RelateMasterDto(String commonMasterName, List<GrpCmmMastItImport> commonMasterItemList, List<RetirePlanCourceDto> retirePlanCourseList) {
		super();
		this.commonMasterName = commonMasterName;
		this.commonMasterItemList = commonMasterItemList;
		this.retirePlanCourseList = retirePlanCourseList;
	}
}
