/**
 * 10:08:35 AM Nov 2, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * @author hungnm
 *
 */
//勤務実績のエラーアラームチェック
@Getter
public class ErrorAlarmCondition  extends AggregateRoot {
	
	/* 表示メッセージ */
	private DisplayMessage displayMessage;
	
	
}
