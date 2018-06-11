package nts.uk.ctx.at.record.infra.entity.monthly.excessoutside;

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
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWork;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：時間外超過
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_EXCOUT_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonExcoutTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonExcoutTimePK PK;

	/** 超過時間 */
	@Column(name = "EXCESS_TIME")
	public int excessTime;
	
	/** マッチング：月別実績の勤怠時間 */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
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
	 * @return 時間外超過
	 */
	public ExcessOutsideWork toDomain(){
		
		return ExcessOutsideWork.of(
				this.PK.breakdownNo,
				this.PK.excessNo,
				new AttendanceTimeMonth(this.excessTime));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 時間外超過
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, ExcessOutsideWork domain){
		
		this.PK = new KrcdtMonExcoutTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				domain.getBreakdownNo(),
				domain.getExcessNo());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 時間外超過
	 */
	public void fromDomainForUpdate(ExcessOutsideWork domain){
		
		this.excessTime = domain.getExcessTime().v();
	}
}
