package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import lombok.Value;

/**
 * @author hieunv
 *
 */
@Value
public class AttendanceItemConditionPubExport {
	// グループ間の演算子
		private int operatorBetweenGroups;

		// グループ1
		private ErAlConditionsAttendanceItemPubExport group1;

		// グループ2
		private ErAlConditionsAttendanceItemPubExport group2;

		// グループ2を利用する
		private Boolean group2UseAtr;
}
