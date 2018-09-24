package nts.uk.ctx.at.record.infra.repository.monthly.procedure;

import javax.ejb.Stateless;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.procedure.ProcMonthlyData;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 実装：月別実績データストアドプロシージャ
 * @author shuichu_ishida
 */
@Stateless
public class JpaProcMonthlyDataImpl extends JpaRepository implements ProcMonthlyData {

	/** 月別実績データストアドプロシージャ */
	@Override
	public void execute(String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		StoredProcedureQuery stQuery = this.getEntityManager().createStoredProcedureQuery("PROCEDURE_MONTHLY_DATA");
		stQuery.registerStoredProcedureParameter("CId", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("SId", String.class, ParameterMode.IN)
				.registerStoredProcedureParameter("Ym", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("closure_id", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("closure_day", Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("isLastDay", Integer.class, ParameterMode.IN)
				.setParameter("CId", companyId)
				.setParameter("SId", employeeId)
				.setParameter("Ym", yearMonth.v())
				.setParameter("closure_id", closureId.value)
				.setParameter("closure_day", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0));
		stQuery.execute();
	}
}
