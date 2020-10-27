package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCMT_OTK_HD_CK_WKTP_NTGT database table.
 * 
 */
@Entity
@Table(name="KRCMT_OTK_HD_CK_WKTP_NTGT")
//@NamedQuery(name="KrcmtOtkHdCkWktpNtgt.findAll", query="SELECT k FROM KrcmtOtkHdCkWktpNtgt k")
public class KrcctOtkWtNonTarget extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcctOtkWtPK id;

	@ManyToOne
	@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
	public KrcmtOtkHdCk krcmtOtkHdCk;
	
	public KrcctOtkWtNonTarget() {
	}

	public KrcctOtkWtNonTarget(KrcctOtkWtPK id) {
		super();
		this.id = id;
	}
	
	public KrcctOtkWtNonTarget(String cid, String workTypeCode) {
		super();
		this.id = new KrcctOtkWtPK(cid, workTypeCode);
	}

	@Override
	protected Object getKey() {
		return this.id;
	}

}