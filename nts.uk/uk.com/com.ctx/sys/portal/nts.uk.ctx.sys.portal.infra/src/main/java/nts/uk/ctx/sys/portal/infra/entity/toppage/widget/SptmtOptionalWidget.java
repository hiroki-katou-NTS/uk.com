package nts.uk.ctx.sys.portal.infra.entity.toppage.widget;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPTMT_OPTIONAL_WIDGET")
public class SptmtOptionalWidget  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SptmtOptionalWidgetPK sptmtOptionalWidgetPK;

	@Override
	protected Object getKey() {
		return sptmtOptionalWidgetPK;
	}

}
