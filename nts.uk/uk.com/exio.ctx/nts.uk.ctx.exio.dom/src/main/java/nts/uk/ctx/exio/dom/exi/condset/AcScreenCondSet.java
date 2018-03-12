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
		this.selectComparisonCondition = Optional
				.ofNullable(EnumAdaptor.valueOf(selectComparisonCondition, SelectComparisonCondition.class));
		this.timeConditionValue2 = Optional.ofNullable(new AcceptanceConditionTime(timeConditionValue2));
		this.timeConditionValue1 = Optional.ofNullable(new AcceptanceConditionTime(timeConditionValue1));
		this.timeMomentConditionValue2 = Optional.ofNullable(new AcceptanceConditionTimeMoment(timeMomentConditionValue2));
		this.timeMomentConditionValue1 = Optional.ofNullable(new AcceptanceConditionTimeMoment(timeMomentConditionValue1));
		this.dateConditionValue2 = Optional.ofNullable(dateConditionValue2);
		this.dateConditionValue1 = Optional.ofNullable(dateConditionValue1);
		this.characterConditionValue2 = Optional.ofNullable(new AcceptanceConditionString(characterConditionValue2));
		this.characterConditionValue1 = Optional.ofNullable(new AcceptanceConditionString(characterConditionValue1));
		this.numberConditionValue2 = Optional.ofNullable(new AcceptanceConditionValue(numberConditionValue2));
		this.numberConditionValue1 = Optional.ofNullable(new AcceptanceConditionValue(numberConditionValue1));
	}

}
