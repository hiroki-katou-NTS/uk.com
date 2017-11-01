/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;

/**
 * @author danpv
 * 計算実行設定情報
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalExeSettingInfor extends DomainObject {
	

	/**
	 * 実行種別
	 * 
	 * 0 : 通常実行
	 * 1 : 再実行
	 */
	private ExecutionType executionType;

	public Object getExecutionContent() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
