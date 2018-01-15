package nts.uk.ctx.at.shared.infra.entity.specialholiday;

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
import nts.uk.ctx.at.shared.dom.specialholiday.SphdLimit;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPHD_LIMIT")
public class KshstSphdLimit extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstSphdLimitPK kshstSphdLimitPK;
		
		/* 月数 */
		@Column(name = "SPECIAL_VACATION_MONTHS")
		public int specialVacationMonths;
		
		/* 年数 */
		@Column(name = "SPECIAL_VACATION_YEARS")
		public int specialVacationYears;
	
		/* 付与日数を繰り越す */
		@Column(name = "GRANT_CARRY_FORWARD")
		public int grantCarryForward;
		
		/* 繰越上限日数 */
		@Column(name = "LIMIT_CARRYOVER_DAYS")
		public int limitCarryoverDays;
		
		/* 特別休暇の期限方法 */
		@Column(name = "SPECIAL_VACATION_METHOD")
		public int specialVacationMethod;
		
		@OneToOne(optional = false)
		@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
			@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
		})
		
		public KshstSpecialHoliday specialHoliday;

	@Override
	protected Object getKey() {
		return kshstSphdLimitPK;
	}

	public KshstSphdLimit(KshstSphdLimitPK kshstSphdLimitPK, int specialVacationMonths, int specialVacationYears,
			int grantCarryForward, int limitCarryoverDays, int specialVacationMethod) {
		super();
		this.kshstSphdLimitPK = kshstSphdLimitPK;
		this.specialVacationMonths = specialVacationMonths;
		this.specialVacationYears = specialVacationYears;
		this.grantCarryForward = grantCarryForward;
		this.limitCarryoverDays = limitCarryoverDays;
		this.specialVacationMethod = specialVacationMethod;
	}
	
	public static KshstSphdLimit toEntity(SphdLimit domain){
		return new KshstSphdLimit(new KshstSphdLimitPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()),
				domain.getSpecialVacationMonths().v(), domain.getSpecialVacationYears().v(), domain.getGrantCarryForward().value,
				domain.getLimitCarryoverDays().v(), domain.getSpecialVacationMethod().value);
		
	}
}
