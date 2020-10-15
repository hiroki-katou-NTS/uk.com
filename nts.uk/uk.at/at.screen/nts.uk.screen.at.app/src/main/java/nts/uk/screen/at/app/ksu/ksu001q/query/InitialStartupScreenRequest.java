package nts.uk.screen.at.app.ksu.ksu001q.query;

import lombok.Data;

@Data
public class InitialStartupScreenRequest {

	/** 種類 */
	private String unit;

	/** ID */
	private String id;

	/** 終了日 */
	private String endDate;

}
