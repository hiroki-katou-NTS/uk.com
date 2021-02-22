package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceItemDto {
	/**
	 * 勤怠項目ID
	 */
	private int attendanceItemId;

	/**
	 * 名称
	 */
	private String attendanceItemName;

	/**
	 * 属性
	 */
	private int attendanceAtr;

	/**
	 * マスタの種類
	 */
	private Integer masterType;

	/**
	 * 表示番号
	 */
	private int displayNumbers;
}
