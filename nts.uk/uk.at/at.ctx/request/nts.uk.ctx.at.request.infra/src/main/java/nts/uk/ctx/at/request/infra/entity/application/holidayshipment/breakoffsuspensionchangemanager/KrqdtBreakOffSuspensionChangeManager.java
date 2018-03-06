package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.breakoffsuspensionchangemanager;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 振休申請休出変更管理
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_BRK_OFF_SUP_CHG_MNG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtBreakOffSuspensionChangeManager extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtBreakOffSuspensionChangeManagerPK pk;

	@Override
	protected Object getKey() {
		return pk;
	}

}
