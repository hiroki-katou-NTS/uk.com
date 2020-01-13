package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmonMasterImport;

@Setter
@Getter
@NoArgsConstructor
public class RelateMasterDto extends GrpCmonMasterImport{
	
	private List<RetirePlanCourceDto> retirePlanCourseList;

	public RelateMasterDto(GrpCmonMasterImport commonMasterImport, List<RetirePlanCourceDto> retirePlanCourseList) {
		super(commonMasterImport.getCommonMasterName(), commonMasterImport.getCommonMasterItems());
		this.retirePlanCourseList = retirePlanCourseList;
	}
	
}
