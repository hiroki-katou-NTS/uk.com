/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.personinfoctgdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author danpv
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_CTG_DATA")
public class PpemtCtgData extends ContractUkJpaEntity{

	@Id
	@Column(name = "RECORD_ID")
	public String recordId;

	@Column(name = "P_INFO_CTG_ID")
	public String pInfoCtgId;

	@Column(name = "PID")
	public String pId;

	@Override
	protected Object getKey() {
		return recordId;
	}

}
