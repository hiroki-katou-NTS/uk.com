package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 
 * @author hieult
 *
 */
public class DeleteShiftPalletComCommand {
	private String workplaceId;
	private int groupNo;
}
