package nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinputusagesetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset.KrcdtTaskFavDayDispOrderPk;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MAN_HR_USE")
public class KrcmtManHrUse extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public int cId;
	
	@Column(name = "USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return this.cId;
	}
}
