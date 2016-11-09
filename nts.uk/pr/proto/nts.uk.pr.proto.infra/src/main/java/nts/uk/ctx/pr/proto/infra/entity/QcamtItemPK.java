package nts.uk.ctx.pr.proto.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QcamtItemPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "ITEM_CD")
	public String itemCd;
	
	@Column(name = "CTG_ATR")
	public int ctgAtr;
}
