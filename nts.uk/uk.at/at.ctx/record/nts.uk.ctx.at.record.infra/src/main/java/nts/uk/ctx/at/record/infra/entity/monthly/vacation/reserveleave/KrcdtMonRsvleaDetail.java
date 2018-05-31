package nts.uk.ctx.at.record.infra.entity.monthly.vacation.reserveleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingDetail;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：積立年休月別残数明細
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_RSVLEA_DETAIL")
@NoArgsConstructor
public class KrcdtMonRsvleaDetail extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonRsvleaDetailPK PK;
	
	/** 残日数 */
	@Column(name = "REMAINING_DAYS")
	public double remainingDays;
	
	/** 実残日数 */
	@Column(name = "FACT_REMAINING_DAYS")
	public double factRemainingDays;
	
	/** マッチング：積立年休月別残数データ */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "KRCDT_MON_RSVLEA_REMAIN.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_MON_RSVLEA_REMAIN.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_MON_RSVLEA_REMAIN.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_MON_RSVLEA_REMAIN.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_MON_RSVLEA_REMAIN.IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonRsvleaRemain krcdtMonRsvleaRemain;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 積立年休残明細　（年休）
	 */
	public ReserveLeaveRemainingDetail toDomainForNormal(){
		
		return ReserveLeaveRemainingDetail.of(
				this.PK.grantDate,
				new ReserveLeaveRemainingDayNumber(this.remainingDays));
	}
	
	/**
	 * ドメインに変換
	 * @return 積立年休残明細　（実年休）
	 */
	public ReserveLeaveRemainingDetail toDomainForReal(){

		return ReserveLeaveRemainingDetail.of(
				this.PK.grantDate,
				new ReserveLeaveRemainingDayNumber(this.factRemainingDays));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param parentDomain 積立年休月別残数データ
	 * @param remainingType 残数種類
	 * @param domain 積立年休残明細　（年休）
	 * @param domainReal 積立年休残明細　（実年休）
	 */
	public void fromDomainForPersist(
			RsvLeaRemNumEachMonth parentDomain,
			int remainingType,
			ReserveLeaveRemainingDetail domain,
			ReserveLeaveRemainingDetail domainReal){
		
		this.PK = new KrcdtMonRsvleaDetailPK(
				parentDomain.getEmployeeId(),
				parentDomain.getYearMonth().v(),
				parentDomain.getClosureId().value,
				parentDomain.getClosureDate().getClosureDay().v(),
				(parentDomain.getClosureDate().getLastDayOfMonth() ? 1 : 0),
				remainingType,
				domain.getGrantDate());
		this.fromDomainForUpdate(domain, domainReal);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 積立年休残明細　（年休）
	 * @param domainReal 積立年休残明細　（実年休）
	 */
	public void fromDomainForUpdate(
			ReserveLeaveRemainingDetail domain,
			ReserveLeaveRemainingDetail domainReal){
		
		this.remainingDays = domain.getDays().v();
		this.factRemainingDays = domainReal.getDays().v();
	}
}
