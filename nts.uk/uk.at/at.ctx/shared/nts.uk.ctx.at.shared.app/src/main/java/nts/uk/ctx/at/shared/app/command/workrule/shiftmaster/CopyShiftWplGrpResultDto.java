package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyShiftWplGrpResultDto {
	private List<ShiftGrpResultDto> shiftGrpResultDto;
	
	private List<DisInfoOrgDto> disInfoOrg;
}
