package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerZipfileValidateStatusDto{
	public boolean status;
	public String message;
}