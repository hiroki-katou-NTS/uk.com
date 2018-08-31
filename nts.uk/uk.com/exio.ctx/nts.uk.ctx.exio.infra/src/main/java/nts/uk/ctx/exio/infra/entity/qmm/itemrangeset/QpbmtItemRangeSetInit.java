package nts.uk.ctx.exio.infra.entity.qmm.itemrangeset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.qmm.itemrangeset.ItemRangeSettingInitialValue;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 項目範囲設定初期値
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_ITEM_RANGE_SET_INIT")
public class QpbmtItemRangeSetInit extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtItemRangeSetInitPk itemRangeSetInitPk;

	/**
	 * 範囲値の属性
	 */
	@Basic(optional = false)
	@Column(name = "RANGE_VALUE_ATR")
	public int rangeValueAtr;

	/**
	 * エラー上限値設定区分
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_UPPER_LIMIT_SET_ATR")
	public int errorUpperLimitSetAtr;

	/**
	 * エラー上限値金額
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_UP_RANGE_VAL_AMOUNT")
	public BigDecimal errorUpRangeValAmount;

	/**
	 * エラー上限値時間
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_UP_RANGE_VAL_TIME")
	public Integer errorUpRangeValTime;

	/**
	 * エラー上限値回数
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_UP_RANGE_VAL_NUM")
	public BigDecimal errorUpRangeValNum;

	/**
	 * エラー下限値設定区分
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_LOWER_LIMIT_SET_ATR")
	public int errorLowerLimitSetAtr;

	/**
	 * エラー上限値金額
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_LO_RANGE_VAL_AMOUNT")
	public BigDecimal errorLoRangeValAmount;

	/**
	 * エラー上限値時間
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_LO_RANGE_VAL_TIME")
	public Integer errorLoRangeValTime;

	/**
	 * エラー上限値回数
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_LO_RANGE_VAL_NUM")
	public BigDecimal errorLoRangeValNum;

	/**
	 * アラーム上限値設定区分
	 */
	@Basic(optional = false)
	@Column(name = "ALARM_UPPER_LIMIT_SET_ATR")
	public int alarmUpperLimitSetAtr;

	/**
	 * アラーム上限値金額
	 */
	@Basic(optional = true)
	@Column(name = "ALARM_UP_RANGE_VAL_AMOUNT")
	public BigDecimal alarmUpRangeValAmount;

	/**
	 * アラーム上限値時間
	 */
	@Basic(optional = true)
	@Column(name = "ALARM_UP_RANGE_VAL_TIME")
	public Integer alarmUpRangeValTime;

	/**
	 * アラーム上限値回数
	 */
	@Basic(optional = true)
	@Column(name = "ALARM_UP_RANGE_VAL_NUM")
	public BigDecimal alarmUpRangeValNum;

	/**
	 * アラーム下限値設定区分
	 */
	@Basic(optional = false)
	@Column(name = "ALARM_LOWER_LIMIT_SET_ATR")
	public int alarmLowerLimitSetAtr;

	/**
	 * アラーム上限値金額
	 */
	@Basic(optional = true)
	@Column(name = "ALARM_LO_RANGE_VAL_AMOUNT")
	public BigDecimal alarmLoRangeValAmount;

	/**
	 * アラーム上限値時間
	 */
	@Basic(optional = true)
	@Column(name = "ALARM_LO_RANGE_VAL_TIME")
	public Integer alarmLoRangeValTime;

	/**
	 * アラーム上限値回数
	 */
	@Basic(optional = true)
	@Column(name = "ALARM_LO_RANGE_VAL_NUM")
	public BigDecimal alarmLoRangeValNum;

	@Override
	protected Object getKey() {
		return itemRangeSetInitPk;
	}

	public ItemRangeSettingInitialValue toDomain() {
		return new ItemRangeSettingInitialValue(this.itemRangeSetInitPk.cid, this.itemRangeSetInitPk.salaryItemId,
				this.rangeValueAtr, this.errorUpperLimitSetAtr, this.errorUpRangeValAmount, this.errorUpRangeValTime,
				this.errorUpRangeValNum, this.errorLowerLimitSetAtr, this.errorLoRangeValAmount,
				this.errorLoRangeValTime, this.errorLoRangeValNum, this.alarmUpperLimitSetAtr,
				this.alarmUpRangeValAmount, this.alarmUpRangeValTime, this.alarmUpRangeValNum,
				this.alarmLowerLimitSetAtr, this.alarmLoRangeValAmount, this.alarmLoRangeValTime,
				this.alarmLoRangeValNum);
	}

	public static QpbmtItemRangeSetInit toEntity(ItemRangeSettingInitialValue domain) {
		return new QpbmtItemRangeSetInit(new QpbmtItemRangeSetInitPk(domain.getCid(), domain.getSalaryItemId()),
				domain.getRangeValueAtr().value,
				domain.getErrorRangeSetting().getUpperLimitSetting().getUpperLimitSettingAtr().value,
				domain.getErrorRangeSetting().getUpperLimitSetting().getRangeValueAmount().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getUpperLimitSetting().getRangeValueTime().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getUpperLimitSetting().getRangeValueNum().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getLowerLimitSetting().getLowerLimitSettingAtr().value,
				domain.getErrorRangeSetting().getLowerLimitSetting().getRangeValueAmount().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getLowerLimitSetting().getRangeValueTime().map(i -> i.v()).orElse(null),
				domain.getErrorRangeSetting().getLowerLimitSetting().getRangeValueNum().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getUpperLimitSetting().getUpperLimitSettingAtr().value,
				domain.getAlarmRangeSetting().getUpperLimitSetting().getRangeValueAmount().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getUpperLimitSetting().getRangeValueTime().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getUpperLimitSetting().getRangeValueNum().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getLowerLimitSetting().getLowerLimitSettingAtr().value,
				domain.getAlarmRangeSetting().getLowerLimitSetting().getRangeValueAmount().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getLowerLimitSetting().getRangeValueTime().map(i -> i.v()).orElse(null),
				domain.getAlarmRangeSetting().getLowerLimitSetting().getRangeValueNum().map(i -> i.v()).orElse(null));
	}

}