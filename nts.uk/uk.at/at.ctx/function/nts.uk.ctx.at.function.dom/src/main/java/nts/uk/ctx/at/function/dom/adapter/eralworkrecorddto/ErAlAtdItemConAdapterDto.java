package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErAlAtdItemConAdapterDto {
	private int targetNO;
	private int conditionAtr;
	private boolean useAtr;
	private int uncountableAtdItem;
	/**	加算する勤怠項目一覧 */
	private List<Integer> countableAddAtdItems;
	/**減算する勤怠項目一覧	 */
	private List<Integer> countableSubAtdItems;
	private int conditionType;
	/**比較演算子：単一値との比較演算の種別、範囲との比較演算の種別 */
	private int compareOperator; //Enumを定義ほしい
	private int singleAtdItem;
	private BigDecimal compareStartValue;
	private BigDecimal compareEndValue;
	private Integer inputCheckCondition;

	public ErAlAtdItemConAdapterDto(int targetNO, int conditionAtr, boolean useAtr, int uncountableAtdItem,
			List<Integer> countableAddAtdItems, List<Integer> countableSubAtdItems, int conditionType,
			int compareOperator, int singleAtdItem, BigDecimal compareStartValue, BigDecimal compareEndValue,
			Integer inputCheckCondition) {
		super();
		this.targetNO = targetNO;
		this.conditionAtr = conditionAtr;
		this.useAtr = useAtr;
		this.uncountableAtdItem = uncountableAtdItem;
		this.countableAddAtdItems = countableAddAtdItems;
		this.countableSubAtdItems = countableSubAtdItems;
		this.conditionType = conditionType;
		this.compareOperator = compareOperator;
		this.singleAtdItem = singleAtdItem;
		this.compareStartValue = compareStartValue;
		this.compareEndValue = compareEndValue;
		this.inputCheckCondition = inputCheckCondition;
	}
}
