package nts.uk.ctx.hr.notice.infra.entity.report;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNMT_RPT_LT_ITEM")
@Entity
public class JhnRptLtItem extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public JhnRptLtItemPk jhnRptLtItemPk;
	
	
	@Basic(optional = false)
    @Column(name = "RPT_LAYOUT_CD")
	public String rptLayouCd;
	
	@Basic(optional = false)
    @Column(name = "RPT_LAYOUT_NAME")
	public String rptLayouName;
	
	@Basic(optional = false)
    @Column(name = "DSP_ORDER")
	public int disOrder;
	
	@Basic(optional = false)
    @Column(name = "LAYOUT_ITEM_TYPE")
	public int layoutItemType;
	
	
	@Basic(optional = false)
    @Column(name = "CONTRACT_CD")
	public String contractCd;
	
	@Basic(optional = false)
    @Column(name = "CATEGORY_ID")
	public String categoryId;
	
	@Basic(optional = false)
    @Column(name = "CATEGORY_NAME")
	public String categoryName;
	
	@Basic(optional = false)
    @Column(name = "ITEM_ID")
	public String itemId;
	
	@Basic(optional = false)
    @Column(name = "ITEM_NAME")
	public String itemName;
	
	@Basic(optional = false)
    @Column(name = "FIXED_ATR")
	public boolean fixedAtr;
	
	@Basic(optional = false)
    @Column(name = "ABOLITION_ATR")
	public int abolitionAtr;

	@Basic(optional = false)
    @Column(name = "REFLECT_ID")
	public int reflectId;
	
	@Basic(optional = false)
    @Column(name = "LAYOUT_DISORDER")
	public int displayOrder;
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.jhnRptLtItemPk;
	}

}
