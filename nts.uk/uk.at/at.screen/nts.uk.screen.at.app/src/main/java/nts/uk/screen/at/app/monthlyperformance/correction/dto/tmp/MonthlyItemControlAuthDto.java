package nts.uk.screen.at.app.monthlyperformance.correction.dto.tmp;

import java.util.List;

import lombok.Data;

/**
 * 権限別月次項目制御
 *
 */
@Data
public class MonthlyItemControlAuthDto {
	/**
	 * 会社ID
	 */
	String cId;
	/**
	 * ロールID
	 */
	String rollId;
	/**
	 * 月次の勤怠項目の表示・入力制御
	 */
	List<MonthlyItemInputDto> lstInputItem;
}
