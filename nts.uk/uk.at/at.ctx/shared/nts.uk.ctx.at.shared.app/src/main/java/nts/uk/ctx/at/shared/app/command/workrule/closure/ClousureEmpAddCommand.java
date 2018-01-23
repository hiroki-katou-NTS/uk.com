package nts.uk.ctx.at.shared.app.command.workrule.closure;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.EmpCdNameDto;

@Getter
@Setter
public class ClousureEmpAddCommand {
	private List<EmpCdNameDto> empCdNameList;
}
