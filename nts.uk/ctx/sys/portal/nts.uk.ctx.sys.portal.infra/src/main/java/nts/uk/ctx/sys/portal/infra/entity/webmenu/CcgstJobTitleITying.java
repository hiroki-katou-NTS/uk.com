package nts.uk.ctx.sys.portal.infra.entity.webmenu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGST_JOB_TITLE_TYING")
public class CcgstJobTitleITying extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public CcgstJobTitleITyingPK ccgstJobTitleITyingPK;
	

	@Override
	protected Object getKey() {
		
		return ccgstJobTitleITyingPK;
	}
	
	

}
