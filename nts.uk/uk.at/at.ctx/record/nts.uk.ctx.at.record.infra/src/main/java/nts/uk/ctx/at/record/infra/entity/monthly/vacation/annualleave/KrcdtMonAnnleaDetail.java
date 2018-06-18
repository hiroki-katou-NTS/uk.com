package nts.uk.ctx.at.record.infra.entity.monthly.vacation.annualleave;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：年休月別残数明細
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_ANNLEA_DETAIL")
@NoArgsConstructor
public class KrcdtMonAnnleaDetail extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAnnleaDetailPK PK;
	
	/** 残日数 */
	@Column(name = "REMAINING_DAYS")
	public double remainingDays;
	
	/** 残時間 */
	@Column(name = "REMAINING_MINUTES")
	public Integer remainingMinutes;
	
	/** 実残日数 */
	@Column(name = "FACT_REMAINING_DAYS")
	public double factRemainingDays;
	
	/** 実残時間 */
	@Column(name = "FACT_REMAINING_MINUTES")
	public Integer factRemainingMinutes;
	
	/** マッチング：年休月別残数データ */
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAnnleaRemain krcdtMonAnnleaRemain;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 年休残明細　（年休）
	 */
	public AnnualLeaveRemainingDetail toDomainForNormal(){
		
		AnnualLeaveRemainingTime valRemainMinutes = null;
		if (this.remainingMinutes != null){
			valRemainMinutes = new AnnualLeaveRemainingTime(this.remainingMinutes);
		}
		
		return AnnualLeaveRemainingDetail.of(
				this.PK.grantDate,
				new AnnualLeaveRemainingDayNumber(this.remainingDays),
				Optional.ofNullable(valRemainMinutes));
	}
	
	/**
	 * ドメインに変換
	 * @return 年休残明細　（実年休）
	 */
	public AnnualLeaveRemainingDetail toDomainForReal(){

		AnnualLeaveRemainingTime valFactRemainMinutes = null;
		if (this.factRemainingMinutes != null){
			valFactRemainMinutes = new AnnualLeaveRemainingTime(this.factRemainingMinutes);
		}
		
		return AnnualLeaveRemainingDetail.of(
				this.PK.grantDate,
				new AnnualLeaveRemainingDayNumber(this.factRemainingDays),
				Optional.ofNullable(valFactRemainMinutes));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param parentDomain 年休月別残数データ
	 * @param remainingType 残数種類
	 * @param domain 年休残明細　（年休）
	 * @param domainReal 年休残明細　（実年休）
	 */
	public void fromDomainForPersist(
			AnnLeaRemNumEachMonth parentDomain,
			int remainingType,
			AnnualLeaveRemainingDetail domain,
			AnnualLeaveRemainingDetail domainReal){
		
		this.PK = new KrcdtMonAnnleaDetailPK(
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
	 * @param domain 年休残明細　（年休）
	 * @param domainReal 年休残明細　（実年休）
	 */
	public void fromDomainForUpdate(
			AnnualLeaveRemainingDetail domain,
			AnnualLeaveRemainingDetail domainReal){
		
		this.remainingDays = domain.getDays().v();
		this.remainingMinutes = (domain.getTime().isPresent() ? domain.getTime().get().v() : null);
		this.factRemainingDays = domainReal.getDays().v();
		this.factRemainingMinutes = (domainReal.getTime().isPresent() ? domainReal.getTime().get().v() : null);
	}
}
