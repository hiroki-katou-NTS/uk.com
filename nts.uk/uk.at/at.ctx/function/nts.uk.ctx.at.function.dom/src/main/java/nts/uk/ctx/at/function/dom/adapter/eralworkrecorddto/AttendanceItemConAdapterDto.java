package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceItemConAdapterDto {
	// グループ間の演算子
		private int operatorBetweenGroups;

		// グループ1
		private ErAlConAttendanceItemAdapterDto group1;

		// グループ2
		private ErAlConAttendanceItemAdapterDto group2;

		// グループ2を利用する
		private boolean group2UseAtr;

		public AttendanceItemConAdapterDto(int operatorBetweenGroups, ErAlConAttendanceItemAdapterDto group1,
				ErAlConAttendanceItemAdapterDto group2, boolean group2UseAtr) {
			super();
			this.operatorBetweenGroups = operatorBetweenGroups;
			this.group1 = group1;
			this.group2 = group2;
			this.group2UseAtr = group2UseAtr;
		}
		
		
}
