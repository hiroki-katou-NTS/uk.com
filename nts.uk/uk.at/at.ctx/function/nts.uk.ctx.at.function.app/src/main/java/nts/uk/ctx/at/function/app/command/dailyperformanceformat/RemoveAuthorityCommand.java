package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class RemoveAuthorityCommand {
	
	private String dailyPerformanceFormatCode;
	
	private int isDefaultInitial;

}
