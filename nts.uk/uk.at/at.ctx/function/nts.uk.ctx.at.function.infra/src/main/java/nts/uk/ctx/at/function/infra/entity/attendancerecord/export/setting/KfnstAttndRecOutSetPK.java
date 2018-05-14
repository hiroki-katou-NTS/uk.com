package nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;

/**
 * The primary key class for the KFNST_ATTND_REC_OUT_SET database table.
 * 
 */
@AllArgsConstructor
@Embeddable
public class KfnstAttndRecOutSetPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The export cd. */
	@Column(name="EXPORT_CD")
	private long exportCd;

	/**
	 * Instantiates a new kfnst attnd rec out set PK.
	 */
	public KfnstAttndRecOutSetPK() {
	}
	
	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	public String getCid() {
		return this.cid;
	}
	
	/**
	 * Sets the cid.
	 *
	 * @param cid the new cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	/**
	 * Gets the export cd.
	 *
	 * @return the export cd
	 */
	public long getExportCd() {
		return this.exportCd;
	}
	
	/**
	 * Sets the export cd.
	 *
	 * @param exportCd the new export cd
	 */
	public void setExportCd(long exportCd) {
		this.exportCd = exportCd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnstAttndRecOutSetPK)) {
			return false;
		}
		KfnstAttndRecOutSetPK castOther = (KfnstAttndRecOutSetPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.exportCd == castOther.exportCd);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.exportCd ^ (this.exportCd >>> 32)));
		
		return hash;
	}
}