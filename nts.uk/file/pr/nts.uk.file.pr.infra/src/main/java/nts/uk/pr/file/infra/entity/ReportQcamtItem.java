/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The Class ReportQcamtItem.
 */
@Table(name = "QCAMT_ITEM")
@Entity
public class ReportQcamtItem implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The qcamt item PK. */
    @EmbeddedId
    public ReportQcamtItemPK qcamtItemPK;
    
    /** The fix atr. */
    @Basic(optional = false)
    @Column(name = "FIX_ATR")
    public int fixAtr;
    
    /** The item name. */
    @Basic(optional = false)
    @Column(name = "ITEM_NAME")
    public String itemName;
    
    /** The item ab name. */
    @Basic(optional = false)
    @Column(name = "ITEM_AB_NAME")
    public String itemAbName;
    
    /** The item ab name E. */
    @Column(name = "ITEM_AB_NAME_E")
    public String itemAbNameE;
    
    /** The item ab name O. */
    @Column(name = "ITEM_AB_NAME_O")
    public String itemAbNameO;
    
    /** The disp set. */
    @Basic(optional = false)
    @Column(name = "DISP_SET")
    public int dispSet;
    
    /** The unite cd. */
    @Column(name = "UNITE_CD")
    public String uniteCd;
    
    /** The zero disp set. */
    @Column(name = "ZERO_DISP_SET")
    public int zeroDispSet;
    
    /** The item disp atr. */
    @Column(name = "ITEM_DISP_ATR")
    public int itemDispAtr;
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qcamtItemPK == null) ? 0 : qcamtItemPK.hashCode());
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
		ReportQcamtItem other = (ReportQcamtItem) obj;
		if (qcamtItemPK == null) {
			if (other.qcamtItemPK != null)
				return false;
		} else if (!qcamtItemPK.equals(other.qcamtItemPK))
			return false;
		return true;
	}
    
    
}
