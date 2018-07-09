package nts.uk.ctx.at.shared.infra.entity.specialholidaynew.periodinformation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom.KshstYearServiceCom;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceper.KshstYearServicePer;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSpecialHolidayNew;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_PERIODIC")
public class KshstGrantPeriodicNew extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstGrantPeriodicNewPK kshstGrantPeriodicPK;

	@Override
	protected Object getKey() {
		return kshstGrantPeriodicPK;
	}
}