package nts.uk.ctx.at.shared.ws.workrule.shiftmaster;

import lombok.Data;

/**
 * @author anhdt
 *
 */
@Data
public class FindShiftMasterDto {
	private String workplaceId;
	private String workplaceGroupId;
	private Integer targetUnit;
}
