package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieunv
 *
 */
@Getter
@Setter
public class AttendanceItemConditionPubExport {
	// グループ間の演算子
	private int operatorBetweenGroups;

	// グループ1
	private ErAlConditionsAttendanceItemPubExport group1;

	// グループ2
	private ErAlConditionsAttendanceItemPubExport group2;

	// グループ2を利用する
	private boolean group2UseAtr;
	public AttendanceItemConditionPubExport(int operatorBetweenGroups,
			ErAlConditionsAttendanceItemPubExport group1,
			ErAlConditionsAttendanceItemPubExport group2,
			boolean group2UseAtr) {
		this.operatorBetweenGroups = operatorBetweenGroups;
		this.group1 = group1;
		this.group2 = group2;
		this.group2UseAtr = group2UseAtr;
	}
	
}
