/**
 * 4:43:53 PM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;

/**
 * @author hungnm
 *
 */
// 勤怠項目の複合エラーアラーム条件
@Getter
public class ErAlConditionsAttendanceItem extends DomainObject {

	private String atdItemConGroupId;

	// 条件間の演算子
	private LogicalOperator conditionOperator;

	// 複合エラーアラーム条件
	private List<ErAlAttendanceItemCondition<?>> lstErAlAtdItemCon;

	private ErAlConditionsAttendanceItem(LogicalOperator conditionOperator) {
		super();
		this.atdItemConGroupId = IdentifierUtil.randomUniqueId();
		this.conditionOperator = conditionOperator;
		this.lstErAlAtdItemCon = new ArrayList<>();
	}

	private ErAlConditionsAttendanceItem(String atdItemConGroupId, LogicalOperator conditionOperator) {
		super();
		this.atdItemConGroupId = atdItemConGroupId;
		this.conditionOperator = conditionOperator;
		this.lstErAlAtdItemCon = new ArrayList<>();
	}

	/** Init from Java type */
	public static ErAlConditionsAttendanceItem init(int conditionOperator) {
		return new ErAlConditionsAttendanceItem(EnumAdaptor.valueOf(conditionOperator, LogicalOperator.class));
	}
	
	/** Init from Java type */
	public static ErAlConditionsAttendanceItem init(LogicalOperator conditionOperator) {
		return new ErAlConditionsAttendanceItem(conditionOperator);
	}

	/** Create from Java type */
	public static ErAlConditionsAttendanceItem init(String atdItemConGroupId, int conditionOperator) {
		return new ErAlConditionsAttendanceItem(atdItemConGroupId,
				EnumAdaptor.valueOf(conditionOperator, LogicalOperator.class));
	}

	/** Create from Java type */
	public static ErAlConditionsAttendanceItem init(String atdItemConGroupId, LogicalOperator conditionOperator) {
		return new ErAlConditionsAttendanceItem(atdItemConGroupId, conditionOperator);
	}
	
	public void addAtdItemConditions(List<ErAlAttendanceItemCondition<?>> conditions) {
		this.lstErAlAtdItemCon.addAll(conditions);
	}
	
	public void addAtdItemConditions(ErAlAttendanceItemCondition<?> conditions) {
		this.lstErAlAtdItemCon.add(conditions);
	}

	public void setGroupId(String atdItemConGroupId) {
		this.atdItemConGroupId = atdItemConGroupId;
	}

	public boolean check(Function<List<Integer>, List<Integer>> getValueFromItemIds) {
		switch (this.conditionOperator) {
		case AND:
			return lstErAlAtdItemCon.stream().filter(aic -> aic.getUseAtr() != null && aic.getUseAtr()).map(aic -> {
				return aic.checkTarget(getValueFromItemIds);
			}).allMatch(r -> r);
		case OR:
			return lstErAlAtdItemCon.stream().map(aic -> {
				return aic.checkTarget(getValueFromItemIds);
			}).anyMatch(r -> r);
		default:
			throw new RuntimeException("invalid conditionOperator: " + conditionOperator);
		}
	}
}
