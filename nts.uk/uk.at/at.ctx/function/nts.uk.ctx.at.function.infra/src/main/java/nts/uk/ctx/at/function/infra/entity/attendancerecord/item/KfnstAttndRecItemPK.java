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
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	/** The record item id. */
	@Column(name = "RECORD_ITEM_ID")
	private String recordItemId;

	/**
	 * Instantiates a new kfnst attnd rec item PK.
	 */
	public KfnstAttndRecItemPK() {
	}

	/**
	 * Gets the record item id.
	 *
	 * @return the record item id
	 */
	public String getRecordItemId() {
		return this.recordItemId;
	}

	/**
	 * Sets the record item id.
	 *
	 * @param recordItemId the new record item id
	 */
	public void setRecordItemId(String recordItemId) {
		this.recordItemId = recordItemId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recordItemId == null) ? 0 : recordItemId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KfnstAttndRecItemPK other = (KfnstAttndRecItemPK) obj;
		if (recordItemId == null) {
			if (other.recordItemId != null)
				return false;
		} else if (!recordItemId.equals(other.recordItemId))
			return false;
		return true;
	}

	
}