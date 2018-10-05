package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 項目範囲設定初期値
 */
@Getter
public class ItemRangeSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 範囲値の属性
	 */
	private RangeValueEnum rangeValueAtr;

	/**
	 * エラー範囲設定
	 */
	private ErrorRangeSetting errorRangeSetting;

	/**
	 * アラーム範囲設定
	 */
	private AlarmRangeSetting alarmRangeSetting;

	public ItemRangeSet(String cid, String salaryItemId, int rangeValueAtr, int errorUpperLimitSetAtr,
			Long errorUpRangeValAmount, Integer errorUpRangeValTime, BigDecimal errorUpRangeValNum,
			int errorLowerLimitSetAtr, Long errorLoRangeValAmount, Integer errorLoRangeValTime,
			BigDecimal errorLoRangeValNum, int alarmUpperLimitSetAtr, Long alarmUpRangeValAmount,
			Integer alarmUpRangeValTime, BigDecimal alarmUpRangeValNum, int alarmLowerLimitSetAtr,
			Long alarmLoRangeValAmount, Integer alarmLoRangeValTime, BigDecimal alarmLoRangeValNum) {
		this.cid = cid;
		this.salaryItemId = salaryItemId;
		this.rangeValueAtr = EnumAdaptor.valueOf(rangeValueAtr, RangeValueEnum.class);
		this.errorRangeSetting = new ErrorRangeSetting(errorUpperLimitSetAtr, errorUpRangeValAmount,
				errorUpRangeValTime, errorUpRangeValNum, errorLowerLimitSetAtr, errorLoRangeValAmount,
				errorLoRangeValTime, errorLoRangeValNum);
		this.alarmRangeSetting = new AlarmRangeSetting(alarmUpperLimitSetAtr, alarmUpRangeValAmount,
				alarmUpRangeValTime, alarmUpRangeValNum, alarmLowerLimitSetAtr, alarmLoRangeValAmount,
				alarmLoRangeValTime, alarmLoRangeValNum);
	}

}
