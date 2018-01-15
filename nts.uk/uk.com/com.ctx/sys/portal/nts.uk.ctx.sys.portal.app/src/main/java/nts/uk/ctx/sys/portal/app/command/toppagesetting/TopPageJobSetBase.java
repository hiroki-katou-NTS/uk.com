package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class TopPageJobSetBase {
	int ctgSet;
	List<UpdateTopPageJobSetCommand> listTopPageJobSet;
}
