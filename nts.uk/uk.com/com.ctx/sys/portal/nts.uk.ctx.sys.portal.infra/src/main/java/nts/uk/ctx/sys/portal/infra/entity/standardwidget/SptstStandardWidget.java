package nts.uk.ctx.sys.portal.infra.entity.standardwidget;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SPTST_STANDARD_WIDGET")
public class SptstStandardWidget extends ContractUkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SptstStandardWidgetPK sptstStandardWidgetPK;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return sptstStandardWidgetPK;
	}

}
