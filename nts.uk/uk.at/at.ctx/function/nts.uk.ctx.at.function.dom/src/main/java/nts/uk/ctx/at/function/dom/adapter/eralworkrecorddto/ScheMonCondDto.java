package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheMonCondDto {
	/* チェックする対比 | チェックする日数*/
	private int scheCheckCondition;
	
	/* 比較演算子 */
	private int comparisonOperator;
	
	/* 範囲との比較．開始値 */
	private Double compareStartValue;
	
	/* 範囲との比較．終了値 */
	private Double compareEndValue;
	
	/* スケジュール月次の残数チェック.特別休暇 */
	private int specialHolidayCode; 
	
	private List<Integer> countableAddAtdItems;
	private List<Integer> countableSubAtdItems;
}
