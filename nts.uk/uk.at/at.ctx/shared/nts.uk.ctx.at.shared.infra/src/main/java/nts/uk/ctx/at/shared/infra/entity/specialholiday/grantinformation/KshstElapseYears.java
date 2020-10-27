package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 経過年数に対する付与日数
 * 
 * @author tanlv
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHST_ELAPSE_YEARS")
public class KshstElapseYears extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstElapseYearsPK pk;
	
	/* 特別休暇付与日数 */
	@Column(name = "GRANTED_DAYS")
	public int grantedDays;
	
	/* 経過年数.月数 */
	@Column(name = "MONTHS")
	public int months;
	
	/* 経過年数.年数 */
	@Column(name = "YEARS")
	public int years;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return pk;
	}

	public KshstElapseYears(KshstElapseYearsPK pk, int grantedDays, int months, int years) {
		
		this.pk = pk;
		this.grantedDays = grantedDays;
		this.months = months;
		this.years = years;
	}
}
