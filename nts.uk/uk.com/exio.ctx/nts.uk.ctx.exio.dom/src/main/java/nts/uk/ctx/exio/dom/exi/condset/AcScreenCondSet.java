package nts.uk.ctx.exio.dom.exi.condset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

/**
* 受入選別条件設定
*/
@Getter
public class AcScreenCondSet extends DomainObject
{
    
    /**
    * 会社ID
    */
    private String cid;
    
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
    private Optional<SelectComparisonCondition> selectComparisonCondition;
    
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
    private Optional<AcceptanceConditionTime> timeMomentConditionValue2;
    
    /**
    * 時刻‗条件値1
    */
    private Optional<AcceptanceConditionTime> timeMomentConditionValue1;
    
    /**
    * 日付‗条件値2
    */
    private GeneralDate dateConditionValue2;
    
    /**
    * 日付‗条件値1
    */
    private GeneralDate dateConditionValue1;
    
    /**
    * 文字‗条件値2
    */
    private Optional<AcceptingConditionString> characterConditionValue2;
    
    /**
    * 文字‗条件値1
    */
    private Optional<AcceptingConditionString> characterConditionValue1;
    
    /**
    * 数値‗条件値2
    */
    private Optional<AcceptanceConditionValue> numberConditionValue2;
    
    /**
    * 数値‗条件値1
    */
    private Optional<AcceptanceConditionValue> numberConditionValue1;

	public AcScreenCondSet(String cid, String conditionSetCd, int acceptItemNum,
			Integer selectComparisonCondition,
			Integer timeConditionValue2,
			Integer timeConditionValue1,
			Integer timeMomentConditionValue2,
			Integer timeMomentConditionValue1, GeneralDate dateConditionValue2,
			GeneralDate dateConditionValue1, String characterConditionValue2,
			String characterConditionValue1,
			BigDecimal numberConditionValue2,
			BigDecimal numberConditionValue1) {
		super();
		this.cid = cid;
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.acceptItemNum = new AcceptanceLineNumber(acceptItemNum);
		if(selectComparisonCondition == null){
			this.selectComparisonCondition = Optional.empty();
		} else {
			this.selectComparisonCondition =  Optional.of(EnumAdaptor.valueOf(selectComparisonCondition, SelectComparisonCondition.class));
		}
		if (timeConditionValue2 == null) {
			this.timeConditionValue2 = Optional.empty();
		} else {
			this.timeConditionValue2 = Optional.of(new AcceptanceConditionTime(timeConditionValue2));
		}
		if (timeConditionValue1 == null) {
			this.timeConditionValue1 = Optional.empty();
		} else {
			this.timeConditionValue1 = Optional.of(new AcceptanceConditionTime(timeConditionValue1));
		}
		if (timeMomentConditionValue2 == null) {
			this.timeMomentConditionValue2 = Optional.empty();
		} else {
			this.timeMomentConditionValue2 = Optional.of(new AcceptanceConditionTime(timeMomentConditionValue2));
		}
		if (timeMomentConditionValue1 == null) {
			this.timeMomentConditionValue1 = Optional.empty();
		} else {
			this.timeMomentConditionValue1 = Optional.of(new AcceptanceConditionTime(timeMomentConditionValue1));
		}
		this.dateConditionValue2 = dateConditionValue2;
		this.dateConditionValue1 = dateConditionValue1;
		if (characterConditionValue2 == null) {
			this.characterConditionValue2 = Optional.empty();
		} else {
			this.characterConditionValue2 = Optional.of(new AcceptingConditionString(characterConditionValue2));
		}
		if (characterConditionValue1 == null) {
			this.characterConditionValue1 = Optional.empty();
		} else {
			this.characterConditionValue1 = Optional.of(new AcceptingConditionString(characterConditionValue1));
		}
		if (numberConditionValue2 == null) {
			this.numberConditionValue2 = Optional.empty();
		} else {
			this.numberConditionValue2 = Optional.of(new AcceptanceConditionValue(numberConditionValue2));
		}
		if (numberConditionValue1 == null) {
			this.numberConditionValue1 = Optional.empty();
		} else {
			this.numberConditionValue1 = Optional.of(new AcceptanceConditionValue(numberConditionValue1));
		}
	}
    
    
    
}
