package nts.uk.ctx.at.function.pub.alarm.checkcondition.eventdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceItemConAdapterPubDto {
	// グループ間の演算子
		private int operatorBetweenGroups;

		// グループ1
		private ErAlConAttendanceItemAdapterPubDto group1;

		// グループ2
		private ErAlConAttendanceItemAdapterPubDto group2;

		// グループ2を利用する
		private boolean group2UseAtr;

		public AttendanceItemConAdapterPubDto(int operatorBetweenGroups, ErAlConAttendanceItemAdapterPubDto group1,
				ErAlConAttendanceItemAdapterPubDto group2, boolean group2UseAtr) {
			super();
			this.operatorBetweenGroups = operatorBetweenGroups;
			this.group1 = group1;
			this.group2 = group2;
			this.group2UseAtr = group2UseAtr;
		}
		
		
}
