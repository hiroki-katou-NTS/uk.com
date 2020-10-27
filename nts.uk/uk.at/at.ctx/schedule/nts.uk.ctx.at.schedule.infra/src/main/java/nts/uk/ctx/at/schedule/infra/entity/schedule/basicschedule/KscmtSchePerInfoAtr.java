package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
/**
 * 
 * @author trungtran
 *スケジュール個人情報区分
 */
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KSCMT_SCHE_PER_INFO_ATR")
public class KscmtSchePerInfoAtr extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscmtSchePerInfoAtrPk kscmtSchePerInfoAtrPk;

	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false)
    })
	public KscmtScheDispControl scheDispControl;
	
	@Override
	protected Object getKey() {
		return kscmtSchePerInfoAtrPk;
	}

	public KscmtSchePerInfoAtr(KscmtSchePerInfoAtrPk kscmtSchePerInfoAtrPk) {
		
		this.kscmtSchePerInfoAtrPk = kscmtSchePerInfoAtrPk;
	}
}
