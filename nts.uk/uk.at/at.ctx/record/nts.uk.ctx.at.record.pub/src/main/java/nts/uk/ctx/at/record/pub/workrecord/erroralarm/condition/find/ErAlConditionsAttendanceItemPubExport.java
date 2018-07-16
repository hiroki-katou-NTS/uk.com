package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import java.util.List;

import lombok.Setter;
import lombok.Value;

/**
 * @author hieunv
 *
 */
@Value
@Setter
public class ErAlConditionsAttendanceItemPubExport {
	private String atdItemConGroupId;

	// 条件間の演算子
	private int conditionOperator;

	// 複合エラーアラーム条件 ErAlAtdItemConditionPubExport
	private List<ErAlAtdItemConditionPubExport> lstErAlAtdItemCon;
}
