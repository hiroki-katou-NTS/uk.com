package nts.uk.ctx.exio.app.find.exi.condset;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;

/**
 * 受入選別条件設定
 */

@Value
public class AcScreenCondSetDto {

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

	public static AcScreenCondSetDto fromDomain(AcScreenCondSet domain) {
		return new AcScreenCondSetDto(domain.getConditionSetCd().v(), domain.getAcceptItemNum().v(),
				domain.getSelectComparisonCondition().value,
				domain.getTimeConditionValue1().isPresent() ? domain.getTimeConditionValue1().get().v() : null,
				domain.getTimeConditionValue2().isPresent() ? domain.getTimeConditionValue2().get().v() : null,
				domain.getTimeMomentConditionValue1().isPresent() ? domain.getTimeMomentConditionValue1().get().v()
						: null,
				domain.getTimeMomentConditionValue2().isPresent() ? domain.getTimeMomentConditionValue2().get().v()
						: null,
				domain.getDateConditionValue1().isPresent() ? domain.getDateConditionValue1().get() : null,
				domain.getDateConditionValue2().isPresent() ? domain.getDateConditionValue2().get() : null,
				domain.getCharacterConditionValue1().isPresent() ? domain.getCharacterConditionValue1().get().v()
						: null,
				domain.getCharacterConditionValue2().isPresent() ? domain.getCharacterConditionValue2().get().v()
						: null,
				domain.getNumberConditionValue1().isPresent() ? domain.getNumberConditionValue1().get().v() : null,
				domain.getNumberConditionValue2().isPresent() ? domain.getNumberConditionValue2().get().v() : null);
	}

}
