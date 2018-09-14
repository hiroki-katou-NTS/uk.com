package nts.uk.ctx.at.record.infra.repository.optionalitemtime;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.infra.entity.daily.anyitem.KrcdtDayAnyItemValue;
import nts.uk.ctx.at.record.infra.entity.daily.anyitem.KrcdtDayAnyItemValuePK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnyItemValueOfDailyRepoImpl extends JpaRepository implements AnyItemValueOfDailyRepo {
	
	private static final String REMOVE_BY_EMPLOYEE;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDayAnyItemValue a ");
		builderString.append("WHERE a.krcdtDayAnyItemValuePK.employeeID = :employeeId ");
		builderString.append("AND a.krcdtDayAnyItemValuePK.generalDate = :processingDate ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
	}

	@Override
	public Optional<AnyItemValueOfDaily> find(String employeeId, GeneralDate baseDate) {
		List<KrcdtDayAnyItemValue> result = findP(employeeId, baseDate);
		if(!result.isEmpty()) {
			return Optional.of(new AnyItemValueOfDaily(result.get(0).krcdtDayAnyItemValuePK.employeeID, result.get(0).krcdtDayAnyItemValuePK.generalDate,
					result.stream().map(e -> e.toDomain()).collect(Collectors.toList())));
		}
		return Optional.empty();
	}

	@Override
	public List<AnyItemValueOfDaily> find(String employeeId, List<GeneralDate> baseDate) {
		if(baseDate.isEmpty()) {
			return new ArrayList<>();
		}
		StringBuilder query = new StringBuilder("SELECT op FROM KrcdtDayAnyItemValue op");
		query.append(" WHERE op.krcdtDayAnyItemValuePK.employeeID = :empId");
		query.append(" AND op.krcdtDayAnyItemValuePK.generalDate IN :date");
		List<KrcdtDayAnyItemValue> result = queryProxy().query(query.toString(), KrcdtDayAnyItemValue.class)
										.setParameter("empId", employeeId)
										.setParameter("date", baseDate).getList();
		if(!result.isEmpty()) {
			return result.stream().collect(
					Collectors.groupingBy(e -> e.krcdtDayAnyItemValuePK.employeeID + e.krcdtDayAnyItemValuePK.generalDate.toString(),
					Collectors.toList())).entrySet().stream()
						.map(l -> new AnyItemValueOfDaily(l.getValue().get(0).krcdtDayAnyItemValuePK.employeeID, 
															l.getValue().get(0).krcdtDayAnyItemValuePK.generalDate,
															l.getValue().stream().map(el -> el.toDomain()).collect(Collectors.toList())))
						.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<AnyItemValueOfDaily> find(String employeeId) {
		StringBuilder query = new StringBuilder("SELECT op FROM KrcdtDayAnyItemValue op");
		query.append(" WHERE op.krcdtDayAnyItemValuePK.employeeID = :empId");
		List<KrcdtDayAnyItemValue> result = queryProxy().query(query.toString(), KrcdtDayAnyItemValue.class)
										.setParameter("empId", employeeId).getList();
		if(!result.isEmpty()) {
			return result.stream().collect(
					Collectors.groupingBy(e -> e.krcdtDayAnyItemValuePK.employeeID + e.krcdtDayAnyItemValuePK.generalDate.toString(),
					Collectors.toList())).entrySet().stream()
						.map(l -> new AnyItemValueOfDaily(l.getValue().get(0).krcdtDayAnyItemValuePK.employeeID, 
															l.getValue().get(0).krcdtDayAnyItemValuePK.generalDate,
															l.getValue().stream().map(el -> el.toDomain()).collect(Collectors.toList())))
						.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<AnyItemValueOfDaily> finds(List<String> employeeId, DatePeriod baseDate) {
		List<AnyItemValueOfDaily> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT op FROM KrcdtDayAnyItemValue op");
		query.append(" WHERE op.krcdtDayAnyItemValuePK.employeeID IN :empId");
		query.append(" AND op.krcdtDayAnyItemValuePK.generalDate >= :start");
		query.append(" AND op.krcdtDayAnyItemValuePK.generalDate <= :end");
		TypedQueryWrapper<KrcdtDayAnyItemValue> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayAnyItemValue.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			List<KrcdtDayAnyItemValue> r = tQuery.setParameter("empId", empIds)
													.setParameter("start", baseDate.start())
													.setParameter("end", baseDate.end()).getList();
			if(!r.isEmpty()) {
				result.addAll(r.stream().collect(
						Collectors.groupingBy(e -> e.krcdtDayAnyItemValuePK.employeeID + e.krcdtDayAnyItemValuePK.generalDate.toString(),
						Collectors.toList())).entrySet().stream()
							.map(l -> new AnyItemValueOfDaily(l.getValue().get(0).krcdtDayAnyItemValuePK.employeeID, 
																l.getValue().get(0).krcdtDayAnyItemValuePK.generalDate,
																l.getValue().stream().map(el -> el.toDomain()).collect(Collectors.toList())))
							.collect(Collectors.toList()));
			}
		});
		
		return result;
	}

	@Override
	public List<AnyItemValueOfDaily> finds(Map<String, List<GeneralDate>> param) {
		List<AnyItemValueOfDaily> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT op FROM KrcdtDayAnyItemValue op");
		query.append(" WHERE op.krcdtDayAnyItemValuePK.employeeID IN :employeeId");
		query.append(" AND op.krcdtDayAnyItemValuePK.generalDate IN :date");
		TypedQueryWrapper<KrcdtDayAnyItemValue> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayAnyItemValue.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
								.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
								.getList().stream()
								.filter(c -> p.get(c.krcdtDayAnyItemValuePK.employeeID).contains(c.krcdtDayAnyItemValuePK.generalDate))
								.collect(Collectors.groupingBy(
										c -> c.krcdtDayAnyItemValuePK.employeeID + c.krcdtDayAnyItemValuePK.generalDate.toString()))
								.entrySet().stream()
								.map(l -> new AnyItemValueOfDaily(l.getValue().get(0).krcdtDayAnyItemValuePK.employeeID, 
										l.getValue().get(0).krcdtDayAnyItemValuePK.generalDate,
										l.getValue().stream().map(el -> el.toDomain()).collect(Collectors.toList())))
								.collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public void update(AnyItemValueOfDaily domain) {
		
		remove(domain);

		domain.getItems().stream().forEach(c -> {
			commandProxy().insert(KrcdtDayAnyItemValue.create(domain.getEmployeeId(), domain.getYmd(), c));
		});
	}

	@Override
	public void add(AnyItemValueOfDaily domain) {
		List<KrcdtDayAnyItemValue> entity = KrcdtDayAnyItemValue.create(domain);
		commandProxy().insertAll(entity);
	}

	@Override
	public void remove(AnyItemValueOfDaily domain) {
		this.removeWithJdbc(domain.getEmployeeId(), domain.getYmd());
	}
	
	@SneakyThrows
	private void removeWithJdbc(String employeeId, GeneralDate baseDate) {
		val statement = this.connection().prepareStatement(
				"DELETE FROM KRCDT_DAY_ANYITEMVALUE"
				+ " WHERE SID = ? AND YMD = ?");
		statement.setString(1, employeeId);
		statement.setDate(2, Date.valueOf(baseDate.localDate()));
		statement.executeUpdate();
	}
	
	@SneakyThrows
	private void removeWithJdbc(String employeeId, GeneralDate baseDate, List<Integer> itemNos) {
		val statement = this.connection().prepareStatement(
				"DELETE FROM KRCDT_DAY_ANYITEMVALUE"
				+ " WHERE SID = ? AND YMD = ?"
				+ " AND ITEM_NO IN (" + itemNos.stream().map(n -> "?").collect(Collectors.joining(",")) + ")");
		statement.setString(1, employeeId);
		statement.setDate(2, Date.valueOf(baseDate.localDate()));
		for (int i = 0; i < itemNos.size(); i++) {
			statement.setInt(3 + i, itemNos.get(i));
		}
		
		statement.executeUpdate();
	}

	@SneakyThrows
	private List<KrcdtDayAnyItemValue> findP(String employeeId, GeneralDate baseDate) {
		val statement = this.connection().prepareStatement("SELECT * FROM KRCDT_DAY_ANYITEMVALUE WHERE SID = ? AND YMD = ?");
		statement.setString(1, employeeId);
		statement.setDate(2, Date.valueOf(baseDate.localDate()));
		val result = new NtsResultSet(statement.executeQuery());
		return result.getList(rec -> {
			val entity = new KrcdtDayAnyItemValue();
			entity.krcdtDayAnyItemValuePK = new KrcdtDayAnyItemValuePK();
			entity.krcdtDayAnyItemValuePK.employeeID = rec.getString("SID");
			entity.krcdtDayAnyItemValuePK.generalDate = rec.getGeneralDate("YMD");
			entity.krcdtDayAnyItemValuePK.itemNo = rec.getInt("ITEM_NO");
			entity.countValue = rec.getBigDecimal("COUNT_VALUE");
			entity.moneyValue = rec.getInt("MONEY_VALUE");
			entity.timeValue = rec.getInt("TIME_VALUE");
			return entity;
		});

//		StringBuilder query = new StringBuilder("SELECT op FROM KrcdtDayAnyItemValue op");
//		query.append(" WHERE op.krcdtDayAnyItemValuePK.employeeID = :empId");
//		query.append(" AND op.krcdtDayAnyItemValuePK.generalDate = :date");
//		List<KrcdtDayAnyItemValue> result = queryProxy().query(query.toString(), KrcdtDayAnyItemValue.class)
//										.setParameter("empId", employeeId)
//										.setParameter("date", baseDate).getList();
//		return result;
	}

	@Override
	public void removeByEmployeeIdAndDate(String employeeId, GeneralDate processingDate) {
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
		.setParameter("processingDate", processingDate).executeUpdate();
		this.getEntityManager().flush();
	}
}
