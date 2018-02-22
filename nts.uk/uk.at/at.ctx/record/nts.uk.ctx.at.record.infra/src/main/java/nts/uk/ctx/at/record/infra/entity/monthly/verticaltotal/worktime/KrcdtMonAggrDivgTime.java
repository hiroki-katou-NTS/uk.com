package nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime;

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
 * 集計乖離時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_DIVG_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrDivgTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAggrDivgTimePK PK;
	
	/** 乖離フラグ */
	@Column(name = "DIVERGENCE_ATR")
	public int divergenceAtr;
	
	/** 乖離時間 */
	@Column(name = "DIVERGENCE_TIME")
	public int divergenceTime;
	
	/** 控除時間 */
	@Column(name = "DEDUCTION_TIME")
	public int deductionTime;
	
	/** 控除後乖離時間 */
	@Column(name = "DVRGEN_TIME_AFT_DEDU")
	public int divergenceTimeAfterDeduction;

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
