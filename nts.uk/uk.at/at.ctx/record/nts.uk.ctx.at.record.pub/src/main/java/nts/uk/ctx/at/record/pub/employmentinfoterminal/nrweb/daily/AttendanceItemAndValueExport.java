package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.daily;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         勤怠項目と値
 */
@Getter
public class AttendanceItemAndValueExport {

	// NO
	private int no;

	// 勤怠項目ID
	private int attendanceItemId;

	// 勤怠項目名称
	private String name;

	// 値
	private String value;

	// 日別勤怠の編集状態
	private int state;

	public AttendanceItemAndValueExport(int no, int attendanceItemId, String name, String value, int state) {
		this.no = no;
		this.attendanceItemId = attendanceItemId;
		this.name = name;
		this.value = value;
		this.state = state;
	}

}
