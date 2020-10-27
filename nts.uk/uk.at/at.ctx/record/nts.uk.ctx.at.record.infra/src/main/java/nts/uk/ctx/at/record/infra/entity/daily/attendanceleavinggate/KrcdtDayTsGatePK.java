package nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate;

import java.io.Serializable;
import javax.persistence.*;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The primary key class for the KRCDT_DAY_TS_GATE database table.
 * 
 */
@Embeddable
public class KrcdtDayTsGatePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String sid;

	@Column(name = "YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate ymd;

	@Column(name = "AL_NO")
	public int alNo;
	
	public KrcdtDayTsGatePK() {
	}

	public KrcdtDayTsGatePK(String sid, GeneralDate ymd, int alNo) {
		super();
		this.sid = sid;
		this.ymd = ymd;
		this.alNo = alNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcdtDayTsGatePK)) {
			return false;
		}
		KrcdtDayTsGatePK castOther = (KrcdtDayTsGatePK) other;
		return this.sid.equals(castOther.sid) && this.ymd.equals(castOther.ymd) &&
				this.alNo == castOther.alNo;
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sid.hashCode();
		hash = hash * prime + this.ymd.hashCode();
		hash = hash * prime + this.alNo;

		return hash;
	}
}