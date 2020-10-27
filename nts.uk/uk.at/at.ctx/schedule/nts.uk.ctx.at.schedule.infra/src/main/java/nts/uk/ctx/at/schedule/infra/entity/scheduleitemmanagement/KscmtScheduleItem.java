package nts.uk.ctx.at.schedule.infra.entity.scheduleitemmanagement;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SCHE_ITEM")
public class KscmtScheduleItem extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtScheduleItemPK kscmtScheduleItemPK;
	
	/*予定項目名称*/
	@Column(name = "SCHEDULE_ITEM_NAME")
	public String scheduleItemName;
	
	/*予定項目の属性*/
	@Column(name = "SCHEDULE_ITEM_ATR")
	public int scheduleItemAtr;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="scheduleItem", orphanRemoval = true)
	public KscmtScheItemOrder scheduleItemOrder;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtScheduleItemPK;
	}
}
