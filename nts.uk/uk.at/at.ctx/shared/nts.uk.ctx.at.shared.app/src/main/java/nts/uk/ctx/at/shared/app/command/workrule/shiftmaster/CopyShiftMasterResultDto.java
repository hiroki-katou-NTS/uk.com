package nts.uk.ctx.at.shared.app.command.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyShiftMasterResultDto {
	private String workplaceId;
	private Boolean status;
}
