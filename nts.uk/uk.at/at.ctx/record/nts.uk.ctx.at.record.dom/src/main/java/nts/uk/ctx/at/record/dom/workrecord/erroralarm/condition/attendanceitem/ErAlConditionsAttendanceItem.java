/**
 * 4:43:53 PM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
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

	public WorkCheckResult check(Function<List<Integer>, List<Double>> getValueFromItemIds) {
		if(isNotUseAll()){
			return WorkCheckResult.NOT_CHECK;
		}
		switch (this.conditionOperator) {
		case AND:
			return evaluate(checkStream(getValueFromItemIds).allMatch(r -> r));
		case OR:
			return evaluate(checkStream(getValueFromItemIds).anyMatch(r -> r));
		default:
			throw new RuntimeException("invalid conditionOperator: " + conditionOperator);
		}
	}
	
	private WorkCheckResult evaluate(boolean flag){
		if(flag){
			return WorkCheckResult.ERROR;
		}
		
		return WorkCheckResult.NOT_ERROR;
	}

	private Stream<Boolean> checkStream(Function<List<Integer>, List<Double>> getValueFromItemIds) {
		return lstErAlAtdItemCon.stream().filter(aic -> aic.isUse()).map(aic -> {
			return aic.checkTarget(getValueFromItemIds);
		});
	}

	public ErAlConditionsAttendanceItem(String atdItemConGroupId, LogicalOperator conditionOperator, List<ErAlAttendanceItemCondition<?>> lstErAlAtdItemCon) {
		super();
		this.atdItemConGroupId = atdItemConGroupId;
		this.conditionOperator = conditionOperator;
		this.lstErAlAtdItemCon = lstErAlAtdItemCon;
	}

	private boolean isNotUseAll(){
		return !lstErAlAtdItemCon.stream().filter(aic -> aic.isUse()).findFirst().isPresent();
	}
}
