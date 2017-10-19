/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.log.usecase.CalExeSettingInfor;

/**
 * ケース別実行内容
 * @author danpv
 *
 */
@Getter
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
