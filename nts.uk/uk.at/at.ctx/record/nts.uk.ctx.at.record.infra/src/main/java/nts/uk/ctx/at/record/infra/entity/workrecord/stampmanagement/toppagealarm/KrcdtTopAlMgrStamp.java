package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.toppagealarm;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TOP_AL_MGR_STAMP")
public class KrcdtTopAlMgrStamp extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	public KrcdtTopAlMgrStampPk pk;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column (name = "CID")
	public String cid;
	
	@Basic(optional = false)
	@Column (name = "ROGER_FLAG")
	public int roger_flag;
	
	@Override
	protected Object getKey() {
		
		return pk;
	}

}
