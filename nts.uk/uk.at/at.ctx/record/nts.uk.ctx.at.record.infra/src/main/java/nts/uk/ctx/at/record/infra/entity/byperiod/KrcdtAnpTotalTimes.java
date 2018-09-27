package nts.uk.ctx.at.record.infra.entity.byperiod;

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
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriodKey;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCount;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：回数集計
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_TOTAL_TIMES")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpTotalTimes extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpTotalTimesPK PK;

	/** 時間 */
	@Column(name = "TOTAL_TIME")
	public int totalTime;
	/** 回数 */
	@Column(name = "TOTAL_COUNT")
	public double totalCount;
	
	/** マッチング：任意期間別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "FRAME_CODE", referencedColumnName = "FRAME_CODE", insertable = false, updatable = false)
	})
	public KrcdtAnpAttendanceTime krcdtAnpAttendanceTime;
	
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
	 * @param key キー値：任意期間別実績の勤怠時間
	 * @param domain 回数集計
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, TotalCount domain){
		
		this.PK = new KrcdtAnpTotalTimesPK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
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
