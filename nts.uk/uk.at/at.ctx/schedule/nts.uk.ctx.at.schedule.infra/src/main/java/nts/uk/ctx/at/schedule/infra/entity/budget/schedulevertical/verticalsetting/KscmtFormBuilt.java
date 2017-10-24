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
@Table(name = "KSCMT_FORM_BUILT")
public class KscmtFormBuilt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtFormBuiltPK kscmtFormBuiltPK;
	
	/* 設定方法 */
	@Column(name = "SETTING_METHOD_1")
	public int settingMethod1;
	
	@Column(name = "VERTICAL_CAL_ITEM_1")
	public int verticalCalItem1;
	
	/* 縦計入力項目 */
	@Column(name = "VERTICAL_INPUT_ITEM_1")
	public int verticalInputItem1;
	
	/* 設定方法 */
	@Column(name = "SETTING_METHOD_2")
	public int settingMethod2;
	
	@Column(name = "VERTICAL_CAL_ITEM_2")
	public int verticalCalItem2;
	
	/* 縦計入力項目 */
	@Column(name = "VERTICAL_INPUT_ITEM_2")
	public int verticalInputItem2;
	
	/* 演算子区分 */
	@Column(name = "OPERATOR_ATR")
	public int operatorAtr;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtFormBuiltPK;
	}
}
