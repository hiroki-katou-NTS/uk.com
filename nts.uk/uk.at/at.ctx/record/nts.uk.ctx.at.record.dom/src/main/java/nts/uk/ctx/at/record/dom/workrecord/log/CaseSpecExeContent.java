/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import javax.persistence.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;

/**
 * ケース別実行内容
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
public class CaseSpecExeContent extends AggregateRoot {

	/**
	 * ID (運用ケース)
	 */
	private String caseSpecExeContentID;

	/**
	 * 並び順
	 */
	private int orderNumber;

	/**
	 * 運用ケース名
	 */
	private UseCaseName useCaseName;
	/**
	 * 設定情報
	 */
	private CalExeSettingInfor settingInformation;
	
	public static CaseSpecExeContent createFromJavaType(
			String caseSpecExeContentID,
			int orderNumber,
			String useCaseName,
			int executionContent,
			int executionType
			) {
		return new CaseSpecExeContent(
				caseSpecExeContentID,
				orderNumber,
				new UseCaseName(useCaseName),
				new CalExeSettingInfor(
						EnumAdaptor.valueOf(executionContent, ExecutionContent.class),
						EnumAdaptor.valueOf(executionType, ExecutionType.class)
						)
				);
		
	}

}
