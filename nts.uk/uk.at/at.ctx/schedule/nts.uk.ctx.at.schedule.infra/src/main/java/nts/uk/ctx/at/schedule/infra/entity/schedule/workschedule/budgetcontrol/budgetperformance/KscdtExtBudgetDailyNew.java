package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.budgetcontrol.budgetperformance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActualValues;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 日次の外部予算実績
 * @author HieuLt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCDT_EXT_BUDGET_DAILY")
public class KscdtExtBudgetDailyNew extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtExtBudgetDailyPkNew pk;
	
	/**外部予算実績項目**/										
	@Column(name = "BUDGET_ATR")								
	public int budgetATR;
	
	/** 日次の外部予算実績 **/
	@Column(name = "VAL")
	public int  val;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	

}
