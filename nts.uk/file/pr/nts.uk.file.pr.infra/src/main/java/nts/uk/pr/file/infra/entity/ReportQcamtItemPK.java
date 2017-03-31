/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class ReportQcamtItemPK.
 */
@Embeddable
public class ReportQcamtItemPK {

    /** The ccd. */
    @Basic(optional = false)
    @Column(name = "CCD")
    public String ccd;
    
    /** The ctg atr. */
    @Basic(optional = false)
    @Column(name = "CTG_ATR")
    public int ctgAtr;
    
    /** The item cd. */
    @Basic(optional = false)
    @Column(name = "ITEM_CD")
    public String itemCd;
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccd == null) ? 0 : ccd.hashCode());
		result = prime * result + ctgAtr;
		result = prime * result + ((itemCd == null) ? 0 : itemCd.hashCode());
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
		ReportQcamtItemPK other = (ReportQcamtItemPK) obj;
		if (ccd == null) {
			if (other.ccd != null)
				return false;
		} else if (!ccd.equals(other.ccd))
			return false;
		if (ctgAtr != other.ctgAtr)
			return false;
		if (itemCd == null) {
			if (other.itemCd != null)
				return false;
		} else if (!itemCd.equals(other.itemCd))
			return false;
		return true;
	}
}