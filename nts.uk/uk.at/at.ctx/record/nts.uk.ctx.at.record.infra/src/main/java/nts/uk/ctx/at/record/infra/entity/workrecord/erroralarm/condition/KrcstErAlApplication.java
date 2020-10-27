/**
 * 11:12:12 AM Dec 6, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
//import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCST_ER_AL_APPLICATION")
public class KrcstErAlApplication extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstErAlApplicationPK krcstErAlApplicationPK;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "ERROR_CD", referencedColumnName = "ERROR_ALARM_CD", insertable = false, updatable = false) })
	public KwrmtErAlWorkRecord kwrmtErAlWorkRecord;

	@Override
	protected Object getKey() {
		return this.krcstErAlApplicationPK;
	}

	public KrcstErAlApplication(KrcstErAlApplicationPK krcstErAlApplicationPK) {
		super();
		this.krcstErAlApplicationPK = krcstErAlApplicationPK;
	}
	
	public String getErrorCd(){
		return krcstErAlApplicationPK.getErrorCd();
	}
	
	public int getAppTypeCd(){
		return krcstErAlApplicationPK.getAppTypeCd();
	}

}
