package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.displaysetting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * 個人条件の表示制御 表示する資格 entity
 *
 */
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "KSCMT_SYACND_DISPCTL_QUA")
public class KscmtSyacndDispCtlQua extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtSyacndDispCtlQuaPk pk;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
    	@PrimaryKeyJoinColumn(name = "CND_ATR", referencedColumnName = "CND_ATR")
    })
	public KscmtSyacndDispCtl kscmtSyacndDispCtl;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
