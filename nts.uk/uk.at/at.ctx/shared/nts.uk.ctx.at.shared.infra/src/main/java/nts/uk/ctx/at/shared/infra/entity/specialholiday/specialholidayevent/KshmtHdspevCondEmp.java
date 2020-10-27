package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSPEV_COND_EMP")
// 雇用一覧
public class KshmtHdspevCondEmp extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshmtHdspevCondEmpPK pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
