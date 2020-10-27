package nts.uk.ctx.pereg.infra.entity.person.setting.selectionitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtSelectionHistPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String histId;

}
