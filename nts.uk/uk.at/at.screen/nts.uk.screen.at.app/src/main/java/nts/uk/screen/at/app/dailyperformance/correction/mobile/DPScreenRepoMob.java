/**
 * 2:14:20 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.util.List;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;

/**
 * @author ductm
 *
 */
public interface DPScreenRepoMob {

	public List<FormatDPCorrectionDto> getListFormatDPCorrection(List<String> lstBusinessType);

}
