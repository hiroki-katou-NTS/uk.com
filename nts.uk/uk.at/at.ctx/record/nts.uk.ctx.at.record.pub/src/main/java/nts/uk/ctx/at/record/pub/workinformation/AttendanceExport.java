package nts.uk.ctx.at.record.pub.workinformation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AttendanceExport {
	// 勤務NO
	private int workNo;
	
	// 場所コード
	private String locationCd;
	
	// 職場ID
	private String workplaceId;

}
