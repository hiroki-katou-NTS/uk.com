package nts.uk.ctx.at.record.infra.entity.monthly.agreement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：管理期間の36協定時間
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCDT_MON_MNG_AGRE_TIME")
@NoArgsConstructor
public class KrcdtMonMngAgreTime extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonMngAgreTimePK PK;
	
	/** 年度 */
	@Column(name = "YEAR")
	public int year;
	
	/** 36協定時間 */
	@Column(name = "AGREEMENT_TIME")
	public int agreementTime;
	
	/** 限度エラー時間 */
	@Column(name = "LIMIT_ERROR_TIME")
	public int limitErrorTime;
	
	/** 限度アラーム時間 */
	@Column(name = "LIMIT_ALARM_TIME")
	public int limitAlarmTime;
	
	/** 特例限度エラー時間 */
	@Column(name = "EXCEPT_LIMIT_ERR_TIME")
	public Integer exceptionLimitErrorTime;
	
	/** 特例限度アラーム時間 */
	@Column(name = "EXCEPT_LIMIT_ALM_TIME")
	public Integer exceptionLimitAlarmTime;
	
	/** 状態 */
	@Column(name = "STATUS")
	public int status;
	
	/** 所定内割増時間 */
	@Column(name = "PRESCR_PREMIUM_TIME")
	public int withinPresctibedPremiumTime;
	
	/** 残業時間 */
	@Column(name = "OVER_TIME")
	public int overTime;
	
	/** 振替残業時間 */
	@Column(name = "TRANS_OVER_TIME")
	public int transferOverTime;
	
	/** 休出時間 */
	@Column(name = "HOLIDAY_WORK_TIME")
	public int holidayWorkTime;
	
	/** 振替休出時間 */
	@Column(name = "TRANS_HDWK_TIME")
	public int transferHolidayWorkTime;
	
	/** フレックス超過時間 */
	@Column(name = "FLEX_EXCESS_TIME")
	public int flexExcessTime;
	
	/** 週割増時間 */
	@Column(name = "WEEK_PREMIUM_TIME")
	public int weeklyPremiumTime;
	
	/** 月割増時間 */
	@Column(name = "MONTH_PREMIUM_TIME")
	public int monthlyPremiumTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
