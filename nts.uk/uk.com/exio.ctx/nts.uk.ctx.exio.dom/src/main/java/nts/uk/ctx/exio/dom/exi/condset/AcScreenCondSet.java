package nts.uk.ctx.exio.dom.exi.condset;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;

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

	public AcScreenCondSet(String conditionSetCd, int acceptItemNum, Integer selectComparisonCondition,
			Integer timeConditionValue1, Integer timeConditionValue2, Integer timeMomentConditionValue1,
			Integer timeMomentConditionValue2, GeneralDate dateConditionValue1, GeneralDate dateConditionValue2,
			String characterConditionValue1, String characterConditionValue2, BigDecimal numberConditionValue1,
			BigDecimal numberConditionValue2) {
		super();
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.acceptItemNum = new AcceptanceLineNumber(acceptItemNum);
		if (selectComparisonCondition == null)
			this.selectComparisonCondition = Optional.empty();
		else
			this.selectComparisonCondition = Optional
					.of(EnumAdaptor.valueOf(selectComparisonCondition, SelectComparisonCondition.class));
		if (timeConditionValue2 == null)
			this.timeConditionValue2 = Optional.empty();
		else
			this.timeConditionValue2 = Optional.of(new AcceptanceConditionTime(timeConditionValue2));
		if (timeConditionValue1 == null)
			this.timeConditionValue1 = Optional.empty();
		else
			this.timeConditionValue1 = Optional.of(new AcceptanceConditionTime(timeConditionValue1));
		if (timeMomentConditionValue2 == null)
			this.timeMomentConditionValue2 = Optional.empty();
		else
			this.timeMomentConditionValue2 = Optional.of(new AcceptanceConditionTimeMoment(timeMomentConditionValue2));
		if (timeMomentConditionValue1 == null)
			this.timeMomentConditionValue1 = Optional.empty();
		else
			this.timeMomentConditionValue1 = Optional.of(new AcceptanceConditionTimeMoment(timeMomentConditionValue1));
		if (dateConditionValue2 == null)
			this.dateConditionValue2 = Optional.empty();
		else
			this.dateConditionValue2 = Optional.of(dateConditionValue2);
		if (dateConditionValue1 == null)
			this.dateConditionValue1 = Optional.empty();
		else
			this.dateConditionValue1 = Optional.of(dateConditionValue1);
		if (characterConditionValue2 == null)
			this.characterConditionValue2 = Optional.empty();
		else
			this.characterConditionValue2 = Optional.of(new AcceptanceConditionString(characterConditionValue2));
		if (characterConditionValue1 == null)
			this.characterConditionValue1 = Optional.empty();
		else
			this.characterConditionValue1 = Optional.of(new AcceptanceConditionString(characterConditionValue1));
		if (numberConditionValue2 == null)
			this.numberConditionValue2 = Optional.empty();
		else
			this.numberConditionValue2 = Optional.of(new AcceptanceConditionValue(numberConditionValue2));
		if (numberConditionValue1 == null)
			this.numberConditionValue1 = Optional.empty();
		else
			this.numberConditionValue1 = Optional.of(new AcceptanceConditionValue(numberConditionValue1));
	}

}
