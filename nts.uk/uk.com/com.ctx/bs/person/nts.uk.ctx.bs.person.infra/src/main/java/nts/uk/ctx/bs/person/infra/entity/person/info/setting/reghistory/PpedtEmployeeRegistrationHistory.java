package nts.uk.ctx.bs.person.infra.entity.person.info.setting.reghistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEDT_EMP_REG_HISTORY")
public class PpedtEmployeeRegistrationHistory extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpedtEmployeeRegistrationHistoryPk ppedtEmployeeRegistrationHistoryPk;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = true)
	@Column(name = "REG_DATE")
	public GeneralDate registeredDate;

	@Basic(optional = false)
	@Column(name = "LAST_REG_SID")
	public String lastRegEmployeeID;

	@Override
	protected Object getKey() {
		return ppedtEmployeeRegistrationHistoryPk;
	}

}
