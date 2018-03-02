package nts.uk.ctx.at.record.infra.entity.monthly.excessoutside;

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
 * エンティティ：月別実績の時間外超過
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_EXCESS_OUTSIDE")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonExcessOutside extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 週割増合計時間 */
	@Column(name = "TOTAL_WEEK_PRM_TIME")
	public int totalWeeklyPremiumTime;
	
	/** 月割増合計時間 */
	@Column(name = "TOTAL_MONTH_PRM_TIME")
	public int totalMonthlyPremiumTime;
	
	/** 変形繰越時間 */
	@Column(name = "DEFORM_CARRYFWD_TIME")
	public int deformationCarryforwardTime;

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
