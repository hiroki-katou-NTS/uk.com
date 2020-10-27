package nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.care.interimdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcdtInterimCarePK implements Serializable {

	/** The Constant serialVersionUID. */
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The sid. */
	@Column(name = "SID")
	public String sid;

	/** The ymd. */
	@Column(name = "YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate ymd;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcdtInterimCarePK)) {
			return false;
		}
		KrcdtInterimCarePK castOther = (KrcdtInterimCarePK) other;
		return this.sid.equals(castOther.sid) && this.ymd.equals(castOther.ymd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sid.hashCode();
		hash = hash * prime + this.ymd.hashCode();

		return hash;
	}
}