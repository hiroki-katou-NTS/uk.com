/**
 * 4:39:03 PM Oct 19, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class UpdateColWidthCommand {

	private Map<Integer, Integer> lstHeader;

	public UpdateColWidthCommand() {
		super();
	}

	public UpdateColWidthCommand(Map<Integer, Integer> lstHeader) {
		super();
		this.lstHeader = lstHeader;
	}
	
}
