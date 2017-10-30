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
@Table(name = "KSCST_FORMULA_MONEY")
public class KscstFormulaMoney extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstFormulaMoneyPK kscstFormulaMoneyPK;
	
    /* カテゴリ区分 */
	@Column(name = "CATEGORY_INDICATOR")
	public int categoryIndicator;
    
    /* 実績表示区分 */
	@Column(name = "ACTUAL_DISPLAY_ATR")
	public int actualDisplayAtr;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFormulaMoneyPK;
	}
}
