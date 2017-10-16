package entity.person.setting.selectionitem.selection;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author tuannv
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_SEL_ITEM_ORDER")
public class PpemtSelItemOrder extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtSelItemOrderPK selectionIdPK;

	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String histidPK;

	@Basic(optional = false)
	@Column(name = "DISPORDER")
	public int disPorder;

	@Basic(optional = false)
	@Column(name = "INIT_SELECTION")
	public int initSelection;

	@Override
	protected Object getKey() {
		return selectionIdPK;
	}
}
