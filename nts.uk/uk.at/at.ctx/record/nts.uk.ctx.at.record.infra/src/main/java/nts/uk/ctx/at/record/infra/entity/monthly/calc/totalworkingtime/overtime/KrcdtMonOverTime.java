package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime;

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
 * 月別実績の残業時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_OVER_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonOverTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 残業合計時間 */
	@Column(name = "TOTAL_OVER_TIME")
	public int totalOverTime;
	
	/** 計算残業合計時間 */
	@Column(name = "CALC_TOTAL_OVER_TIME")
	public int calcTotalOverTime;
	
	/** 事前残業時間 */
	@Column(name = "BEFORE_OVER_TIME")
	public int beforeOverTime;
	
	/** 振替残業合計時間 */
	@Column(name = "TOTAL_TRNOVR_TIME")
	public int totalTransferOverTime;
	
	/** 計算振替残業合計時間 */
	@Column(name = "CALC_TOTAL_TRNOVR_TIME")
	public int calcTotalTransferOverTime;

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
