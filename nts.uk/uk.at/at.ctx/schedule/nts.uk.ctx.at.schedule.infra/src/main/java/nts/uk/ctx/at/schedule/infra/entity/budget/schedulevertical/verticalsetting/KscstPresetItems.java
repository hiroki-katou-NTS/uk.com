package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_PRESET_ITEMS")
public class KscstPresetItems extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstPresetItemsPK kscstPresetItemsPK;

	/*名称 */
	@Column(name = "NAME")
	public String name;
	
	/* 属性 */
	@Column(name = "ATTRIBUTES")
	public int attributes;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstPresetItemsPK;
	}
}
