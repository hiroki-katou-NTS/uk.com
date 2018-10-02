package nts.uk.ctx.at.record.infra.entity.monthly.affiliation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AggregateAffiliationInfo;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の所属情報
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AFFILIATION")
@NoArgsConstructor
public class KrcdtMonAffiliation extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAffiliationPK PK;
	
	/** 月初雇用コード */
	@Column(name = "FIRST_EMP_CD")
	public String firstEmploymentCd;
	
	/** 月初職場ID */
	@Column(name = "FIRST_WKP_ID")
	public String firstWorkplaceId;
	
	/** 月初職位ID */
	@Column(name = "FIRST_JOB_ID")
	public String firstJobTitleId;
	
	/** 月初分類コード */
	@Column(name = "FIRST_CLS_CD")
	public String firstClassCd;
	
	/** 月初勤務種別コード */
	@Column(name = "FIRST_BUS_CD")
	public String firstBusinessTypeCd;
	
	/** 月末雇用コード */
	@Column(name = "LAST_EMP_CD")
	public String lastEmploymentCd;
	
	/** 月末職場ID */
	@Column(name = "LAST_WKP_ID")
	public String lastWorkplaceId;
	
	/** 月末職位ID */
	@Column(name = "LAST_JOB_ID")
	public String lastJobTitleId;
	
	/** 月末分類コード */
	@Column(name = "LAST_CLS_CD")
	public String lastClassCd;
	
	/** 月末勤務種別コード */
	@Column(name = "LAST_BUS_CD")
	public String lastBusinessTypeCd;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 月別実績の所属情報
	 */
	public AffiliationInfoOfMonthly toDomain(){
		
		return AffiliationInfoOfMonthly.of(
				this.PK.employeeId,
				new YearMonth(this.PK.yearMonth),
				EnumAdaptor.valueOf(this.PK.closureId, ClosureId.class),
				new ClosureDate(this.PK.closureDay, (this.PK.isLastDay == 1)),
				AggregateAffiliationInfo.of(
						new EmploymentCode(this.firstEmploymentCd),
						new WorkplaceId(this.firstWorkplaceId),
						new JobTitleId(this.firstJobTitleId),
						new ClassificationCode(this.firstClassCd),
						new BusinessTypeCode(this.firstBusinessTypeCd)),
				AggregateAffiliationInfo.of(
						new EmploymentCode(this.lastEmploymentCd),
						new WorkplaceId(this.lastWorkplaceId),
						new JobTitleId(this.lastJobTitleId),
						new ClassificationCode(this.lastClassCd),
						new BusinessTypeCode(this.lastBusinessTypeCd)));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 月別実績の所属情報
	 */
	public void fromDomainForPersist(AffiliationInfoOfMonthly domain){
		
		this.PK = new KrcdtMonAffiliationPK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の所属情報
	 */
	public void fromDomainForUpdate(AffiliationInfoOfMonthly domain){
		
		this.firstEmploymentCd = domain.getFirstInfo().getEmploymentCd().v();
		this.firstWorkplaceId = domain.getFirstInfo().getWorkplaceId().v();
		this.firstJobTitleId = domain.getFirstInfo().getJobTitleId().v();
		this.firstClassCd = domain.getFirstInfo().getClassCd().v();
		this.firstBusinessTypeCd = domain.getFirstInfo().getBusinessTypeCd().v();
		this.lastEmploymentCd = domain.getLastInfo().getEmploymentCd().v();
		this.lastWorkplaceId = domain.getLastInfo().getWorkplaceId().v();
		this.lastJobTitleId = domain.getLastInfo().getJobTitleId().v();
		this.lastClassCd = domain.getLastInfo().getClassCd().v();
		this.lastBusinessTypeCd = domain.getLastInfo().getBusinessTypeCd().v();
	}
}
