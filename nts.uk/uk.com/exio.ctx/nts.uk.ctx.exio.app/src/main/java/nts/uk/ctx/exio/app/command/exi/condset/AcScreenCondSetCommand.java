package nts.uk.ctx.exio.app.command.exi.condset;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;

@Value
public class AcScreenCondSetCommand {

	/**
	 * 条件設定コード
	 */
	private String conditionSetCd;

	/**
	 * 受入項目の番号
	 */
	private int acceptItemNum;

	/**
	 * 比較条件選択
	 */
	private Integer selectComparisonCondition;

	/**
	 * 時間‗条件値1
	 */
	private Integer timeConditionValue1;

	/**
	 * 時間‗条件値2
	 */
	private Integer timeConditionValue2;

	/**
	 * 時刻‗条件値1
	 */
	private Integer timeMomentConditionValue1;

	/**
	 * 時刻‗条件値2
	 */
	private Integer timeMomentConditionValue2;

	/**
	 * 日付‗条件値1
	 */
	private GeneralDate dateConditionValue1;

	/**
	 * 日付‗条件値2
	 */
	private GeneralDate dateConditionValue2;

	/**
	 * 文字‗条件値1
	 */
	private String characterConditionValue1;

	/**
	 * 文字‗条件値2
	 */
	private String characterConditionValue2;

	/**
	 * 数値‗条件値1
	 */
	private BigDecimal numberConditionValue1;

	/**
	 * 数値‗条件値2
	 */
	private BigDecimal numberConditionValue2;

	public AcScreenCondSet toDomain() {
		return new AcScreenCondSet(this.conditionSetCd, this.acceptItemNum, this.selectComparisonCondition,
				this.timeConditionValue1, this.timeConditionValue2, this.timeMomentConditionValue1,
				this.timeMomentConditionValue2, this.dateConditionValue1, this.dateConditionValue2,
				this.characterConditionValue1, this.characterConditionValue2, this.numberConditionValue1,
				this.numberConditionValue2);
	}
}
