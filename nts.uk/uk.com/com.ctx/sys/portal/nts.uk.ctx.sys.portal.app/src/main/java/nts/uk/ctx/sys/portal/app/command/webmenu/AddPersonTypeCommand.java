package nts.uk.ctx.sys.portal.app.command.webmenu;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddPersonTypeCommand {
	
	private String webMenuCode;
	
	private String employeeId;
}
