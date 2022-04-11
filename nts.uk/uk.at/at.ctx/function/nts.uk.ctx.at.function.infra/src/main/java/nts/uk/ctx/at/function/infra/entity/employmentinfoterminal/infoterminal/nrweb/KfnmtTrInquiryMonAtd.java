package nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.nrweb;

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
@Table(name = "	KFNMT_TR_INQUIRY_MON_ATD")
public class KfnmtTrInquiryMonAtd extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtTrInquiryMonAtdPK pk;

	/**
	 * 順序
	 */
	@Column(name = "NO")
	public int no;

	@Override
	protected Object getKey() {
		return pk;
	}

}
