package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計休出時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_AGGR_HOLIDAY_WRK_TM")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAggrHolidayWrkTm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAggrHolidayWrkTmPK PK;

	/** 休出時間 */
	@Column(name = "HLDY_WRK_TM")
	public int holidayWorkTime;

	/** 休出計算時間 */
	@Column(name = "HLDY_WRK_TM_CALC")
	public int holidayWorkTimeCalc;

	/** 事前休出時間 */
	@Column(name = "BFR_HLDY_WRK_TM")
	public int beforeHolidayWorkTime;

	/** 振替休出時間 */
	@Column(name = "TRANSFER_TIME")
	public int transferHolidayWorkTime;

	/** 振替休出計算時間 */
	@Column(name = "TRANSFER_TIME_CALC")
	public int transferHolidayWorkTimeCalc;

	/** 法定内休出時間 */
	@Column(name = "WITHIN_HLD_WK_TM")
	public int withinStatutoryHolidayWorkTime;

	/** 法定内振替休出時間 */
	@Column(name = "WITHIN_TRN_HLD_WK_TM")
	public int withinStatutoryTransferHolidayWorkTime;
	
	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="SID", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "START_YMD", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.START_YMD", insertable = false, updatable = false),
		@JoinColumn(name = "END_YMD", referencedColumnName="KRCDT_MON_ATTENDANCE_TIME.END_YMD", insertable = false, updatable = false)
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
