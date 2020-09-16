package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftGrpResultDto {
	private String workplaceId;
	private Boolean status;
}
