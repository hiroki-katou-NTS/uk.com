package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat.businesstype;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype.KrcmtBusinessTypeOfHistory;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype.KrcmtBusinessTypeOfHistoryPK;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpHis;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpHisAdaptor;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * implement BusinessTypeEmpOfHistoryRepository
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class JpaBusinessTypeEmpOfHistory extends JpaRepository
		implements BusinessTypeEmpOfHistoryRepository {
	private static final String FIND_BY_BASE_DATE;
	private static final String FIND_BY_EMPLOYEE;
	private static final String FIND_BY_EMPLOYEE_DESC;
//	private static final String SEL_BUSINESS_TYPE;

	private static final String FIND_BY_CID_SID_DATE_PERIOD = "SELECT NEW " + BusinessTypeOfEmpDto.class.getName()
			+ " (a.cID, a.sId, a.krcmtBusinessTypeOfHistoryPK.historyId, a.startDate, a.endDate, b.businessTypeCode)"
			+ " FROM KrcmtBusinessTypeOfHistory a JOIN KrcmtBusinessTypeOfEmployee b"
			+ " ON a.krcmtBusinessTypeOfHistoryPK.historyId = b.krcmtBusinessTypeOfEmployeePK.historyId"
			+ " WHERE a.sId IN :sIds" + " AND a.cID = :cId"
			+ " AND a.startDate <= :endDate and a.endDate >= :startDate";

	static {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT k ");
		stringBuilder.append("FROM KrcmtBusinessTypeOfHistory k ");
		stringBuilder.append("WHERE k.sId = :sId ");
		stringBuilder.append("AND k.startDate <= :baseDate and k.endDate >= :baseDate");
		FIND_BY_BASE_DATE = stringBuilder.toString();
		
//		StringBuilder builderString = new StringBuilder();
//		builderString.append("SELECT h");
//		builderString.append(" FROM KrcmtBusinessTypeOfEmployee b");
//		builderString.append(" JOIN KrcmtBusinessTypeOfHistory h");
//		builderString
//				.append(" ON b.krcmtBusinessTypeOfEmployeePK.historyId = h.krcmtBusinessTypeOfHistoryPK.historyId");
//		builderString.append(" WHERE b.sId IN :lstSID");
//		builderString.append(" AND h.startDate <= :endYmd");
//		builderString.append(" AND h.endDate >= :startYmd");
//		SEL_BUSINESS_TYPE = stringBuilder.toString();

		stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT k ");
		stringBuilder.append("FROM KrcmtBusinessTypeOfHistory k ");
		stringBuilder.append("WHERE k.sId = :sId ");
		stringBuilder.append("AND k.cID = :cId ");
		stringBuilder.append("ORDER BY k.startDate");
		FIND_BY_EMPLOYEE = stringBuilder.toString();

		stringBuilder.append(" DESC");
		FIND_BY_EMPLOYEE_DESC = stringBuilder.toString();
	}

	private static KrcmtBusinessTypeOfHistory toEntity(String companyId, String employeeId, String historyId,
			GeneralDate startDate, GeneralDate endDate) {

		KrcmtBusinessTypeOfHistory entity = new KrcmtBusinessTypeOfHistory();
		KrcmtBusinessTypeOfHistoryPK pk = new KrcmtBusinessTypeOfHistoryPK(historyId);
		entity.krcmtBusinessTypeOfHistoryPK = pk;
		entity.startDate = startDate;
		entity.endDate = endDate;
		entity.cID = companyId;
		entity.sId = employeeId;
		return entity;

	}

	private static BusinessTypeOfEmployeeHistory toDomain(List<KrcmtBusinessTypeOfHistory> entities) {
		String companyId = entities.get(0).cID;
		String employeeId = entities.get(0).sId;
		List<DateHistoryItem> histories = entities.stream().map(entity -> {
			DateHistoryItem history = new DateHistoryItem(entity.krcmtBusinessTypeOfHistoryPK.historyId,
					new DatePeriod(entity.startDate, entity.endDate));
			return history;
		}).collect(Collectors.toList());
		return new BusinessTypeOfEmployeeHistory(companyId, histories, employeeId);
	}

	@Override
	public Optional<BusinessTypeOfEmployeeHistory> findByBaseDate(GeneralDate baseDate, String sId) {
		List<KrcmtBusinessTypeOfHistory> entities = this.queryProxy()
				.query(FIND_BY_BASE_DATE, KrcmtBusinessTypeOfHistory.class).setParameter("sId", sId)
				.setParameter("baseDate", baseDate).getList();
		if (entities == null || entities.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entities));
		}

	}

	@Override
	public Optional<BusinessTypeOfEmployeeHistory> findByEmployee(String cid, String sId) {
		List<KrcmtBusinessTypeOfHistory> entities = this.queryProxy()
				.query(FIND_BY_EMPLOYEE, KrcmtBusinessTypeOfHistory.class).setParameter("sId", sId)
				.setParameter("cId", cid).getList();
		if (entities == null || entities.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entities));
		}
	}

	@Override
	public Optional<BusinessTypeOfEmployeeHistory> findByEmployeeDesc(String cid, String sId) {
		List<KrcmtBusinessTypeOfHistory> entities = this.queryProxy()
				.query(FIND_BY_EMPLOYEE_DESC, KrcmtBusinessTypeOfHistory.class).setParameter("sId", sId)
				.setParameter("cId", cid).getList();
		if (entities == null || entities.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entities));
		}
	}

	@Override
	public void add(String companyId, String employeeId, String historyId, GeneralDate startDate, GeneralDate endDate) {
		this.commandProxy().insert(toEntity(companyId, employeeId, historyId, startDate, endDate));
	}

	@Override
	public void update(String companyId, String employeeId, String historyId, GeneralDate startDate,
			GeneralDate endDate) {
		Optional<KrcmtBusinessTypeOfHistory> optional = this.queryProxy()
				.find(new KrcmtBusinessTypeOfHistoryPK(historyId), KrcmtBusinessTypeOfHistory.class);
		if (optional.isPresent()) {
			KrcmtBusinessTypeOfHistory entity = optional.get();
			entity.startDate = startDate;
			entity.endDate = endDate;
			this.commandProxy().update(entity);
		}

	}

	@Override
	public void delete(String historyId) {
		this.commandProxy().remove(KrcmtBusinessTypeOfHistory.class, new KrcmtBusinessTypeOfHistoryPK(historyId));
	}

	@Override
	public Optional<BusinessTypeOfEmployeeHistory> findByHistoryId(String historyId) {
		Optional<KrcmtBusinessTypeOfHistory> entity = this.queryProxy()
				.find(new KrcmtBusinessTypeOfHistoryPK(historyId), KrcmtBusinessTypeOfHistory.class);
		if (entity.isPresent()) {
			return Optional.of(toDomain(new ArrayList<KrcmtBusinessTypeOfHistory>() {
				private static final long serialVersionUID = 1L;

				{
					add(entity.get());
				}
			}));
		}
		return Optional.empty();
	}

