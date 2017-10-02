package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalCalItemPK;
	}
}
