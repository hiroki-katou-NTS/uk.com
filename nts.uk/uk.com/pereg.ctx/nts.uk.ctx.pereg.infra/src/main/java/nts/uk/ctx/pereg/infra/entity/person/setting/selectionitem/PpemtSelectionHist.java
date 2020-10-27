package nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_SELECTION_HIST")
public class PpemtSelectionHist extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtSelectionHistPK histidPK;

	@Basic(optional = false)
	@Column(name = "SELECTION_ITEM_ID")
	public String selectionItemId;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "START_DATE")
	public GeneralDate startDate;

	@Basic(optional = false)
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return histidPK;
	}

}
