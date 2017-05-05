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
import nts.uk.shr.infra.data.entity.AggregateTableEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CCGMT_FLOWMENU")
public class CcgmtFlowMenu extends AggregateTableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CcgmtFlowMenuPK ccgmtFlowMenuPK;

	@Column(name = "FILE_ID")
	public String fileID;

	@Column(name = "FILE_NAME")
	public String fileName;

	@Column(name = "DEF_CLASS_ATR")
	public int defClassAtr;

	@Override
	protected Object getKey() {
		return ccgmtFlowMenuPK;
	}

}
