/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.infra.entity.flowmenu;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CCGMT_FLOWMENU")
public class CcgmtFlowMenu extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CcgmtFlowMenuPK ccgmtFlowMenuPK;

	@Column(name = "FILE_ID")
	public String fileID;

	@Column(name = "DEF_CLASS_ATR")
	public int defClassAtr;
	
	@Override
	protected Object getKey() {
		return ccgmtFlowMenuPK;
	}

}
