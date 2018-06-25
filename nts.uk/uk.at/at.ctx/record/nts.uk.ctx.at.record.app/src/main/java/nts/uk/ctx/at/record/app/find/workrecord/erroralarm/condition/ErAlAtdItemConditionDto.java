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

}

