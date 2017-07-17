package nts.uk.ctx.sys.portal.app.command.personaltying;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonTypingCommand {
	private String employeeId;
	private List<String> webMenuCodes;
}
