package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshmtLegalMon;

/**
 * 雇用別月単位労働時間
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_LEGAL_TIME_M_EMP")
public class KshmtLegalTimeMEmp extends KshmtLegalMon implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtLegalTimeMEmpPK pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
