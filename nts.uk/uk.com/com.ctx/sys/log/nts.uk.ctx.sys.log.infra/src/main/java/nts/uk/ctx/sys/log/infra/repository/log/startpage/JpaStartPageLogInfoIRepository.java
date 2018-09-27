package nts.uk.ctx.sys.log.infra.repository.log.startpage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.log.infra.entity.log.startpage.SrcdtStartPageLogInfo;
import nts.uk.shr.com.security.audittrail.start.StartPageLog;
import nts.uk.shr.com.security.audittrail.start.StartPageLogRepository;
import nts.uk.shr.com.security.audittrail.start.StartPageLogStorageRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaStartPageLogInfoIRepository extends JpaRepository
		implements StartPageLogStorageRepository, StartPageLogRepository {

	@Override
	public Optional<StartPageLog> find(String operationId) {
		return this.queryProxy().find(operationId, SrcdtStartPageLogInfo.class).map(e -> e.toDomain());
	}

	@Override
	public List<StartPageLog> find(List<String> operationId) {
		List<StartPageLog> res = new ArrayList<>();
		
		CollectionUtil.split(operationId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, sub -> {
			res.addAll(this.queryProxy().query("SELECT l FROM SrcdtStartPageLogInfo l WHERE l.operationId IN :operationId",
											SrcdtStartPageLogInfo.class)
									.setParameter("operationId", sub).getList(r -> r.toDomain()));
		});
		
		return res;
	}

	@Override
	public List<StartPageLog> finds(String companyId) {
		return this.queryProxy()
				.query("SELECT l FROM SrcdtStartPageLogInfo l WHERE l.companyId = :cid", SrcdtStartPageLogInfo.class)
				.setParameter("cid", companyId).getList(r -> r.toDomain());
	}

	@Override
	public List<StartPageLog> finds(GeneralDate targetDate) {
		int year = targetDate.year();
		int month = targetDate.month();
		int dayOfMonth = targetDate.day();
		GeneralDateTime startDate = GeneralDateTime.ymdhms(year, month, dayOfMonth, 0, 0, 0);
		GeneralDateTime endDate = GeneralDateTime.ymdhms(year, month, dayOfMonth, 23, 59, 59);
		String sql = "SELECT l FROM SrcdtStartPageLogInfo l WHERE l.startDateTime >= :startDate AND l.startDateTime <= :endDate";
		return this.queryProxy().query(sql, SrcdtStartPageLogInfo.class)
									.setParameter("startDate", startDate)
									.setParameter("endDate", endDate)
									.getList(r -> r.toDomain());
	}

	@Override
	public List<StartPageLog> finds(DatePeriod targetDate) {
		GeneralDateTime startDate = GeneralDateTime.ymdhms(targetDate.start().year(), targetDate.start().month(), targetDate.start().day(), 0, 0, 0);
		GeneralDateTime endDate = GeneralDateTime.ymdhms(targetDate.end().year(), targetDate.end().month(), targetDate.end().day(), 23, 59, 59);
		String sql = "SELECT l FROM SrcdtStartPageLogInfo l WHERE l.startDateTime >= :startDate AND l.startDateTime <= :endDate";
		return this.queryProxy().query(sql, SrcdtStartPageLogInfo.class)
									.setParameter("startDate", startDate)
									.setParameter("endDate", endDate)
									.getList(r -> r.toDomain());
	}

	@Override
	public List<StartPageLog> findBySid(String sId) {
		return this.queryProxy()
				.query("SELECT l FROM SrcdtStartPageLogInfo l WHERE l.employeeId = :sId", SrcdtStartPageLogInfo.class)
				.setParameter("sId", sId).getList(r -> r.toDomain());
	}

	@Override
	public List<StartPageLog> findBySid(List<String> sIds) {
		List<StartPageLog> res = new ArrayList<>();
		
		CollectionUtil.split(sIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, sub -> {
			res.addAll(this.queryProxy().query("SELECT l FROM SrcdtStartPageLogInfo l WHERE l.employeeId IN :employeeIds",
											SrcdtStartPageLogInfo.class)
									.setParameter("employeeIds", sub).getList(r -> r.toDomain()));
		});
		
		return res;
	}

	@Override
	public List<StartPageLog> findBy(String companyId, List<String> listEmployeeId,
			GeneralDateTime start, GeneralDateTime end) {
		if(CollectionUtil.isEmpty(listEmployeeId)){
			return findBy(companyId, start, end);
		}
		List<StartPageLog> res = new ArrayList<>();
		
		StringBuilder qb = new StringBuilder("SELECT l FROM SrcdtStartPageLogInfo l WHERE ");
		qb.append(" l.employeeId IN :employeeIds");
		qb.append(" AND l.companyId = :cid");
		qb.append(" AND l.startDateTime >= :start");
		qb.append(" AND l.startDateTime <= :end");
		
		CollectionUtil.split(listEmployeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, sub -> {
			res.addAll(this.queryProxy().query(qb.toString(), SrcdtStartPageLogInfo.class)
										.setParameter("cid", companyId)
										.setParameter("employeeIds", sub)
										.setParameter("start", start)
										.setParameter("end", end)
										.getList(r -> r.toDomain()));
		});
		
		return res;
	}

	@Override
	public List<StartPageLog> findBy(String companyId, GeneralDateTime start, GeneralDateTime end) {
		
		StringBuilder qb = new StringBuilder("SELECT l FROM SrcdtStartPageLogInfo l WHERE ");
		qb.append(" l.companyId = :cid");
		qb.append(" AND l.startDateTime >= :start");
		qb.append(" AND l.startDateTime <= :end");
		
		
		return this.queryProxy().query(qb.toString(), SrcdtStartPageLogInfo.class)
				.setParameter("cid", companyId)
				.setParameter("start", start)
				.setParameter("end", end)
				.getList(r -> r.toDomain());
	}

	@Override
	public void save(StartPageLog log) {
		this.commandProxy().insert(SrcdtStartPageLogInfo.from(log));
	}

	@Override
	public void delete(String operationId) {
		this.queryProxy().find(operationId, SrcdtStartPageLogInfo.class).ifPresent(e -> {
			this.commandProxy().remove(e);
		});
	}

}
