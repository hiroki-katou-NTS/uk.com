package nts.uk.ctx.exio.dom.exi.condset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;

/**
 * 受入選別条件設定
 */
@Getter
public class AcScreenCondSet extends DomainObject {

	/**
	 * 条件設定コード
	 */
	private AcceptanceConditionCode conditionSetCd;

	/**
	 * 受入項目の番号
	 */
	private AcceptanceLineNumber acceptItemNum;

	/**
	 * 比較条件選択
	 */
	private SelectComparisonCondition selectComparisonCondition;

	/**
	 * 時間‗条件値2
	 */
	private Optional<AcceptanceConditionTime> timeConditionValue2;

	/**
	 * 時間‗条件値1
	 */
	private Optional<AcceptanceConditionTime> timeConditionValue1;

	/**
	 * 時刻‗条件値2
	 */
	private Optional<AcceptanceConditionTimeMoment> timeMomentConditionValue2;

	/**
	 * 時刻‗条件値1
	 */
	private Optional<AcceptanceConditionTimeMoment> timeMomentConditionValue1;

	/**
	 * 日付‗条件値2
	 */
	private Optional<GeneralDate> dateConditionValue2;

	/**
	 * 日付‗条件値1
	 */
	private Optional<GeneralDate> dateConditionValue1;

	/**
	 * 文字‗条件値2
	 */
	private Optional<AcceptanceConditionString> characterConditionValue2;

	/**
	 * 文字‗条件値1
	 */
	private Optional<AcceptanceConditionString> characterConditionValue1;

	/**
	 * 数値‗条件値2
	 */
	private Optional<AcceptanceConditionValue> numberConditionValue2;

	/**
	 * 数値‗条件値1
	 */
	private Optional<AcceptanceConditionValue> numberConditionValue1;