//	@Override
//	public Optional<BusinessTypeOfEmpHis> findByBaseDateAndSid(GeneralDate baseDate, String sId) {
//		return this.findByBaseDate(baseDate, sId).map(x -> new BusinessTypeOfEmpHis(x.getCompanyId(), x.getEmployeeId(),
//				x.getHistory().get(0).identifier(), x.getHistory().get(0).span()));
//	}
//
//	@Override
//	public List<BusinessTypeOfEmpDto> findByCidSidBaseDate(String cid, List<String> sIds, DatePeriod datePeriod) {
//		
//		// ResultList
//		List<BusinessTypeOfEmpDto> resultList = new ArrayList<>();
//		// Split employeeId List if size of employeeId List is greater than 1000
//		CollectionUtil.split(sIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
//			List<BusinessTypeOfEmpDto> entities = this.queryProxy()
//					.query(FIND_BY_CID_SID_DATE_PERIOD, BusinessTypeOfEmpDto.class).setParameter("sIds", subList)
//					.setParameter("cId", cid).setParameter("startDate", datePeriod.start())
//					.setParameter("endDate", datePeriod.end())
//					.getList();
//			resultList.addAll(entities);
//		});
//		
//		return resultList;
//	}

	@Override
	@SneakyThrows
	public List<DateHistoryItem> getDateHistItemByCidAndSidsAndBaseDate(String cid, List<String> sIds,
			GeneralDate baseDate) {
		List<DateHistoryItem> result= new ArrayList<>();
		CollectionUtil.split(sIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			String sql = "SELECT * FROM KRCMT_BUS_TYPE_HIST WHERE CID = ? AND START_DATE <= ? AND END_DATE >= ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
					
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);
				stmt.setDate(2, Date.valueOf(baseDate.localDate()));
				stmt.setDate(3, Date.valueOf(baseDate.localDate()));
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(4 + i, subList.get(i));
				}
				
				List<DateHistoryItem> lstObj = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return new DateHistoryItem(rec.getString("HIST_ID"),  new DatePeriod(rec.getGeneralDate("START_DATE"),  rec.getGeneralDate("END_DATE")));
				});
				result.addAll(lstObj);
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	@SneakyThrows
	public List<BusinessTypeOfEmployeeHistory> findByEmployeeDesc(String cid, List<String> sids) {
		List<BusinessTypeOfEmployeeHistory> result = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			String sql = "SELECT * FROM KRCMT_BUS_TYPE_HIST WHERE CID = ? AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")" + " ORDER BY SID, START_DATE DESC";

			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}

				List<Map<String, Object>> map = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					Map<String, Object> m = new HashMap<>();
					m.put("HIST_ID", rec.getString("HIST_ID"));
					m.put("CID", rec.getString("CID"));
					m.put("SID", rec.getString("SID"));
					m.put("START_DATE", rec.getGeneralDate("START_DATE"));
					m.put("END_DATE", rec.getGeneralDate("END_DATE"));
					return m;
				});

				map.stream().collect(Collectors.groupingBy(c -> c.get("SID"),
						Collectors.collectingAndThen(Collectors.toList(), list -> {
							BusinessTypeOfEmployeeHistory his = new BusinessTypeOfEmployeeHistory(
									list.get(0).get("CID").toString(), list.stream().map(c -> {
										return new DateHistoryItem(c.get("HIST_ID").toString(), new DatePeriod(
												(GeneralDate) c.get("START_DATE"), (GeneralDate) c.get("END_DATE")));
									}).collect(Collectors.toList()), list.get(0).get("SID").toString());
							result.add(his);
							return his;
						})));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}
	
	@Override
	public List<DateHistoryItem> getListByListSidsNoWithPeriod(String cid, List<String> sids) {
		List<DateHistoryItem> result = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			String sql = "SELECT * FROM KRCMT_BUS_TYPE_HIST WHERE CID = ? AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")" + " ORDER BY START_DATE ASC";

			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}

				List<DateHistoryItem> lstObj = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return new DateHistoryItem(rec.getString("HIST_ID"),
							new DatePeriod(rec.getGeneralDate("START_DATE"), rec.getGeneralDate("END_DATE")));
				}).stream().collect(Collectors.toList());
				result.addAll(lstObj);
			
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	@Override
	public void addAll(Map<String, DateHistoryItem> dateHistItems) {
		String cid = AppContexts.user().companyId();
		String INS_SQL = "INSERT INTO KRCMT_BUS_TYPE_HIST (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " HIST_ID, CID, SID,"
				+ " START_DATE, END_DATE)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " HIST_ID_VAL, CID_VAL, SID_VAL, START_DATE_VAL, END_DATE_VAL); ";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();

		dateHistItems.entrySet().stream().forEach(c ->{
			String sql = INS_SQL;
			DateHistoryItem dateHistItem = c.getValue();
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
			
			sql = sql.replace("HIST_ID_VAL", "'" + dateHistItem.identifier() + "'");
			sql = sql.replace("CID_VAL", "'" + cid + "'");
			sql = sql.replace("SID_VAL", "'" + c.getKey() + "'");
			sql = sql.replace("START_DATE_VAL", "'" + dateHistItem.start() + "'");
			sql = sql.replace("END_DATE_VAL","'" +  dateHistItem.end() + "'");
			
			sb.append(sql);
		});
		
		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(Map<String, DateHistoryItem> dateHistItems) {
		String UP_SQL = "UPDATE KRCMT_BUS_TYPE_HIST SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
				+ " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL AND SID = SID_VAL;";
		String cid = AppContexts.user().companyId();
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg = AppContexts.programId();
		
		StringBuilder sb = new StringBuilder();

		dateHistItems.entrySet().stream().forEach(c ->{
			DateHistoryItem dateHistItem = c.getValue();
			String sql = UP_SQL;
			sql = UP_SQL.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("START_DATE_VAL", "'" + dateHistItem.start() + "'");
			sql = sql.replace("END_DATE_VAL","'" +  dateHistItem.end() + "'");
			
			sql = sql.replace("HIST_ID_VAL", "'" + dateHistItem.identifier() +"'");
			sql = sql.replace("CID_VAL", "'" + cid +"'");
			sql = sql.replace("SID_VAL", "'" + c.getKey() +"'");
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public List<BusinessTypeOfEmployeeHistory> findByBaseDate(GeneralDate baseDate, List<String> sIds) {
		List<BusinessTypeOfEmployeeHistory> result= new ArrayList<>();
		CollectionUtil.split(sIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			String sql = "SELECT * FROM KRCMT_BUS_TYPE_HIST WHERE CID = ? AND START_DATE <= ? AND END_DATE >= ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
					
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, AppContexts.user().companyId());
				stmt.setDate(2, Date.valueOf(baseDate.localDate()));
				stmt.setDate(3, Date.valueOf(baseDate.localDate()));
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(4 + i, subList.get(i));
				}
				List<Map<String, Object>> map = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					Map<String, Object> m = new HashMap<>();
					m.put("HIST_ID", rec.getString("HIST_ID"));
					m.put("CID", rec.getString("CID"));
					m.put("SID", rec.getString("SID"));
					m.put("START_DATE", rec.getGeneralDate("START_DATE"));
					m.put("END_DATE", rec.getGeneralDate("END_DATE"));
					return m;
				});

				map.stream().collect(Collectors.groupingBy(c -> c.get("SID"),
						Collectors.collectingAndThen(Collectors.toList(), list -> {
							BusinessTypeOfEmployeeHistory his = new BusinessTypeOfEmployeeHistory(
									list.get(0).get("CID").toString(), list.stream().map(c -> {
										return new DateHistoryItem(c.get("HIST_ID").toString(), new DatePeriod(
												(GeneralDate) c.get("START_DATE"), (GeneralDate) c.get("END_DATE")));
									}).collect(Collectors.toList()), list.get(0).get("SID").toString());
							result.add(his);
							return his;
						})));
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

//	@Override
//	public List<BusinessTypeOfEmployeeHistory> getListBusinessType(List<String> lstEmployee, Period dateRange) {
//		List<BusinessTypeOfEmployeeHistory> data = this.queryProxy().query(SEL_BUSINESS_TYPE,KrcmtBusinessTypeOfHistory.class)
//				.set
//				List<String> businessTypes = new ArrayList<>();
//				CollectionUtil.split(lstEmployee, 1000, subList -> {
//					businessTypes.addAll(this.queryProxy().query(SEL_BUSINESS_TYPE, String.class)
//							.setParameter("lstSID", subList).setParameter("startYmd", dateRange.getStartDate())
//							.setParameter("endYmd", dateRange.getEndDate()).getList());
//				});
//				return businessTypes;
//		return null;
//	}
}
