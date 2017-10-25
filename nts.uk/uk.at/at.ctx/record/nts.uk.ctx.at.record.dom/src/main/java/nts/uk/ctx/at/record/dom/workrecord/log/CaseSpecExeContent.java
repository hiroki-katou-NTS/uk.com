/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * ケース別実行内容
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
public class CaseSpecExeContent extends AggregateRoot {

	/**
	 * ID
	 */
	private long caseSpecExeContentID;

	/**
	 * 並び順
	 */
	private int orderNumber;

	/**
	 * 運用ケース名
	 */
	private String useCaseName;
	/**
	 * 設定情報
	 */
	private CalExeSettingInfor settingInformation;

}