	public AcScreenCondSet(String conditionSetCd, int acceptItemNum, int selectComparisonCondition,
			Integer timeConditionValue1, Integer timeConditionValue2, Integer timeMomentConditionValue1,
			Integer timeMomentConditionValue2, GeneralDate dateConditionValue1, GeneralDate dateConditionValue2,
			String characterConditionValue1, String characterConditionValue2, BigDecimal numberConditionValue1,
			BigDecimal numberConditionValue2) {
		super();
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.acceptItemNum = new AcceptanceLineNumber(acceptItemNum);
		this.selectComparisonCondition = EnumAdaptor.valueOf(selectComparisonCondition, SelectComparisonCondition.class);
		this.timeConditionValue2 = Optional.ofNullable(timeConditionValue2 == null ? null
				: new AcceptanceConditionTime(timeConditionValue2));
		this.timeConditionValue1 = Optional.ofNullable(timeConditionValue1 == null ? null 
				: new AcceptanceConditionTime(timeConditionValue1));
		this.timeMomentConditionValue2 = Optional.ofNullable(timeMomentConditionValue2 == null ? null
				: new AcceptanceConditionTimeMoment(timeMomentConditionValue2));
		this.timeMomentConditionValue1 = Optional.ofNullable(timeMomentConditionValue1 == null ? null 
				: new AcceptanceConditionTimeMoment(timeMomentConditionValue1));
		this.dateConditionValue2 = Optional.ofNullable(dateConditionValue2);
		this.dateConditionValue1 = Optional.ofNullable(dateConditionValue1);
		this.characterConditionValue2 = Optional.ofNullable(characterConditionValue2 == null ? null 
				: new AcceptanceConditionString(characterConditionValue2));
		this.characterConditionValue1 = Optional.ofNullable(characterConditionValue1 == null ? null
				:new AcceptanceConditionString(characterConditionValue1));
		this.numberConditionValue2 = Optional.ofNullable(numberConditionValue2 == null ? null 
				: new AcceptanceConditionValue(numberConditionValue2));
		this.numberConditionValue1 = Optional.ofNullable(numberConditionValue1 == null ? null 
				: new AcceptanceConditionValue(numberConditionValue1));		
	}
	/**
	 * 受入条件の判定
	 * @param itemValue
	 * @param itemType
	 * @return
	 */
	public boolean checkCondNumber(Object itemValue, ItemType itemType) {
		Object condValue1 = null;
		Object condValue2 = null;
		boolean result = true;
		
		switch (itemType) {
			case DATE:
				condValue1 = this.dateConditionValue1.isPresent() ? this.dateConditionValue1.get() : null;
				condValue2 = this.dateConditionValue2.isPresent() ? this.dateConditionValue2.get() : null;
				break;
			case INS_TIME:
				condValue1 = this.timeMomentConditionValue1.isPresent() ? this.timeMomentConditionValue1.get() : null;
				condValue2 = this.timeMomentConditionValue2.isPresent() ? this.timeMomentConditionValue1.get() : null;
				break;
			case NUMERIC:
				condValue1 = this.numberConditionValue1.isPresent() ? this.numberConditionValue1.get() : null;
				condValue2 = this.numberConditionValue2.isPresent() ? this.numberConditionValue2.get() : null;
				break;
			case TIME:
				condValue1 = this.timeConditionValue1.isPresent() ? this.timeConditionValue1.get() : null;
				condValue2 = this.timeConditionValue2.isPresent() ? this.timeConditionValue2.get() : null;
				break;
			case CHARACTER:
				condValue1 = this.characterConditionValue1.isPresent() ? this.characterConditionValue1.get() : null;
			default:
				break;
		}
		if (condValue1 == null && condValue2 == null) return result;
		if (condValue1 == null && itemType == ItemType.CHARACTER) return result;
		
		GeneralDate condValueD1 = null;
		GeneralDate condValueD2 = null;
		GeneralDate valueItemD = null;
		Double condValueDb1 = null;
		Double condValueDb2 = null;
		Double itemValueDb = null;
		String condValueS1 = null;
		String valueItemS = null;
		if(itemType == ItemType.DATE) {
			condValueD1 = (GeneralDate) condValue1;
			condValueD2 = (GeneralDate) condValue2;
			valueItemD = (GeneralDate) itemValue;
		} else if (itemType == ItemType.CHARACTER) {
			condValueS1 = condValue1.toString();
			valueItemS = itemValue.toString();
		} else {
			condValueDb1 = (Double) condValue1;
			condValueDb2 = (Double) condValue2;
			itemValueDb = (Double) itemValue;
		}
		switch (this.selectComparisonCondition) {		
		case COND1_LESS_VAL:
			if(itemType == ItemType.DATE) {
				result = condValueD1.before(valueItemD);
			} else {
				result = condValueDb1 < itemValueDb;	
			}
			break;
		case COND1_LESS_EQUAL_VAL:
			if(itemType == ItemType.DATE) {
				result = condValueD1.beforeOrEquals(valueItemD);
			} else {
				result = condValueDb1 <= itemValueDb;	
			}
			
			break;
		case VAL_LESS_COND1:
			if(itemType == ItemType.DATE) {
				result = valueItemD.before(condValueD1);
			} else {
				result = itemValueDb < condValueDb1;
			}
			break;
		case VAL_LESS_EQUAL_COND1:
			if(itemType == ItemType.DATE) {
				result = valueItemD.beforeOrEquals(condValueD1);
			} else {
				result = itemValueDb <= condValueDb1;	
			}
			break;
		case COND1_LESS_VAL_AND_VAL_LESS_COND2://条件値1　＜　値　かつ　　値　＜　条件値2 
			if(itemType == ItemType.DATE) {
				result = condValueD1.before(valueItemD) && valueItemD.before(condValueD2);
			} else {
				result = condValueDb1 < itemValueDb && itemValueDb < condValueDb2;
			}
			break;
		case COND1_LESS_EQUAL_VAL_AND_VAL_LESS_EQUAL_COND2: //条件値1　≦　値　かつ　　値　≦　条件値2 
			if(itemType == ItemType.DATE) {
				result = condValueD1.beforeOrEquals(valueItemD) && valueItemD.beforeOrEquals(condValueD2);
			} else {
				result = condValueDb1 <= itemValueDb && itemValueDb <= condValueDb2;
			}
			break;
		case VAL_LESS_COND1_OR_COND2_LESS_VAL: //値　＜　条件値1　または　　条件値2　＜　値 
			if(itemType == ItemType.DATE) {
				result = condValueD1.before(valueItemD) || valueItemD.before(condValueD2);
			} else {
				result = condValueDb1 < itemValueDb || itemValueDb < condValueDb2;
			}
			break;
		case VAL_LESS_EQUAL_COND1_OR_COND2_LESS_EQUAL_VAL: //値　≦　条件値1　または　　条件値2　≦　値 
			if(itemType == ItemType.DATE) {
				result = condValueD1.beforeOrEquals(valueItemD) || valueItemD.beforeOrEquals(condValueD2);
			} else {
				result = condValueDb1 <= itemValueDb || itemValueDb <= condValueDb2;
			}
			break;
		case COND1_EQUAL_VAL: //条件値1　＝　値
			if(itemType == ItemType.DATE) {
				result = valueItemD.equals(condValueD1);
			} else if (itemType == ItemType.CHARACTER) {
				result = valueItemS.equals(condValueS1);
			} else {
				result = itemValueDb == condValueDb1;	
			}
			break;
		case COND1_NOT_EQUAL_VAL: //条件値1　≠　　値 
			if(itemType == ItemType.DATE) {
				result = !valueItemD.equals(condValueD1);
			} else if (itemType == ItemType.CHARACTER) {
				result = !valueItemS.equals(condValueS1);
			} else {
				result = itemValueDb != condValueDb1;	
			}
			break;
		default:
			break;
		}
		return result;
	}
}
