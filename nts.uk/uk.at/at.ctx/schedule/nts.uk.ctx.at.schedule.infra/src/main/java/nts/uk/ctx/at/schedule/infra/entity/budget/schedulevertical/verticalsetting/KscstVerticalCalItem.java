package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_VERTICAL_CAL_ITEM")
public class KscstVerticalCalItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstVerticalCalItemPK kscstVerticalCalItemPK;

	/* 項目名 */
	@Column(name = "ITEM_NAME")
	public String itemName;
	
	/* 計算区分 */
	@Column(name = "CALCULATE_ATR")
	public int calculateAtr;
	
	/* 表示区分 */
	@Column(name = "DISPLAY_ATR")
	public int displayAtr;
	
	/* 累計区分 */
	@Column(name = "CUMULATIVE_ATR")
	public int cumulativeAtr;
	
	/* 属性 */
	@Column(name = "ATTRIBUTES")
	public int attributes;
	
	@Column(name = "ROUNDING_ATR")
	public int rounding;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "VERTICAL_CAL_CD", insertable = false, updatable = false)
    })
	public KscstVerticalCalSet verticalCalSet;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="kscstVerticalCalItem", orphanRemoval = true)
	public KscstVerticalItemOrder verticalItemOrder;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalCalItemPK;
	}
	
	public KscstVerticalCalItem(KscstVerticalCalItemPK kscstVerticalCalItemPK, String itemName, int calculateAtr, int displayAtr, int cumulativeAtr, 
			int attributes, int rounding, KscstVerticalItemOrder verticalItemOrder) {
		this.kscstVerticalCalItemPK = kscstVerticalCalItemPK;
		this.itemName = itemName;
		this.calculateAtr = calculateAtr;
		this.displayAtr = displayAtr;
		this.cumulativeAtr = cumulativeAtr;
		this.attributes = attributes;
		this.rounding = rounding;
		this.verticalItemOrder = verticalItemOrder;
	}
}
