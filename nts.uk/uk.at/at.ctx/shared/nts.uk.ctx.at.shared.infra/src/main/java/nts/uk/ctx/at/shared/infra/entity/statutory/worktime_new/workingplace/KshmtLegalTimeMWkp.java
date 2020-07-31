package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshmtLegalMon;

/**
 * 職場別月単位労働時間
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCMT_LEGAL_TIME_M_WKP")
public class KshmtLegalTimeMWkp extends KshmtLegalMon implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtLegalTimeMWkpPK pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
