/**
 * 4:39:03 PM Oct 19, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;
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
	
	private Map<Integer, Integer> lstHeaderMiGrid;

	private Map<Integer, Integer> lstHeader;
	
	private List<String> formatCode;

	public UpdateColWidthCommand() {
		super();
	}

	public UpdateColWidthCommand(Map<Integer, Integer> lstHeader, List<String> formatCode) {
		super();
		this.lstHeader = lstHeader;
		this.formatCode = formatCode;
	}
	
}
