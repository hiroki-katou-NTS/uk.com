package nts.uk.screen.at.app.command.kdp.kdp003.m;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkPlaceInfo {
	/** 職場ID */
	public String workplaceId;

	public String hierarchyCode;

	/** 職場コード */
	public String workplaceCode;

	/** 職場名称 */
	public String workplaceName;

	public String displayName;

	public String genericName;

	public String externalCode;
}
