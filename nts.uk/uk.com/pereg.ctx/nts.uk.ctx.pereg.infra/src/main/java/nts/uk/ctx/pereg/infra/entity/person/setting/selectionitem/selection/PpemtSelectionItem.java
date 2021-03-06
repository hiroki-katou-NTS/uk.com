package nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * aa
 * @author tuannv
 *
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_SELECTION_ITEM")
public class PpemtSelectionItem extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtSelectionItemPK selectionId;

	@Basic(optional = true)
	@Column(name = "HIST_ID")
	public String histId;

	@Basic(optional = true)
	@Column(name = "SELECTION_CD")
	public String selectionCd;

	@Basic(optional = true)
	@Column(name = "SELECTION_NAME")
	public String selectionName;

	@Basic(optional = true)
	@Column(name = "EXTERNAL_CD")
	public String externalCd;

	@Basic(optional = true)
	@Column(name = "MEMO")
	public String memo;

	@Override
	protected Object getKey() {
		return selectionId;
	}
}
