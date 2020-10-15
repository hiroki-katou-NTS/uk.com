package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share.KshmtLegalMon;

/**
 * 社員別月単位労働時間
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KSRMT_LEGAL_TIME_M_SYA")
public class KshmtLegalTimeMSya extends KshmtLegalMon implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Getter
	@EmbeddedId
	public KshmtLegalTimeMSyaPK pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
