package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TR_SEND_WORKTYPE")
public class KrcmtTrSendWorkType extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTrSendWorkTypePK pk;

	@Override
	protected Object getKey() {
		return pk;
	}

}
