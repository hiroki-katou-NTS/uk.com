package nts.uk.ctx.at.record.infra.repository.raisesalarytime;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr.KrcdtDaiSpeDayCla;
import nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr.KrcdtDaiSpeDayClaPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class SpecificDateAttrOfDailyPerforRepoImpl extends JpaRepository implements SpecificDateAttrOfDailyPerforRepo {

	private static final String REMOVE_BY_EMPLOYEEID_AND_DATE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiSpeDayCla a ");
		builderString.append("WHERE a.krcdtDaiSpeDayClaPK.sid = :employeeId ");
		builderString.append("AND a.krcdtDaiSpeDayClaPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEEID_AND_DATE = builderString.toString();
	}

	@Override
	public Optional<SpecificDateAttrOfDailyPerfor> find(String employeeId, GeneralDate baseDate) {
		List<SpecificDateAttrSheet> shortTimeSheets = findEntities(employeeId, baseDate)
				.getList(c -> specificDateAttr(c));
		if (!shortTimeSheets.isEmpty()) {
			return Optional.of(new SpecificDateAttrOfDailyPerfor(employeeId, shortTimeSheets, baseDate));
		}
		return Optional.empty();
	}

	@Override
	public void update(SpecificDateAttrOfDailyPerfor domain) {
		List<KrcdtDaiSpeDayCla> entities = findEntities(domain.getEmployeeId(), domain.getYmd()).getList();
		domain.getSpecificDateAttrSheets().stream().forEach(c -> {
			KrcdtDaiSpeDayCla current = entities.stream()
					.filter(x -> x.krcdtDaiSpeDayClaPK.speDayItemNo == c.getSpecificDateItemNo().v()).findFirst()
					.orElse(null);
			if (current != null) {
				current.tobeSpeDay = c.getSpecificDateAttr().value;
			} else {
				entities.add(newEntities(domain.getEmployeeId(), domain.getYmd(), c));
			}
		});
		commandProxy().updateAll(entities);
	}

	@Override
	public void add(SpecificDateAttrOfDailyPerfor domain) {
		List<KrcdtDaiSpeDayCla> entities = domain.getSpecificDateAttrSheets().stream()
				.map(c -> newEntities(domain.getEmployeeId(), domain.getYmd(), c)).collect(Collectors.toList());
		commandProxy().insertAll(entities);
	}

	private KrcdtDaiSpeDayCla newEntities(String employeeId, GeneralDate ymd, SpecificDateAttrSheet c) {
		return new KrcdtDaiSpeDayCla(new KrcdtDaiSpeDayClaPK(employeeId, ymd, c.getSpecificDateItemNo().v()),
				c.getSpecificDateAttr().value);
	}

	private TypedQueryWrapper<KrcdtDaiSpeDayCla> findEntities(String employeeId, GeneralDate ymd) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT s FROM KrcdtDaiSpeDayCla s");
		query.append(" WHERE s.krcdtDaiSpeDayClaPK.sid = :employeeId");
		query.append(" AND s.krcdtDaiSpeDayClaPK.ymd = :ymd");
		query.append(" ORDER BY s.krcdtDaiSpeDayClaPK.speDayItemNo");
		return queryProxy().query(query.toString(), KrcdtDaiSpeDayCla.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd);
	}

	@Override
	public List<SpecificDateAttrOfDailyPerfor> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDaiSpeDayCla a ");
		query.append("WHERE a.krcdtDaiSpeDayClaPK.sid = :employeeId ");
		query.append("AND a.krcdtDaiSpeDayClaPK.ymd >= :start ");
		query.append("AND a.krcdtDaiSpeDayClaPK.ymd <= :end ");
		query.append("ORDER BY a.krcdtDaiSpeDayClaPK.ymd ");
		return queryProxy().query(query.toString(), KrcdtDaiSpeDayCla.class).setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end()).getList().stream()
				.collect(Collectors.groupingBy(c -> c.krcdtDaiSpeDayClaPK.sid + c.krcdtDaiSpeDayClaPK.ymd.toString()))
				.entrySet().stream()
				.map(c -> new SpecificDateAttrOfDailyPerfor(c.getValue().get(0).krcdtDaiSpeDayClaPK.sid,
						c.getValue().stream().map(x -> specificDateAttr(x)).collect(Collectors.toList()),
						c.getValue().get(0).krcdtDaiSpeDayClaPK.ymd))
				.collect(Collectors.toList());
	}

	@Override
	public List<SpecificDateAttrOfDailyPerfor> finds(List<String> employeeId, DatePeriod ymd) {
		List<SpecificDateAttrOfDailyPerfor> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDaiSpeDayCla a ");
		query.append("WHERE a.krcdtDaiSpeDayClaPK.sid IN :employeeId ");
		query.append("AND a.krcdtDaiSpeDayClaPK.ymd <= :end AND a.krcdtDaiSpeDayClaPK.ymd >= :start");
		TypedQueryWrapper<KrcdtDaiSpeDayCla> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiSpeDayCla.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
								.setParameter("start", ymd.start())
								.setParameter("end", ymd.end())
								.getList().stream().collect(Collectors.groupingBy(
										c -> c.krcdtDaiSpeDayClaPK.sid + c.krcdtDaiSpeDayClaPK.ymd.toString()))
								.entrySet().stream()
								.map(c -> new SpecificDateAttrOfDailyPerfor(c.getValue().get(0).krcdtDaiSpeDayClaPK.sid,
													c.getValue().stream().map(x -> specificDateAttr(x)).collect(Collectors.toList()),
													c.getValue().get(0).krcdtDaiSpeDayClaPK.ymd))
								.collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public List<SpecificDateAttrOfDailyPerfor> finds(Map<String, List<GeneralDate>> param) {
		List<SpecificDateAttrOfDailyPerfor> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDaiSpeDayCla a ");
		query.append("WHERE a.krcdtDaiSpeDayClaPK.sid IN :employeeId ");
		query.append("AND a.krcdtDaiSpeDayClaPK.ymd IN :date");
		TypedQueryWrapper<KrcdtDaiSpeDayCla> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiSpeDayCla.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
								.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
								.getList().stream()
								.filter(c -> p.get(c.krcdtDaiSpeDayClaPK.sid).contains(c.krcdtDaiSpeDayClaPK.ymd))
								.collect(Collectors.groupingBy(
										c -> c.krcdtDaiSpeDayClaPK.sid + c.krcdtDaiSpeDayClaPK.ymd.toString()))
								.entrySet().stream()
								.map(c -> new SpecificDateAttrOfDailyPerfor(c.getValue().get(0).krcdtDaiSpeDayClaPK.sid,
													c.getValue().stream().map(x -> specificDateAttr(x)).collect(Collectors.toList()),
													c.getValue().get(0).krcdtDaiSpeDayClaPK.ymd))
								.collect(Collectors.toList()));
		});
		return result;
	}

	private SpecificDateAttrSheet specificDateAttr(KrcdtDaiSpeDayCla c) {
		return new SpecificDateAttrSheet(new SpecificDateItemNo(c.krcdtDaiSpeDayClaPK.speDayItemNo),
				EnumAdaptor.valueOf(c.tobeSpeDay, SpecificDateAttr.class));
	}

	@Override
	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate baseDate) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAI_SPE_DAY_CLA Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + baseDate + "'" ;
		try {
			con.createStatement().executeUpdate(sqlQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
//		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEEID_AND_DATE).setParameter("employeeId", employeeId)
//				.setParameter("ymd", baseDate).executeUpdate();
//		this.getEntityManager().flush();
	}

}
