package nts.uk.screen.at.app.command.kdp.kdp003.m;

import java.util.List;

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
public class SelectingWorkplacesForStampInputDto {

	public SWkpHistExport sWkpHistExport = new SWkpHistExport();

	public List<WorkPlaceInfo> workPlaceInfo;

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	class SWkpHistExport {

		// 職場ID
		public String workplaceId;

		/** The workplace code. */
		public String workplaceCode;

		/** The workplace name. */
		public String workplaceName;

		/** The wkp display name. */
		// 職場表示名
		public String wkpDisplayName;
	}
}
