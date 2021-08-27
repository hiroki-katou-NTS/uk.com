package nts.uk.ctx.at.record.app.command.knr.knr002.L;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class RegisterSwitchingDatesCommand {

	private String timeSwitchUKMode;
	
	private String empInfoTerminalCode;
}
