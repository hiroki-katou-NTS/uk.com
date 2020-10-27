package nts.uk.ctx.at.shared.infra.entity.workmanagementmultiple;

import java.io.Serializable;

import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * author hieult
 */
@Entity
@Table(name = "KSHMT_2WORK_MNG")
@AllArgsConstructor
@NoArgsConstructor
public class Kshmt2workMngMultiple extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String companyID;

	@Column(name = "USE_ATR")
	public int useATR;

	@Override
	protected Object getKey() {
		return companyID;
	}

}
