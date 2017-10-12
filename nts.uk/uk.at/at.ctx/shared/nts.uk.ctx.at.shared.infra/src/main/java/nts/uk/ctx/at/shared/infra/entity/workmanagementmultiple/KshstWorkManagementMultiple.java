package nts.uk.ctx.at.shared.infra.entity.workmanagementmultiple;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * author hieult
 */
@Entity
@Table(name = "KSHST_WORK_MANAGEMENT")
@AllArgsConstructor
@NoArgsConstructor
public class KshstWorkManagementMultiple extends UkJpaEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshstWorkManagementMultiplePK kshstWorkManagementPK;
	
	@Column(name = "USE_ATR")
	public int useATR;
	@Override
	
	protected Object getKey() {
		return kshstWorkManagementPK;
	}

}
