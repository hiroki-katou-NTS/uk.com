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
import nts.uk.ctx.at.record.dom.byperiod.ExcessOutsideItemByPeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：期間別の時間外超過項目
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_ANP_EXCOUT_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtAnpExcoutTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtAnpExcoutTimePK PK;

	/** 超過時間 */
	@Column(name = "EXCESS_TIME")
	public int excessTime;
	
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
	 * @return 期間別の時間外超過項目
	 */
	public ExcessOutsideItemByPeriod toDomain(){
		
		return ExcessOutsideItemByPeriod.of(
				this.PK.breakdownNo,
				new AttendanceTimeMonth(this.excessTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：任意期間別実績の勤怠時間
	 * @param domain 期間別の時間外超過項目
	 */
	public void fromDomainForPersist(AttendanceTimeOfAnyPeriodKey key, ExcessOutsideItemByPeriod domain){
		
		this.PK = new KrcdtAnpExcoutTimePK(
				key.getEmployeeId(),
				key.getAnyAggrFrameCode().v(),
				domain.getBreakdownNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 期間別の時間外超過項目
	 */
	public void fromDomainForUpdate(ExcessOutsideItemByPeriod domain){
		
		this.excessTime = domain.getExcessTime().v();
	}
}
