package nts.uk.ctx.at.request.infra.entity.setting.company.applicationsetting;

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
@Table(name = "KRQST_PROXY_APP_SET")
public class KrqstProxyAppSet extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KrqstProxyAppSetPK krqstProxyAppSetPK;
	@Override
	protected Object getKey() {
		return krqstProxyAppSetPK;
	}
}
