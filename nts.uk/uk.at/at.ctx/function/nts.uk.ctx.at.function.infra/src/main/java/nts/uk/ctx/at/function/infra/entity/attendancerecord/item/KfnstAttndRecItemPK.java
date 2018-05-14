package nts.uk.ctx.at.function.infra.entity.attendancerecord.item;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

/**
 * The primary key class for the KFNST_ATTND_REC_ITEM database table.
 * 
 */
@Embeddable
@AllArgsConstructor
public class KfnstAttndRecItemPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The export cd. */
	@Column(name="EXPORT_CD")
	private long exportCd;

	/** The column index. */
	@Column(name="COLUMN_INDEX")
	private long columnIndex;

	/** The position. */
	@Column(name="[POSITION]")
	private long position;

	/** The output atr. */
	@Column(name="OUTPUT_ATR")
	private long outputAtr;

	/** The time item id. */
	@Column(name="TIME_ITEM_ID")
	private long timeItemId;

	/**
	 * Instantiates a new kfnst attnd rec item PK.
	 */
	public KfnstAttndRecItemPK() {
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
	
	/**
	 * Gets the column index.
	 *
	 * @return the column index
	 */
	public long getColumnIndex() {
		return this.columnIndex;
	}
	
	/**
	 * Sets the column index.
	 *
	 * @param columnIndex the new column index
	 */
	public void setColumnIndex(long columnIndex) {
		this.columnIndex = columnIndex;
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public long getPosition() {
		return this.position;
	}
	
	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(long position) {
		this.position = position;
	}
	
	/**
	 * Gets the output atr.
	 *
	 * @return the output atr
	 */
	public long getOutputAtr() {
		return this.outputAtr;
	}
	
	/**
	 * Sets the output atr.
	 *
	 * @param outputAtr the new output atr
	 */
	public void setOutputAtr(long outputAtr) {
		this.outputAtr = outputAtr;
	}
	
	/**
	 * Gets the time item id.
	 *
	 * @return the time item id
	 */
	public long getTimeItemId() {
		return this.timeItemId;
	}
	
	/**
	 * Sets the time item id.
	 *
	 * @param timeItemId the new time item id
	 */
	public void setTimeItemId(long timeItemId) {
		this.timeItemId = timeItemId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KfnstAttndRecItemPK)) {
			return false;
		}
		KfnstAttndRecItemPK castOther = (KfnstAttndRecItemPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.exportCd == castOther.exportCd)
			&& (this.columnIndex == castOther.columnIndex)
			&& (this.position == castOther.position)
			&& (this.outputAtr == castOther.outputAtr)
			&& (this.timeItemId == castOther.timeItemId);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.exportCd ^ (this.exportCd >>> 32)));
		hash = hash * prime + ((int) (this.columnIndex ^ (this.columnIndex >>> 32)));
		hash = hash * prime + ((int) (this.position ^ (this.position >>> 32)));
		hash = hash * prime + ((int) (this.outputAtr ^ (this.outputAtr >>> 32)));
		hash = hash * prime + ((int) (this.timeItemId ^ (this.timeItemId >>> 32)));
		
		return hash;
	}
}