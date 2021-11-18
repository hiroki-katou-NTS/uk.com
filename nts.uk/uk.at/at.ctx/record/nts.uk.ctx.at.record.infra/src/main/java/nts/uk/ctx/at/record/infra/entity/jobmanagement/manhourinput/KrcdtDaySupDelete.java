package nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinput;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_SUP_DELETE")
public class KrcdtDaySupDelete extends ContractCompanyUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtDaySupDeletePk pk;
	
	@Column(name = "STATUS")
	public int status;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcdtDaySupDelete(KrcdtDaySupDeletePk pk, int status) {
		super();
		this.pk = pk;
		this.status = status;
	}
	
}
