package nts.ctx.at.record.infra.entity.divergencetime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Entity
@Table(name = "KMNMT_DIVERGENCE_REASON")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KmnmtDivergenceReason extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/*主キー*/
	@EmbeddedId
	public KmnmtDivergenceReasonPK kmnmtDivergenceReasonPK;
	/*乖離理由*/
	@Basic(optional = false)
	@Column(name = "DIVERGENCETIME_REASON")
	public String divReason;
	/*必須区分*/
	@Basic(optional = false)
	@Column(name = "REQUIRED_ATR")
	public int requiredAtr;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
