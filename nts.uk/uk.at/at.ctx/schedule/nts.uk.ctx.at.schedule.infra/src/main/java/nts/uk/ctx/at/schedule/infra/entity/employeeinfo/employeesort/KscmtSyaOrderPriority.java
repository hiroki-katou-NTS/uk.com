package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.employeesort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * KSCMT_SYA_ORDER_PRIORITY
 * 
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

	/** 並び替える項目の種類 **/
	@Column(name = "ITEM_TYPE")
	public int itemType;

	/** 並び替え方向 **/
	@Column(name = "ORDER_DIRECTION")
	public int orderDirection;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static List<KscmtSyaOrderPriority> toEntity(SortSetting domain) {
		List<KscmtSyaOrderPriority> priorities = new ArrayList<>();
		for (int i = 0; i < domain.getOrderedList().size(); i++) {
			// 並び替えの優先順 OrderedListの順
			KscmtSyaOrderPriorityPk pk = new KscmtSyaOrderPriorityPk(domain.getCompanyID(), i + 1);
			KscmtSyaOrderPriority priority = new KscmtSyaOrderPriority(pk,
					domain.getOrderedList().get(i).getType().value,
					domain.getOrderedList().get(i).getSortOrder().value);
			priorities.add(priority);
		}
		return priorities;

	}

}
