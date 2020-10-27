package nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem.selection;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tuannv
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtSelectionItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "SELECTION_ID")
	public String selectionId;
}
