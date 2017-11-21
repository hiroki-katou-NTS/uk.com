package nts.uk.ctx.sys.auth.app.find.grant;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.auth.app.find.roleset.RoleSetDto;
import nts.uk.ctx.sys.auth.dom.employee.dto.JobTitleValueImport;

/**
 * 
 * @author HungTT
 *
 */

@AllArgsConstructor
@Data
public class GrantRoleSetJobDto {

	private List<RoleSetDto> listRoleSetDto;
	
	private RoleSetGrantedJobTitleDto roleSetGrantedJobTitleDto;
	
	private List<JobTitleValueImport> jobTitleDto;
}
