package nts.uk.ctx.at.shared.infra.entity.vacation.setting.temp.caredata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The primary key class for the KRCDT_TEMP_CARE_DATA database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcdtTempCareDataPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The sid. */
	@Column(name="SID")
	private String sid;

	/** The ymd. */
	@Column(name="YMD")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate ymd;

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcdtTempCareDataPK)) {
			return false;
		}
		KrcdtTempCareDataPK castOther = (KrcdtTempCareDataPK)other;
		return 
			this.sid.equals(castOther.sid)
			&& this.ymd.equals(castOther.ymd);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.sid.hashCode();
		hash = hash * prime + this.ymd.hashCode();
		
		return hash;
	}
}