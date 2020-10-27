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
@Table(name = "KRCMT_ERAL_APPLICATION")
public class KrcmtEralApplication extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtEralApplicationPK krcmtEralApplicationPK;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "ERROR_CD", referencedColumnName = "ERROR_ALARM_CD", insertable = false, updatable = false) })
	public KwrmtErAlWorkRecord kwrmtErAlWorkRecord;

	@Override
	protected Object getKey() {
		return this.krcmtEralApplicationPK;
	}

	public KrcmtEralApplication(KrcmtEralApplicationPK krcmtEralApplicationPK) {
		super();
		this.krcmtEralApplicationPK = krcmtEralApplicationPK;
	}
	
	public String getErrorCd(){
		return krcmtEralApplicationPK.getErrorCd();
	}
	
	public int getAppTypeCd(){
		return krcmtEralApplicationPK.getAppTypeCd();
	}

}
