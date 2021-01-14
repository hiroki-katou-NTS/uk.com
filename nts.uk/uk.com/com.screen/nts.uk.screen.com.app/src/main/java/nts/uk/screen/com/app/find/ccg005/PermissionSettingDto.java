package nts.uk.screen.com.app.find.ccg005;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;

@Data
@Builder
public class PermissionSettingDto {
	
	//	List<在席照会で参照できる権限の指定>
	List<SpecifyAuthInquiry> specifyAuthInquiry;
	
	//	List<ロール>
	List<RoleDto> role;
	
	//	List<職位情報>
	List<JobTitleExport> jobTitle; 
}
