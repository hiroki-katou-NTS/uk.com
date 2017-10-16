/**
 * 5:16:15 PM Aug 28, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class DPErrorSettingDto {

	private String companyId;

	private String errorAlarmCode;

	private String errorAlarmName;

	private boolean fixedAtr;

	private boolean useAtr;

	private Integer typeAtr;

	private String messageDisplay;

	private boolean boldAtr;

	private String messageColor;

	private boolean cancelableAtr;

	private Integer errorDisplayItem;

}
