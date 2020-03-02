package nts.uk.ctx.hr.notice.infra.entity.report;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class JhnRptLtItemPk implements Serializable{
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "CID")
	public String cid; 
	
	@Basic(optional = false)
    @Column(name = "RPT_LAYOUT_ID")
	public int rptLayouId;
	
	@Basic(optional = false)
    @Column(name = "CATEGORY_CD")
	public String categoryCd;
	
	@Basic(optional = false)
    @Column(name = "ITEM_CD")
	public String itemCd;
	
}
