package nts.uk.ctx.at.record.infra.repository.monthlyclosureupdatelog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseLog;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaMonthlyClosureUpdateLogRepository extends JpaRepository implements MonthlyClosureUpdateLogRepository {

	@Override
	public List<MonthlyClosureUpdateLog> getAll(String companyId) {
		String sql = "SELECT c FROM KrcdtMcloseLog c WHERE c.companyId = :companyId ORDER BY c.executionDateTime ASC";
		return this.queryProxy().query(sql, KrcdtMcloseLog.class).setParameter("companyId", companyId)
				.getList(c -> c.toDomain());
	}

	@Override
	public void add(MonthlyClosureUpdateLog domain) {
		this.commandProxy().insert(KrcdtMcloseLog.fromDomain(domain));
	}

	@Override
	public Optional<MonthlyClosureUpdateLog> getLogById(String id) {
		Optional<KrcdtMcloseLog> opt = this.queryProxy().find(id, KrcdtMcloseLog.class);
		if (opt.isPresent())
			return Optional.of(opt.get().toDomain());
		else
			return Optional.empty();
	}

	@Override
	public void updateStatus(MonthlyClosureUpdateLog domain) {
		Optional<KrcdtMcloseLog> opt = this.queryProxy().find(domain.getId(), KrcdtMcloseLog.class);
		if (opt.isPresent()) {
			KrcdtMcloseLog entity = opt.get();
			entity.completeStatus = domain.getCompleteStatus().value;
			entity.executionStatus = domain.getExecutionStatus().value;
			this.commandProxy().update(entity);
		} else
			throw new RuntimeException("Can not find MonthlyClosureUpdateLog with id=" + domain.getId());
	}
	
	@Override
	public void updateStatusForCompany(String cid) {

		Connection con = this.getEntityManager().unwrap(Connection.class);

		String sqlQuery = "update KRCDT_MCLOSE_LOG set EXECUTE_STATUS = " + 2 + " where CID = '" + cid
				+ "' and EXECUTE_STATUS != " + 2;
		try {
			con.createStatement().executeUpdate(JDBCUtil.toUpdateWithCommonField(sqlQuery));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<MonthlyClosureUpdateLog> getLogRunningOrNotConfirmByEmpId(String companyId, int closureId,
			String employeeId) {
		String sql = "SELECT c FROM KrcdtMcloseLog c WHERE c.companyId = :companyId "
				+ "AND c.closureId = :closureId AND c.executeEmployeeId = :executeEmployeeId "
				+ "AND (c.executionStatus = :runningStatus OR c.executionStatus = :notConfirmStatus)";
		int running = MonthlyClosureExecutionStatus.RUNNING.value;
		int notConfirm = MonthlyClosureExecutionStatus.COMPLETED_NOT_CONFIRMED.value;
		Optional<KrcdtMcloseLog> opt = this.queryProxy().query(sql, KrcdtMcloseLog.class)
				.setParameter("companyId", companyId).setParameter("closureId", employeeId)
				.setParameter("executeEmployeeId", employeeId).setParameter("runningStatus", running)
				.setParameter("notConfirmStatus", notConfirm).getSingle();
		return opt.isPresent() ? Optional.ofNullable(opt.get().toDomain()) : Optional.empty();
	}

	@Override
	public List<MonthlyClosureUpdateLog> getAllByClosureId(String companyId, int closureId) {
		String sql = "SELECT c FROM KrcdtMcloseLog c WHERE c.companyId = :companyId AND c.closureId = :closureId";
		return this.queryProxy().query(sql, KrcdtMcloseLog.class).setParameter("companyId", companyId)
				.setParameter("closureId", closureId).getList(c -> c.toDomain());
	}

	@Override
	public List<MonthlyClosureUpdateLog> getAllSortedByExeDate(String companyId) {
		String sql = "SELECT c FROM KrcdtMcloseLog c WHERE c.companyId = :companyId ORDER BY c.executionDateTime DESC, c.closureId ASC";
		return this.queryProxy().query(sql, KrcdtMcloseLog.class).setParameter("companyId", companyId)
				.getList(c -> c.toDomain());
	}

}
