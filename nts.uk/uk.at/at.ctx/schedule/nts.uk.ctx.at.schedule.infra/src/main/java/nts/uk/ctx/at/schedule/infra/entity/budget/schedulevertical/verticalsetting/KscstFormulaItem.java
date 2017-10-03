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
@Table(name = "KSCST_FORMULA_ITEM")
public class KscstFormulaItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstFormulaItemPK kscstFormulaItemPK;

	/* 縦計入力項目 */
	@Column(name = "VERTICAL_INPUT_ITEM")
	public int verticalInputItem;
	
	/* 設定方法 */
	@Column(name = "SETTING_METHOD")
	public int settingMethod;
	
	/* 演算子区分 */
	@Column(name = "OPERATOR_ATR")
	public int operatorAtr;
	
	/* 順番 */
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFormulaItemPK;
	}
}
