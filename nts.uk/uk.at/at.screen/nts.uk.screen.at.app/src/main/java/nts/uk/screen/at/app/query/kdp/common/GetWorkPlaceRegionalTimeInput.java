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
public class GetWorkPlaceRegionalTimeInput {

	// 契約コード
	private String ContractCode;
	
	// 会社ID
	private String cid;
	
	// 社員ID
	private String sid;

	// 職場ID
	private String workPlaceId;
}
