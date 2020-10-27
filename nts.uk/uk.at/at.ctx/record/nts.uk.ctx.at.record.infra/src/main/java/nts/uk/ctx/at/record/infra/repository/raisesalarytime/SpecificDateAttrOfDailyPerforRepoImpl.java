package nts.uk.ctx.at.record.infra.repository.raisesalarytime;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr.KrcdtDayInfoSpecific;
import nts.uk.ctx.at.record.infra.entity.daily.specificdatetttr.KrcdtDayInfoSpecificPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;

@Stateless
public class SpecificDateAttrOfDailyPerforRepoImpl extends JpaRepository implements SpecificDateAttrOfDailyPerforRepo {

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
		List<KrcdtDayInfoSpecific> entities = findEntities(domain.getEmployeeId(), domain.getYmd()).getList();
		domain.getSpecificDay().getSpecificDateAttrSheets().stream().forEach(c -> {
			KrcdtDayInfoSpecific current = entities.stream()
					.filter(x -> x.krcdtDayInfoSpecificPK.speDayItemNo == c.getSpecificDateItemNo().v()).findFirst()
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
		List<KrcdtDayInfoSpecific> entities = domain.getSpecificDay().getSpecificDateAttrSheets().stream()
				.map(c -> newEntities(domain.getEmployeeId(), domain.getYmd(), c)).collect(Collectors.toList());
		commandProxy().insertAll(entities);
	}

	private KrcdtDayInfoSpecific newEntities(String employeeId, GeneralDate ymd, SpecificDateAttrSheet c) {
		return new KrcdtDayInfoSpecific(new KrcdtDayInfoSpecificPK(employeeId, ymd, c.getSpecificDateItemNo().v()),
				c.getSpecificDateAttr().value);
	}

	private TypedQueryWrapper<KrcdtDayInfoSpecific> findEntities(String employeeId, GeneralDate ymd) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT s FROM KrcdtDayInfoSpecific s");
		query.append(" WHERE s.krcdtDayInfoSpecificPK.sid = :employeeId");
		query.append(" AND s.krcdtDayInfoSpecificPK.ymd = :ymd");
		query.append(" ORDER BY s.krcdtDayInfoSpecificPK.speDayItemNo");
		return queryProxy().query(query.toString(), KrcdtDayInfoSpecific.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd);
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecificDateAttrOfDailyPerfor> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDayInfoSpecific a ");
		query.append("WHERE a.krcdtDayInfoSpecificPK.sid = :employeeId ");
		query.append("AND a.krcdtDayInfoSpecificPK.ymd >= :start ");
		query.append("AND a.krcdtDayInfoSpecificPK.ymd <= :end ");
		query.append("ORDER BY a.krcdtDayInfoSpecificPK.ymd ");
		return queryProxy().query(query.toString(), KrcdtDayInfoSpecific.class).setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end()).getList().stream()
				.collect(Collectors.groupingBy(c -> c.krcdtDayInfoSpecificPK.sid + c.krcdtDayInfoSpecificPK.ymd.toString()))
				.entrySet().stream()
				.map(c -> new SpecificDateAttrOfDailyPerfor(c.getValue().get(0).krcdtDayInfoSpecificPK.sid,
						c.getValue().stream().map(x -> specificDateAttr(x)).collect(Collectors.toList()),
						c.getValue().get(0).krcdtDayInfoSpecificPK.ymd))
				.collect(Collectors.toList());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecificDateAttrOfDailyPerfor> finds(List<String> employeeId, DatePeriod ymd) {
		List<SpecificDateAttrOfDailyPerfor> result = new ArrayList<>();
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQuery(ymd, empIds));
		});
		return result;
	}
	
	@SneakyThrows
	private List<SpecificDateAttrOfDailyPerfor> internalQuery(DatePeriod baseDate, List<String> empIds) {
		String subEmp = NtsStatement.In.createParamsString(empIds);
		StringBuilder query = new StringBuilder("SELECT SPE_DAY_ITEM_NO, YMD, SID, TOBE_SPE_DAY FROM KRCDT_DAY_INFO_SPECIFIC  ");
		query.append(" WHERE YMD <= ? AND YMD >= ? ");
		query.append(" AND SID IN (" + subEmp + ")");
		try (val stmt = this.connection().prepareStatement(query.toString())){
			stmt.setDate(1, Date.valueOf(baseDate.end().localDate()));
			stmt.setDate(2, Date.valueOf(baseDate.start().localDate()));
			for (int i = 0; i < empIds.size(); i++) {
				stmt.setString(i + 3, empIds.get(i));
			}
			return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
				Map<String, Object> r = new HashMap<>();
				r.put("SPE_DAY_ITEM_NO", rec.getInt(1));
				r.put("YMD", rec.getGeneralDate(2));
				r.put("SID", rec.getString(3));
				r.put("TOBE_SPE_DAY", rec.getInt(4));
				return r;
			}).stream().collect(Collectors.groupingBy(r -> (String) r.get("SID"), Collectors.collectingAndThen(Collectors.toList(), s -> {
				
				 Map<GeneralDate, List<SpecificDateAttrSheet>> mapped = s.stream().collect(Collectors.groupingBy(c -> (GeneralDate) c.get("YMD"), 
						 Collectors.collectingAndThen(Collectors.toList(), d -> {
					return d.stream().map(sd -> {
						return new SpecificDateAttrSheet(new SpecificDateItemNo((int) sd.get("SPE_DAY_ITEM_NO")), 
								((int) sd.get("TOBE_SPE_DAY")) == SpecificDateAttr.USE.value ? SpecificDateAttr.USE : SpecificDateAttr.NOT_USE);
					}).collect(Collectors.toList());
				})));
				 
				return mapped;
			}))).entrySet().stream().map(r -> {
				
				return r.getValue().entrySet().stream().map(c -> {
					return new SpecificDateAttrOfDailyPerfor(r.getKey(), c.getValue(), c.getKey());
				}).collect(Collectors.toList());
			}).flatMap(List::stream).collect(Collectors.toList());
		}
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @SneakyThrows
	@Override
	public List<SpecificDateAttrOfDailyPerfor> finds(Map<String, List<GeneralDate>> param) {
    	List<String> subList = param.keySet().stream().collect(Collectors.toList());
    	List<GeneralDate> subListDate = param.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
    	List<SpecificDateAttrOfDailyPerfor> result = new ArrayList<>();
		CollectionUtil.split(subList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQueryMap(subListDate, empIds));
		});
		return result;
	}
    
    @SneakyThrows
	private List<SpecificDateAttrOfDailyPerfor> internalQueryMap(List<GeneralDate> subListDate, List<String> subList) {

    	String subEmp = NtsStatement.In.createParamsString(subList);
    	String subInDate = NtsStatement.In.createParamsString(subListDate);
		StringBuilder query = new StringBuilder("SELECT SPE_DAY_ITEM_NO, YMD, SID, TOBE_SPE_DAY FROM KRCDT_DAY_INFO_SPECIFIC  ");
		query.append(" WHERE SID IN (" + subEmp + ")");
		query.append(" AND YMD IN (" + subInDate + ")");
		try (val stmt = this.connection().prepareStatement(query.toString())){
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 1, subList.get(i));
			}
			
			for (int i = 0; i < subListDate.size(); i++) {
				stmt.setDate(1 + i + subList.size(),  Date.valueOf(subListDate.get(i).localDate()));
			}
			return new NtsResultSet(stmt.executeQuery()).getList(rec -> {
				Map<String, Object> r = new HashMap<>();
				r.put("SPE_DAY_ITEM_NO", rec.getInt(1));
				r.put("YMD", rec.getGeneralDate(2));
				r.put("SID", rec.getString(3));
				r.put("TOBE_SPE_DAY", rec.getInt(4));
				return r;
			}).stream().collect(Collectors.groupingBy(r -> (String) r.get("SID"), Collectors.collectingAndThen(Collectors.toList(), s -> {
				
				 Map<GeneralDate, List<SpecificDateAttrSheet>> mapped = s.stream().collect(Collectors.groupingBy(c -> (GeneralDate) c.get("YMD"), 
						 Collectors.collectingAndThen(Collectors.toList(), d -> {
					return d.stream().map(sd -> {
						return new SpecificDateAttrSheet(new SpecificDateItemNo((int) sd.get("SPE_DAY_ITEM_NO")), 
								((int) sd.get("TOBE_SPE_DAY")) == SpecificDateAttr.USE.value ? SpecificDateAttr.USE : SpecificDateAttr.NOT_USE);
					}).collect(Collectors.toList());
				})));
				 
				return mapped;
			}))).entrySet().stream().map(r -> {
				
				return r.getValue().entrySet().stream().map(c -> {
					return new SpecificDateAttrOfDailyPerfor(r.getKey(), c.getValue(), c.getKey());
				}).collect(Collectors.toList());
			}).flatMap(List::stream).collect(Collectors.toList());
		}
    }

	private SpecificDateAttrSheet specificDateAttr(KrcdtDayInfoSpecific c) {
		return new SpecificDateAttrSheet(new SpecificDateItemNo(c.krcdtDayInfoSpecificPK.speDayItemNo),
				EnumAdaptor.valueOf(c.tobeSpeDay, SpecificDateAttr.class));
	}

	@Override
	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate baseDate) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAY_INFO_SPECIFIC Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + baseDate + "'" ;
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
