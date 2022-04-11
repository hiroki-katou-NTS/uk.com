package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.month;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         勤怠項目と値
 */
@Getter
public class AttItemValueMonthExport {

	// NO
	private int no;

	// 勤怠項目ID
	private int attendanceItemId;

	// 勤怠項目名称
	private String name;

	// 値
	private String value;

	public AttItemValueMonthExport(int no, int attendanceItemId, String name, String value) {
		this.no = no;
		this.attendanceItemId = attendanceItemId;
		this.name = name;
		this.value = value;
	}

}
