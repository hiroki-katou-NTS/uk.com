package nts.uk.ctx.at.record.infra.entity.daily.attendanceleavinggate;

import java.io.Serializable;
import javax.persistence.*;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The primary key class for the KRCDT_DAY_PC_LOGON_INFO database table.
 * 
 */
@Embeddable
public class KrcdtDayPcLogonInfoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String sid;

	@Column(name = "YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate ymd;

	@Column(name = "PC_LOG_NO")
	public int pcLogNo;

	public KrcdtDayPcLogonInfoPK() {
	}

	public KrcdtDayPcLogonInfoPK(String sid, GeneralDate ymd, int pcLogNo) {
		super();
		this.sid = sid;
		this.ymd = ymd;
		this.pcLogNo = pcLogNo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcdtDayPcLogonInfoPK)) {
			return false;
		}
		KrcdtDayPcLogonInfoPK castOther = (KrcdtDayPcLogonInfoPK) other;
		return this.sid.equals(castOther.sid) && this.ymd.equals(castOther.ymd) 
				&& this.pcLogNo == castOther.pcLogNo;
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sid.hashCode();
		hash = hash * prime + this.ymd.hashCode();
		hash = hash * prime + this.pcLogNo;

		return hash;
	}
}