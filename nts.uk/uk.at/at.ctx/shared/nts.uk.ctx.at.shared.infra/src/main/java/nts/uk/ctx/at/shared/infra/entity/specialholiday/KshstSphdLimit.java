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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
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
}
