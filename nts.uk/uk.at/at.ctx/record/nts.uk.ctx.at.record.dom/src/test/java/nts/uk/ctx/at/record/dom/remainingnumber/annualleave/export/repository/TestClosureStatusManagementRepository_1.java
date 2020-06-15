package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 締め状態管理
 * @author masaaki_jinno
 *
 */
public class TestClosureStatusManagementRepository_1 extends JpaRepository implements ClosureStatusManagementRepository {

	@Override
	public void add(ClosureStatusManagement domain) {
//		this.commandProxy().insert(KrcdtClosureSttMng.fromDomain(domain));
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);

	}

	@Override
	public Optional<ClosureStatusManagement> getById(String employeeId, YearMonth ym, int closureId,
			ClosureDate closureDate) {
//		Optional<KrcdtClosureSttMng> opt = this.queryProxy().find(new KrcdtClosureSttMngPk(ym.v(), employeeId,
//				closureId, closureDate.getClosureDay().v(), closureDate.getLastDayOfMonth() ? 1 : 0),
//				KrcdtClosureSttMng.class);
//		if (opt.isPresent())
//			return Optional.of(opt.get().toDomain());
//		return Optional.empty();
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return Optional.empty();
	}

	@Override
	public Optional<ClosureStatusManagement> getLatestByEmpId(String employeeId) {
//		String sql = "SELECT a FROM KrcdtClosureSttMng a WHERE a.pk.employeeId = :employeeId ORDER BY a.end DESC";
//		List<KrcdtClosureSttMng> lstEntity = this.queryProxy().query(sql, KrcdtClosureSttMng.class)
//				.setParameter("employeeId", employeeId).getList();
//		if (lstEntity.isEmpty())
//			return Optional.empty();
//		return Optional.of(lstEntity.get(0).toDomain());
		System.out.print("要実装→OK.今回は実装なしでテストを行う");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return Optional.empty();
	}
	
	@Override
	public List<ClosureStatusManagement> getByIdListAndDatePeriod(List<String> employeeIds, DatePeriod span){
		
//		List<KrcdtClosureSttMng> result = new ArrayList<>();
//		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtClosureSttMng a ");
//		query.append("WHERE a.pk.employeeId IN :employeeId ");
//		query.append("AND a.start <= :endDate ");
//		query.append("AND a.end >= :startDate ");
//		TypedQueryWrapper<KrcdtClosureSttMng> tQuery=  this.queryProxy().query(query.toString(), KrcdtClosureSttMng.class);
//		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
//			result.addAll(tQuery
//					.setParameter("employeeId", empIds)
//					.setParameter("startDate", span.start())
//					.setParameter("endDate", span.end())
//					.getList());
//		});
//		return toDomainFromJoin(result);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

//	private List<ClosureStatusManagement> toDomainFromJoin(List<KrcdtClosureSttMng> result) {
//		return result.stream().map(tc -> tc.toDomain()).collect(Collectors.toList());		
//	}

	@Override
	public Map<String, ClosureStatusManagement> getLatestBySids(List<String> sids) {
//		Map<String, ClosureStatusManagement> result = new HashMap<>();
//		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtClosureSttMng a ");
//		query.append("WHERE a.pk.employeeId IN :employeeId ");
//		TypedQueryWrapper<KrcdtClosureSttMng> tQuery=  this.queryProxy().query(query.toString(), KrcdtClosureSttMng.class);
//		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
//			List<KrcdtClosureSttMng> closureEntityLst = tQuery
//					.setParameter("employeeId", sids)
//					.getList();
//			Map<String,List<ClosureStatusManagement>>  closureStatus = toDomainFromJoin(closureEntityLst).stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
//			closureStatus.entrySet().forEach(c ->{
//				ClosureStatusManagement closeStatusLast = c.getValue().stream().sorted((d1, d2) -> d2.getPeriod().end().compareTo(d1.getPeriod().end())).collect(Collectors.toList()).get(0);
//				result.put(c.getKey(), closeStatusLast);
//			});
//		});
//		return result;
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}

}