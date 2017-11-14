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
@Table(name = "KSCMT_GEN_VERT_ITEM")
public class KscmtGenVertItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtGenVertItemPK kscmtGenVertItemPK;

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
	public KscmtGenVertSet genVertSet;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="genVertItem", orphanRemoval = true)
	public KscmtGenVertOrder genVertOrder;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtGenVertItemBuilt", orphanRemoval = true)
	public KscmtFormBuilt formBuilt;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtGenVertItemTime", orphanRemoval = true)
	public KscmtFormTime formTime;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtGenVertItemPeople", orphanRemoval = true)
	public KscmtFormPeople formPeople;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtGenVertItem", orphanRemoval = true)
	public KscstFormAmount amount;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtGenVertItem", orphanRemoval = true)
	public KscstFormNumerical numerical;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscmtGenVertItem", orphanRemoval = true)
	public KscstFormulaUnitPrice price;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtGenVertItemPK;
	}
	
	public KscmtGenVertItem(KscmtGenVertItemPK kscmtGenVertItemPK, String itemName, int calculateAtr, int displayAtr, int cumulativeAtr, 
			int attributes, int rounding, KscmtGenVertOrder genVertOrder, KscmtFormBuilt formBuilt, KscmtFormTime formTime, KscmtFormPeople formPeople, KscstFormAmount amount) {
		this.kscmtGenVertItemPK = kscmtGenVertItemPK;
		this.itemName = itemName;
		this.calculateAtr = calculateAtr;
		this.displayAtr = displayAtr;
		this.cumulativeAtr = cumulativeAtr;
		this.attributes = attributes;
		this.rounding = rounding;
		this.genVertOrder = genVertOrder;
		this.formBuilt = formBuilt;
		this.formTime = formTime;
		this.formPeople = formPeople;
		this.amount = amount;
	}
}
