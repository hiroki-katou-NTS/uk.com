package nts.uk.ctx.at.record.infra.entity.weekly;

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
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCount;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：回数集計
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_WEK_TOTAL_TIMES")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtWekTotalTimes extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtWekTotalTimesPK PK;

	/** 時間 */
	@Column(name = "TOTAL_TIME")
	public int totalTime;
	/** 回数 */
	@Column(name = "TOTAL_COUNT")
	public double totalCount;
	
	/** マッチング：週別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "WEEK_NO", referencedColumnName = "WEEK_NO", insertable = false, updatable = false)
	})
	public KrcdtWekAttendanceTime krcdtWekAttendanceTime;
	
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
	 * @param key キー値：週別実績の勤怠時間
	 * @param domain 回数集計
	 */
	public void fromDomainForPersist(AttendanceTimeOfWeeklyKey key, TotalCount domain){
		
		this.PK = new KrcdtWekTotalTimesPK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				key.getWeekNo(),
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
