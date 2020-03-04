package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * 
 * @author hieult
 *
 */
public class InsertShiftPalletCommand {
	public int patternNo;
	public String patternName;
	
	public List<InsertShiftPalletCombinaCommand> listInsertWorkPairSetCommand;

	public InsertShiftPalletCommand(int patternNo, String patternName,
			List<InsertShiftPalletCombinaCommand> listInsertWorkPairSetCommand) {
		super();
		this.patternNo = patternNo;
		this.patternName = patternName;
		this.listInsertWorkPairSetCommand = listInsertWorkPairSetCommand;
	}
	
	
	

}
