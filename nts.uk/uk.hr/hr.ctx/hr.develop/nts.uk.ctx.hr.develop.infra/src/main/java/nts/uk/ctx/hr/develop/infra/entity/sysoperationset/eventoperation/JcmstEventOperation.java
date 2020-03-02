package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.eventoperation;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JCMST_EVENT_OPERATION")
public class JcmstEventOperation extends UkJpaEntity{

	@EmbeddedId
    public JcmstEventOperationPK jcmstEventOperationPK;
	// イベントを使用する
	@Column(name = "USE_EVENT")
	public int useEvent;
	
	// 会社コード
	@Column(name = "CCD")
	public BigInteger ccd;
	
	@Override
	protected Object getKey() {
		return jcmstEventOperationPK;
	}

}
