package nts.uk.screen.at.app.query.kdp.common;

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
public class GetWorkPlaceRegionalTimeDto {

	private String workPlaceId;

	/** コード */
	private String workLocationCD;

	/** 名称 */
	private String workLocationName;
	
	private int regional = 0;
	
}
