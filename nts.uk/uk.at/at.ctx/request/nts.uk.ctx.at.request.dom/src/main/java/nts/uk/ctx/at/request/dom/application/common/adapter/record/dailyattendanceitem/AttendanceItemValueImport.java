package nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Anh.BD
 *
 */
@Value
@AllArgsConstructor
public class AttendanceItemValueImport {
	private String value;

	/** INTEGER(0, "INTEGER", "回数、時間、時刻"),
	STRING(1, "STRING", "コード、文字"),
	DECIMAL(2, "DECIMAL", "回数"),
	DATE(3, "DATE", "年月日"), */
	private int valueType;
	
	private int itemId;
}
