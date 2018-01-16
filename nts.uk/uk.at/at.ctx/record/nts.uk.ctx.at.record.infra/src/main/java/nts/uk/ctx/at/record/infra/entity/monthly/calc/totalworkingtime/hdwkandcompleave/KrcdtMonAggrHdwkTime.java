package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave;

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
@Table(name = "KRCDT_MON_AGGR_HDWK_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrHdwkTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrHdwkTimePK PK;

	/** 休出時間 */
	@Column(name = "HDWK_TIME")
	public int holidayWorkTime;

	/** 計算休出時間 */
	@Column(name = "CALC_HDWK_TIME")
	public int calcHolidayWorkTime;

	/** 事前休出時間 */
	@Column(name = "BEFORE_HDWK_TIME")
	public int beforeHolidayWorkTime;

	/** 振替時間 */
	@Column(name = "TRN_TIME")
	public int transferTime;

	/** 計算振替時間 */
	@Column(name = "CALC_TRN_TIME")
	public int calcTransferTime;

	/** 法定内休出時間 */
	@Column(name = "LEGAL_HDWK_TIME")
	public int legalHolidayWorkTime;

	/** 法定内振替休出時間 */
	@Column(name = "LEGAL_TRN_HDWK_TIME")
	public int legalTransferHolidayWorkTime;
	
	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
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
