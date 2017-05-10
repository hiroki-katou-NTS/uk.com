package nts.uk.ctx.sys.portal.app.command.toppage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Class CopyTopPageCommand.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CopyTopPageCommand extends TopPageBaseCommand{
	
	/** The is check overwrite. */
	public boolean isCheckOverwrite;
	
	/** The copy code. */
	public String copyCode;
}
