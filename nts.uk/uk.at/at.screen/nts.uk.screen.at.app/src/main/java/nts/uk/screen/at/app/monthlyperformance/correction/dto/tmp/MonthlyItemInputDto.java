package nts.uk.screen.at.app.monthlyperformance.correction.dto.tmp;

import lombok.Data;

/**
 * 月次の勤怠項目の表示・入力制御
 *
 */
@Data
public class MonthlyItemInputDto {
	/**
	 * 勤怠項目ID
	 */
	int atendanceItemId;
	/**
	 * 利用する
	 */
	boolean useFlg;
	/**
	 * 入力制御
	 */
	boolean persionCanChange;
	/**
	 * 他人が変更できる
	 */
	boolean othersCanChange;
}
