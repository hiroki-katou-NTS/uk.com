package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 振休振出同時申請管理
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_COMP_LEAVE_SIL_MNG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtCompltLeaveSimMana extends ContractUkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrqdtCompltLeaveSimManaPK pk;

	/**
	 * 同期中
	 */
	@Basic(optional = false)
	@Column(name = "SYNCING")
	private int syncing;

	@Override
	protected Object getKey() {
		return pk;
	}

}
