package nts.uk.ctx.at.record.infra.repository.workrecord.identificationstatus;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.month.KrcdtConfirmationMonth;
import nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.month.KrcdtConfirmationMonthPK;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class JpaConfirmationMonthRepository  extends JpaRepository implements ConfirmationMonthRepository{
    
	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtConfirmationMonth a "
			+ "WHERE a.krcdtConfirmationMonthPK.companyID = :companyID "
			+ "AND a.krcdtConfirmationMonthPK.employeeId = :employeeId "
			+ "AND a.krcdtConfirmationMonthPK.closureId = :closureId "
			+ "AND a.krcdtConfirmationMonthPK.closureDay = :closureDay "
			+ "AND a.krcdtConfirmationMonthPK.isLastDay = :isLastDayOfMonth "
			+ "AND a.krcdtConfirmationMonthPK.processYM = :processYM ";
	
	private static final String FIND_BY_SID_YM = "SELECT a FROM KrcdtConfirmationMonth a "
			+ "WHERE a.krcdtConfirmationMonthPK.companyID = :companyId "
			+ "AND a.krcdtConfirmationMonthPK.employeeId = :employeeId "
			+ "AND a.krcdtConfirmationMonthPK.processYM = :processYM ";
	
	private static final String FIND_BY_SOME_PROPERTY = "SELECT a FROM KrcdtConfirmationMonth a "
			+ "WHERE a.krcdtConfirmationMonthPK.employeeId IN :employeeIds "
			+ "AND a.krcdtConfirmationMonthPK.processYM = :processYM "
			+ "AND a.krcdtConfirmationMonthPK.closureDay = :closureDay "
			+ "AND a.krcdtConfirmationMonthPK.closureId = :closureId ";
	@Override
	public Optional<ConfirmationMonth> findByKey(String companyID, String employeeID, ClosureId closureId, ClosureDate closureDate, YearMonth processYM) {
		return this.queryProxy().find(new KrcdtConfirmationMonthPK(companyID, employeeID,
						closureId.value, closureDate.getClosureDay().v(),(closureDate.getLastDayOfMonth() ? 1 : 0), processYM.v()) , KrcdtConfirmationMonth.class).map(x -> x.toDomain());
	}

	@Override
	public void insert(ConfirmationMonth confirmationMonth) { 
		this.commandProxy().insert(new KrcdtConfirmationMonth(
				new KrcdtConfirmationMonthPK(confirmationMonth.getCompanyID().v(), confirmationMonth.getEmployeeId(),
						confirmationMonth.getClosureId().value, confirmationMonth.getClosureDate().getClosureDay().v(),(confirmationMonth.getClosureDate().getLastDayOfMonth() ? 1 : 0), confirmationMonth.getProcessYM().v()),
				confirmationMonth.getIndentifyYmd()));
	}

	@Override
	public void delete(String companyId, String employeeId, int closureId, int closureDate, boolean isLastDayOfMonth, int processYM) {
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK, KrcdtConfirmationMonth.class)
				.setParameter("companyID", companyId).setParameter("employeeId", employeeId)
				.setParameter("closureId", closureId)
				.setParameter("closureDay", closureDate)
				.setParameter("isLastDay", isLastDayOfMonth)
				.setParameter("processYM", processYM).executeUpdate();
	}

	@Override
	public List<ConfirmationMonth> findBySidAndYM(String employeeId, int processYM) {
		return this.queryProxy().query(FIND_BY_SID_YM, KrcdtConfirmationMonth.class)
				.setParameter("companyId", AppContexts.user().companyId()).setParameter("employeeId", employeeId)
				.setParameter("processYM", processYM).getList(x -> x.toDomain());
	}

	@Override
	public List<ConfirmationMonth> findBySomeProperty(List<String> employeeIds, int processYM, int closureDay,
			int closureId) {
		return this.queryProxy().query(FIND_BY_SOME_PROPERTY, KrcdtConfirmationMonth.class)
				.setParameter("employeeIds", employeeIds).setParameter("processYM", processYM)
				.setParameter("closureDay", closureDay).setParameter("closureId", closureId)
				.getList(x -> x.toDomain());
	}

}
