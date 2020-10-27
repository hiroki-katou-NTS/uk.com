package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FORM_UNITPRICE")
public class KscstFormulaUnitPrice extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主キー **/
	@EmbeddedId
	public KscstFormulaUnitPricePK kscstFormulaUnitPricePK;

	/**出勤区分**/
	@Column(name = "ATTENDANCE_ATR")
	public int attendanceAtr;
	
	/**単価**/
	@Column(name = "UNIT_PRICE")
	public int unitPrice;
	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "VERTICAL_CAL_CD", insertable = false, updatable = false),
			@JoinColumn(name = "VERTICAL_CAL_ITEM_ID", referencedColumnName = "ITEM_ID", insertable = false, updatable = false) })
	public KscmtVerticalItem kscmtVerticalItemUnitPrice;
	@Override
	protected Object getKey() {
		return kscstFormulaUnitPricePK;
	} 

}
