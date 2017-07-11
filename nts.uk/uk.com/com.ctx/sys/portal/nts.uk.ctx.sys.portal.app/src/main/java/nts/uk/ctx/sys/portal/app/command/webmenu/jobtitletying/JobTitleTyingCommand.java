package nts.uk.ctx.sys.portal.app.command.webmenu.jobtitletying;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobTitleTyingCommand {
	private String jobId;
	private String webMenuCode;
}
