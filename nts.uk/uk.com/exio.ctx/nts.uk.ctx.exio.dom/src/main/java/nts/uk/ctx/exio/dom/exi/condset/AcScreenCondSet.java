package nts.uk.ctx.exio.dom.exi.condset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.type.numeric.real.ImportingConditionReal;
import nts.uk.ctx.exio.dom.exi.condset.type.string.ImportingConditionString;
import nts.uk.ctx.exio.dom.exi.condset.type.time.ImportingConditionTime;
import nts.uk.ctx.exio.dom.exi.condset.type.time.ImportingConditionTimeMoment;
import nts.uk.ctx.exio.dom.input.revise.dataformat.ItemType;

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
	private CompareValueCondition selectComparisonCondition;

	/**
	 * 時間‗条件値1
	 */
	private Optional<ImportingConditionTime> timeConditionValue1;
	
	/**
	 * 時間‗条件値2
	 */
	private Optional<ImportingConditionTime> timeConditionValue2;

	/**
	 * 時刻‗条件値1
	 */
	private Optional<ImportingConditionTimeMoment> timeMomentConditionValue1;
	
	/**
	 * 時刻‗条件値2
	 */
	private Optional<ImportingConditionTimeMoment> timeMomentConditionValue2;

	/**
	 * 日付‗条件値1
	 */
	private Optional<GeneralDate> dateConditionValue1;
	
	/**
	 * 日付‗条件値2
	 */
	private Optional<GeneralDate> dateConditionValue2;

	/**
	 * 文字‗条件値1
	 */
	private Optional<ImportingConditionString> characterConditionValue1;
	
	/**
	 * 文字‗条件値2
	 */
	private Optional<ImportingConditionString> characterConditionValue2;

	/**
	 * 数値‗条件値1
	 */
	private Optional<ImportingConditionReal> numberConditionValue1;
	
	/**
	 * 数値‗条件値2
	 */
	private Optional<ImportingConditionReal> numberConditionValue2;

	public AcScreenCondSet(String conditionSetCd, int acceptItemNum, int selectComparisonCondition,
			Integer timeConditionValue1, Integer timeConditionValue2, Integer timeMomentConditionValue1,
			Integer timeMomentConditionValue2, GeneralDate dateConditionValue1, GeneralDate dateConditionValue2,
			String characterConditionValue1, String characterConditionValue2, BigDecimal numberConditionValue1,
			BigDecimal numberConditionValue2) {
		super();
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.acceptItemNum = new AcceptanceLineNumber(acceptItemNum);
		this.selectComparisonCondition = EnumAdaptor.valueOf(selectComparisonCondition, CompareValueCondition.class);
		this.timeConditionValue2 = Optional.ofNullable(timeConditionValue2 == null ? null
				: new ImportingConditionTime(timeConditionValue2));
		this.timeConditionValue1 = Optional.ofNullable(timeConditionValue1 == null ? null 
				: new ImportingConditionTime(timeConditionValue1));
		this.timeMomentConditionValue2 = Optional.ofNullable(timeMomentConditionValue2 == null ? null
				: new ImportingConditionTimeMoment(timeMomentConditionValue2));
		this.timeMomentConditionValue1 = Optional.ofNullable(timeMomentConditionValue1 == null ? null 
				: new ImportingConditionTimeMoment(timeMomentConditionValue1));
		this.dateConditionValue2 = Optional.ofNullable(dateConditionValue2);
		this.dateConditionValue1 = Optional.ofNullable(dateConditionValue1);
		this.characterConditionValue2 = Optional.ofNullable(characterConditionValue2 == null ? null 
				: new ImportingConditionString(characterConditionValue2));
		this.characterConditionValue1 = Optional.ofNullable(characterConditionValue1 == null ? null
				:new ImportingConditionString(characterConditionValue1));
		this.numberConditionValue2 = Optional.ofNullable(numberConditionValue2 == null ? null 
				: new ImportingConditionReal(numberConditionValue2));
		this.numberConditionValue1 = Optional.ofNullable(numberConditionValue1 == null ? null 
				: new ImportingConditionReal(numberConditionValue1));		
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
			case INT:
			case REAL:
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
				throw new RuntimeException("実装が存在しない実装型です。:"+ itemType);
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
				return condValueD1.before(valueItemD);
			}
			return condValueDb1 < itemValueDb;	
		case COND1_LESS_EQUAL_VAL:
			if(itemType == ItemType.DATE) {
				return condValueD1.beforeOrEquals(valueItemD);
			}
			return condValueDb1 <= itemValueDb;	
		case VAL_LESS_COND1:
			if(itemType == ItemType.DATE) {
				return valueItemD.before(condValueD1);
			}
			return itemValueDb < condValueDb1;
		case VAL_LESS_EQUAL_COND1:
			if(itemType == ItemType.DATE) {
				return valueItemD.beforeOrEquals(condValueD1);
			}
			return itemValueDb <= condValueDb1;	
		case COND1_LESS_VAL_AND_VAL_LESS_COND2://条件値1　＜　値　かつ　　値　＜　条件値2 
			if(itemType == ItemType.DATE) {
				return condValueD1.before(valueItemD) && valueItemD.before(condValueD2);
			}
			return condValueDb1 < itemValueDb && itemValueDb < condValueDb2;
		case COND1_LESS_EQUAL_VAL_AND_VAL_LESS_EQUAL_COND2: //条件値1　≦　値　かつ　　値　≦　条件値2 
			if(itemType == ItemType.DATE) {
				return condValueD1.beforeOrEquals(valueItemD) && valueItemD.beforeOrEquals(condValueD2);
			}
			return condValueDb1 <= itemValueDb && itemValueDb <= condValueDb2;
		case VAL_LESS_COND1_OR_COND2_LESS_VAL: //値　＜　条件値1　または　　条件値2　＜　値 
			if(itemType == ItemType.DATE) {
				return condValueD1.before(valueItemD) || valueItemD.before(condValueD2);
			}
			return condValueDb1 < itemValueDb || itemValueDb < condValueDb2;
		case VAL_LESS_EQUAL_COND1_OR_COND2_LESS_EQUAL_VAL: //値　≦　条件値1　または　　条件値2　≦　値 
			if(itemType == ItemType.DATE) {
				return condValueD1.beforeOrEquals(valueItemD) || valueItemD.beforeOrEquals(condValueD2);
			}
			return condValueDb1 <= itemValueDb || itemValueDb <= condValueDb2;
		case COND1_EQUAL_VAL: //条件値1　＝　値
			if(itemType == ItemType.DATE) {
				return valueItemD.equals(condValueD1);
			} else if (itemType == ItemType.CHARACTER) {
				return valueItemS.equals(condValueS1);
			}
			return itemValueDb == condValueDb1;	
		case COND1_NOT_EQUAL_VAL: //条件値1　≠　　値 
			if(itemType == ItemType.DATE) {
				return !valueItemD.equals(condValueD1);
			} else if (itemType == ItemType.CHARACTER) {
				return !valueItemS.equals(condValueS1);
			} 
			return itemValueDb != condValueDb1;	
		default:
			throw new RuntimeException("実装が存在しない比較条件です。:" + this.selectComparisonCondition);
		}
	}
}
