package nts.uk.screen.com.app.smm.smm001.screenquery;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmileCooperationAcceptanceSettingDto {

	/**
	 * Name: Smile連携受入 
	 * Type: Smile連携受入項目
	 */
	private Integer cooperationAcceptance;

	/**
	 * Name: Smile連携受入区分 
	 * Type: Smile連携受入区分
	 */
	private Integer cooperationAcceptanceClassification;

	/**
	 * Name: Smile連携受入条件 
	 * Type: Optional 外部受入条件コード
	 */
	private String cooperationAcceptanceConditions;
}
