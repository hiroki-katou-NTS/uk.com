package nts.uk.ctx.sys.auth.app.find._role;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InforRoleSetJobDto {

	private List<RoleTypeDto> roleTypeDtos;
	
	private List<RoleDto> roleDtos;
	
	private List<RoleIndividualGrantDto> roleIndividualGrantDtos;
}
