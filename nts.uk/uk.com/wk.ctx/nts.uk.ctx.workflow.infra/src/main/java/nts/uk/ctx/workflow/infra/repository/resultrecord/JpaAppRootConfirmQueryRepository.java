package nts.uk.ctx.workflow.infra.repository.resultrecord;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.collection.ListHashMap;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirmQueryRepository;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootIntermForQuery;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootRecordConfirmForQuery;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAppRootConfirmQueryRepository
		extends JpaRepository
		implements AppRootConfirmQueryRepository {

	@Override
	public AppRootIntermForQuery.List queryInterm(
			String companyId,
			List<String> employeeIds,
			DatePeriod period,
			RecordRootType rootType) {
		
		List<AppRootIntermForQuery> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subEmpIds -> {
			//long startTime = System.currentTimeMillis();
			String sql = "select r.ROOT_ID, r.EMPLOYEE_ID, r.START_DATE, r.END_DATE, MAX(p.PHASE_ORDER) as FINAL_PHASE_ORDER" 
					+ " from WWFDT_INST_ROUTE r"
					+ " inner join WWFDT_INST_PHASE p"
					+ " on r.ROOT_ID = p.ROOT_ID"
					+ " where r.EMPLOYEE_ID in (" + NtsStatement.In.createParamsString(subEmpIds) + ")"
					+ " and r.START_DATE <= ?"
					+ " and r.END_DATE >= ?"
					+ " and r.CID = ?"
					+ " and r.ROOT_TYPE = ?"
					+ " group by r.ROOT_ID, r.EMPLOYEE_ID, r.START_DATE, r.END_DATE";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				for (int i = 0; i < subEmpIds.size(); i++) {
					stmt.setString(1 + i, subEmpIds.get(i));
				}
				
				stmt.setDate(1 + subEmpIds.size(), Date.valueOf(period.end().localDate()));
				stmt.setDate(2 + subEmpIds.size(), Date.valueOf(period.start().localDate()));
				stmt.setString(3 + subEmpIds.size(), companyId);
				stmt.setInt(4 + subEmpIds.size(), rootType.value);
				
				List<AppRootIntermForQuery> subResults = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					
					return new AppRootIntermForQuery(
							rec.getString("ROOT_ID"),
							rec.getString("EMPLOYEE_ID"),
							new DatePeriod(
									rec.getGeneralDate("START_DATE"),
									rec.getGeneralDate("END_DATE")),
							rec.getInt("FINAL_PHASE_ORDER"));
				});
				
				results.addAll(subResults);
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			//System.out.print("\n thoi gian queryInterm:  " +(System.currentTimeMillis() - startTime) + "\n");
		});
	
		return new AppRootIntermForQuery.List(results);
	}

	@Override
	public AppRootRecordConfirmForQuery.List queryConfirm(
			String companyId,
			List<String> employeeIds,
			DatePeriod period,
			RecordRootType rootType) {

		List<AppRootRecordConfirmForQuery> results = new ArrayList<>();
        //long startTime = System.currentTimeMillis();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subEmpIds -> {
			
			String sql = "select r.ROOT_ID, r.EMPLOYEE_ID, r.RECORD_DATE, p.PHASE_ORDER, p.APP_PHASE_ATR"
					+ " from WWFDT_CONF_ROUTE r"
					+ " left join WWFDT_CONF_PHASE p"
					+ " on r.ROOT_ID = p.ROOT_ID"
					+ " where r.EMPLOYEE_ID in (" + NtsStatement.In.createParamsString(subEmpIds) + ")"
					+ " and r.RECORD_DATE between ? and ?"
					+ " and r.CID = ?"
					+ " and r.ROOT_TYPE = ?";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				for (int i = 0; i < subEmpIds.size(); i++) {
					stmt.setString(i+1, subEmpIds.get(i));
				}
				
				stmt.setDate(subEmpIds.size() + 1, Date.valueOf(period.start().localDate()));
				stmt.setDate(subEmpIds.size() + 2, Date.valueOf(period.end().localDate()));
				
				stmt.setString(subEmpIds.size() + 3, companyId);
				stmt.setInt(subEmpIds.size() + 4, rootType.value);
				
				List<ConfirmRecord> recordList = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					
					ConfirmRecord r = new ConfirmRecord();
					r.rootId = rec.getString("ROOT_ID");
					r.sid = rec.getString("EMPLOYEE_ID");
					r.recordDate = rec.getGeneralDate("RECORD_DATE");
					r.phaseOrder = rec.getInt("PHASE_ORDER");
					r.appPhaseAtr = rec.getInt("APP_PHASE_ATR");
					
					return r;
				});
				
				ConfirmRecord.Set records = new ConfirmRecord.Set(recordList);
				results.addAll(records.aggregate());
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			//System.out.print("\n thoi gian queryConfirm:  " +(System.currentTimeMillis() - startTime) + "\n");
		});
		
		return new AppRootRecordConfirmForQuery.List(results);
	}


	private static class ConfirmRecord {
		String rootId;
		String sid;
		GeneralDate recordDate;
		Integer phaseOrder;
		Integer appPhaseAtr;
		
		static class Set {
			
			private final Map<String, ListHashMap<GeneralDate, ConfirmRecord>> map;
			
			public Set(List<ConfirmRecord> records) {
				
				this.map = new HashMap<>();
				
				for (val record : records) {
					
					if (!map.containsKey(record.sid)) {
						map.put(record.sid, new ListHashMap<>());
					}
					
					val mapForOnePerson = map.get(record.sid);
					mapForOnePerson.addElement(record.recordDate, record);
				}
			}
			
			public List<AppRootRecordConfirmForQuery> aggregate() {
				
				val confirms = new ArrayList<AppRootRecordConfirmForQuery>();
				
				for (val esPerson : map.entrySet()) {
					String employeeId = esPerson.getKey();
					val mapForOnePerson = esPerson.getValue();
					
					for (val esDate : mapForOnePerson.entrySet()) {
						GeneralDate date = esDate.getKey();
						val records = esDate.getValue();
						
						Integer finalConfirmedPhase = records.stream()
								.filter(r -> r.appPhaseAtr != null && r.appPhaseAtr == 1)
								// appPhaseAtrがnullでないなら、phaseOrderもnullではないので、nullチェック不要
								.max((a, b) -> a.phaseOrder.compareTo(b.phaseOrder))
								.map(r -> r.phaseOrder)
								.orElse(null);
						
						val confirm = new AppRootRecordConfirmForQuery(
								records.get(0).rootId, // 少なくとも1レコードは必ずある
								employeeId,
								date,
								finalConfirmedPhase != null,
								finalConfirmedPhase);
						
						confirms.add(confirm);
					}
				}
				
				return confirms;
				
			}
			
		}
	}
}
