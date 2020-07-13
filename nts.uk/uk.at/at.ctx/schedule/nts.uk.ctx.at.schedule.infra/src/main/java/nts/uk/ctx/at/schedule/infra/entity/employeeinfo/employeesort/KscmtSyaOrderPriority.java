package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_SYA_ORDER_PRIORITY
 * @author kingo
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SYA_ORDER_PRIORITY")
public class KscmtSyaOrderPriority extends ContractUkJpaEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtSyaOrderPriorityPk pk;
	
	/**並び替える項目の種類**/
	@Column(name = "ITEM_TYPE")
	public int itemType;
	
	/**並び替え方向**/
	@Column(name = "ORDER_DIRECTION")
	public int orderDirection;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
