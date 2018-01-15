package nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の通常変形時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_REG_IRREG_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonRegIrregTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 週割増合計時間 */
	@Column(name = "WEEK_TOTAL_PREM_TIME")
	public int weeklyTotalPremiumTime;
	
	/** 月割増合計時間 */
	@Column(name = "MON_TOTAL_PREM_TIME")
	public int monthlyTotalPremiumTime;
	
	/** 複数月変形途中時間 */
	@Column(name = "MULTI_MON_IRGMDL_TIME")
	public int multiMonthIrregularMiddleTime;
	
	/** 変形期間繰越時間 */
	@Column(name = "IRGPERIOD_CRYFWD_TIME")
	public int irregularPeriodCarryforwardTime;
	
	/** 変形労働不足時間 */
	@Column(name = "IRG_SHORTAGE_TIME")
	public int irregularWorkingShortageTime;
	
	/** 変形法定内残業時間 */
	@Column(name = "IRG_LEGAL_OVER_TIME")
	public int irregularLegalOverTime;
	
	/** 計算変形法定内残業時間 */
	@Column(name = "CALC_IRG_LGL_OVER_TIME")
	public int calcIrregularLegalOverTime;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
    	@JoinColumn(name = "SID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
}
