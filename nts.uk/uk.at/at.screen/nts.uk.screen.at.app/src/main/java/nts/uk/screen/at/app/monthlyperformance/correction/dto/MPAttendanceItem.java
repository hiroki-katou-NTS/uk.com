
package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MPAttendanceItem {
	private Integer id;
	private String name;
	private Integer displayNumber;
	private boolean userCanSet;
	private Integer lineBreakPosition;
	/*1:  時間 */
	/*2:  回数 */
	/*3:  日数*/
	/*4:  金額 */
	/*5:  マスタを参照する */
	/*6:  コード */
	/*7:  区分 */
	/*8:  比率 */
	/*9:  文字 */
	private Integer attendanceAtr;
	
	/**
	 * 1: 勤務種類 2: 就業時間帯 3: 勤務場所 4: 乖離理由 5: 職場 6: 分類 7: 職位 8: 雇用 9: するしない区分 10:
	 * 時間外の自動計算区分 11: 外出理由"
	 */
	private Integer typeGroup;
	
	private Integer primitive;
	
}
