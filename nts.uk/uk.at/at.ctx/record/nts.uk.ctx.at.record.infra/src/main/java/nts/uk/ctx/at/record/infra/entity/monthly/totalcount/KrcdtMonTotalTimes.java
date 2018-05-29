package nts.uk.ctx.at.record.infra.entity.monthly.totalcount;

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
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCount;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の回数集計
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_TOTAL_TIMES")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonTotalTimes extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonTotalTimesPK PK;

	/** 時間 */
	@Column(name = "TOTAL_TIME")
	public int totalTime;

	/** 回数 */
	@Column(name = "TOTAL_COUNT")
	public double totalCount;
	
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
	
	/**
	 * ドメインに変換
	 * @return 回数集計
	 */
	public TotalCount toDomain(){
		
		return TotalCount.of(
				this.PK.totalTimesNo,
				new AttendanceDaysMonth(this.totalCount),
				new AttendanceTimeMonth(this.totalTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 回数集計
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, TotalCount domain){
		
		this.PK = new KrcdtMonTotalTimesPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getTotalCountNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 回数集計
	 */
	public void fromDomainForUpdate(TotalCount domain){
		
		this.totalTime = domain.getTime().v();
		this.totalCount = (double)domain.getCount().v();
	}
}
