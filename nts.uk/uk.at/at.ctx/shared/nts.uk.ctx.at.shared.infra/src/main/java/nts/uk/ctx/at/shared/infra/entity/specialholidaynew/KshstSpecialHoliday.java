package nts.uk.ctx.at.shared.infra.entity.specialholidaynew;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecialLeaveRestriction;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstGrantRegular;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.periodinformation.KshstGrantPeriodic;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPECIAL_HOLIDAY")
public class KshstSpecialHoliday extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSpecialHolidayPK kshstSpecialHolidayPK;

	/* 特別休暇名称 */
	@Column(name = "SPHD_NAME")
	public String specialHolidayName;
	
	/* メモ */
	@Column(name = "MEMO")
	public String memo;

	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstGrantRegular grantRegular;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstGrantPeriodic grantPeriodic;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstSpecialLeaveRestriction specialLeaveRestriction;
	
	@Override
	protected Object getKey() {
		return kshstSpecialHolidayPK;
	}
}
