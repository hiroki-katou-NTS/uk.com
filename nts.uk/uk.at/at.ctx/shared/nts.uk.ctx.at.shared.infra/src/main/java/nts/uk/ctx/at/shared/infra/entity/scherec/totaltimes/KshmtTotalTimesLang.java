package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author phongtq
 *
 */
@Setter
@Getter
@Entity
@Table(name = "KSHMT_TOTAL_TIMES_LANG")
public class KshmtTotalTimesLang extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtTotalTimesLangPK kshmtTotalTimesLangPK;
	
	/** The total times name. */
	@Column(name = "TOTAL_TIMES_NAME")
	public String totalTimesName;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshmtTotalTimesLangPK;
	}
}
