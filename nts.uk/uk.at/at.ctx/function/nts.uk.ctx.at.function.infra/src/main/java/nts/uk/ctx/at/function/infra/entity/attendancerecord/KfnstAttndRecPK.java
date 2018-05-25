package nts.uk.ctx.at.function.infra.entity.attendancerecord;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The primary key class for the KFNST_ATTND_REC database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KfnstAttndRecPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	private String cid;

	@Column(name = "EXPORT_CD")
	private long exportCd;

	@Column(name = "COLUMN_INDEX")
	private long columnIndex;

	@Column(name = "OUTPUT_ATR")
	private long outputAtr;

	@Column(name = "[POSITION]")
	private long position;

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public long getExportCd() {
		return this.exportCd;
	}

	public void setExportCd(long exportCd) {
		this.exportCd = exportCd;
	}

	public long getColumnIndex() {
		return this.columnIndex;
	}

	public void setColumnIndex(long columnIndex) {
		this.columnIndex = columnIndex;
	}

	public long getOutputAtr() {
		return this.outputAtr;
	}

	public void setOutputAtr(long outputAtr) {
		this.outputAtr = outputAtr;
	}

	public long getPosition() {
		return this.position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnstAttndRecPK)) {
			return false;
		}
		KfnstAttndRecPK castOther = (KfnstAttndRecPK) other;
		return this.cid.equals(castOther.cid) && (this.exportCd == castOther.exportCd)
				&& (this.columnIndex == castOther.columnIndex) && (this.outputAtr == castOther.outputAtr)
				&& (this.position == castOther.position);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.exportCd ^ (this.exportCd >>> 32)));
		hash = hash * prime + ((int) (this.columnIndex ^ (this.columnIndex >>> 32)));
		hash = hash * prime + ((int) (this.outputAtr ^ (this.outputAtr >>> 32)));
		hash = hash * prime + ((int) (this.position ^ (this.position >>> 32)));

		return hash;
	}
}