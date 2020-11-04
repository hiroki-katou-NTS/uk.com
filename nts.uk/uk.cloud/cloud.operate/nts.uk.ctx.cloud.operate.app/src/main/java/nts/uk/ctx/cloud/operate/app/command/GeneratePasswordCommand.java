package nts.uk.ctx.cloud.operate.app.command;

import lombok.Value;

@Value
public class GeneratePasswordCommand {
	String tenanteCode;
	String password;
}
