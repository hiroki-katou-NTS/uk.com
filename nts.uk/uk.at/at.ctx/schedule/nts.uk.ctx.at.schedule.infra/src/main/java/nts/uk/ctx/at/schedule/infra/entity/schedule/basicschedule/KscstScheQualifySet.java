package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * スケジュール資格設定
 * 
 * @author trungtran
 *
 */

@Entity
@NoArgsConstructor
@Table(name = "KSCST_SCHE_QUALIFY_SET")
public class KscstScheQualifySet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstScheQualifySetPK kscstScheQualifySetPK;

	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    })
	public KscmtScheDispControl scheDispControl;

	@Override
	protected Object getKey() {
		return this.kscstScheQualifySetPK;
	}

	public KscstScheQualifySet(KscstScheQualifySetPK kscstScheQualifySetPK) {
		
		this.kscstScheQualifySetPK = kscstScheQualifySetPK;
	}
}
