/**
 * 3:00:49 PM Dec 4, 2017
 */
package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hungnm
 *
 */
@Setter
@Getter
public class ErAlAtdItemConditionDto {
	private int targetNO;
	private int conditionAtr;
	private boolean useAtr;
	private int uncountableAtdItem;
	private List<Integer> countableAddAtdItems;
	private List<Integer> countableSubAtdItems;
	private int conditionType;
	private int compareOperator;
	private int singleAtdItem;
	private BigDecimal compareStartValue;
	private BigDecimal compareEndValue;
	private Integer inputCheckCondition;
	
	public ErAlAtdItemConditionDto() {
		super();
	}

	public ErAlAtdItemConditionDto(int targetNO, int conditionAtr, boolean useAtr, Integer inputCheckCondition, int uncountableAtdItem,
			List<Integer> countableAddAtdItems, List<Integer> countableSubAtdItems, int conditionType,
			int compareOperator, int singleAtdItem, BigDecimal compareStartValue, BigDecimal compareEndValue) {
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

