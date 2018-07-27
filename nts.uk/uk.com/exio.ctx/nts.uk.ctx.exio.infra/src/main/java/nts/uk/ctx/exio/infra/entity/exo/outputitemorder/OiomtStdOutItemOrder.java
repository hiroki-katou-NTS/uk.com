package nts.uk.ctx.exio.infra.entity.exo.outputitemorder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.outputitem.ConditionSettingCode;
import nts.uk.ctx.exio.dom.exo.outputitemorder.OutputItemCode;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 出力項目並び順(定型)
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_OUT_ITEM_ORDER")
public class OiomtStdOutItemOrder extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdOutItemOrderPk stdOutItemOrderPk;

	/**
	 * 順序
	 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Override
	protected Object getKey() {
		return stdOutItemOrderPk;
	}

	public StandardOutputItemOrder toDomain() {
		return new StandardOutputItemOrder(this.stdOutItemOrderPk.cid,
				new OutputItemCode(this.stdOutItemOrderPk.outItemCd),
				new ConditionSettingCode(this.stdOutItemOrderPk.condSetCd), this.displayOrder);
	}

	public static OiomtStdOutItemOrder toEntity(StandardOutputItemOrder domain) {
		return new OiomtStdOutItemOrder(new OiomtStdOutItemOrderPk(domain.getCid(), domain.getOutputItemCode().v(),
				domain.getConditionSettingCode().v()), domain.getDisplayOrder());
	}
	
	public static List<OiomtStdOutItemOrder> toEntity(List<StandardOutputItemOrder> listDomain) {
		List<OiomtStdOutItemOrder> listOiomtStdOutItemOrder = new ArrayList<OiomtStdOutItemOrder>();
		for (StandardOutputItemOrder standardOutputItemOrder : listDomain) {
			listOiomtStdOutItemOrder.add(OiomtStdOutItemOrder.toEntity(standardOutputItemOrder));
		}
		return listOiomtStdOutItemOrder;
	}
}
