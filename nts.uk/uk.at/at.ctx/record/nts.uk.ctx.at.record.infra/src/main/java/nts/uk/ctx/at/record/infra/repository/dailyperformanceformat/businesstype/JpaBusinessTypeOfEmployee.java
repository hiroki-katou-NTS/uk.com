package nts.uk.ctx.at.record.infra.repository.dailyperformanceformat.businesstype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype.KrcmtBusinessTypeOfEmployee;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.businesstype.KrcmtBusinessTypeOfEmployeePK;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmp;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpAdaptor;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * implement BusinessTypeOfEmployeeRepository
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class JpaBusinessTypeOfEmployee extends JpaRepository
		implements BusinessTypeOfEmployeeRepository, BusinessTypeOfEmpAdaptor {
	private static final String FIND_BY_LIST_CODE;
	private static final String FIND_BY_SID_HISTID;
	private static final String SEL_BUSINESS_TYPE;
	static {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT k ");
		stringBuilder.append("FROM KrcmtBusinessTypeOfEmployee k ");
		stringBuilder.append("WHERE k.businessTypeCode IN :businessTypeCodes");
		FIND_BY_LIST_CODE = stringBuilder.toString();

		StringBuilder stringBuild = new StringBuilder();
		stringBuild.append("SELECT k ");
		stringBuild.append("FROM KrcmtBusinessTypeOfEmployee k ");
		stringBuild.append("WHERE k.sId = :employeeId ");
		stringBuild.append("AND k.krcmtBusinessTypeOfEmployeePK.historyId = :historyId");
		FIND_BY_SID_HISTID = stringBuild.toString();
		
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT b");
		builderString.append(" FROM KrcmtBusinessTypeOfEmployee b");
		builderString.append(" JOIN KrcmtBusinessTypeOfHistory h");
		builderString
				.append(" ON b.krcmtBusinessTypeOfEmployeePK.historyId = h.krcmtBusinessTypeOfHistoryPK.historyId");
		builderString.append(" WHERE b.sId IN :lstSid");
		builderString.append(" AND h.startDate <= :endYmd");
		builderString.append(" AND h.endDate >= :startYmd");
		SEL_BUSINESS_TYPE = builderString.toString();
	}

	@Override
	public List<BusinessTypeOfEmployee> findAllByListCode(List<String> businessTypeCodes) {
		List<BusinessTypeOfEmployee> resultList = new ArrayList<>();
		CollectionUtil.split(businessTypeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_LIST_CODE, KrcmtBusinessTypeOfEmployee.class)
				.setParameter("businessTypeCodes", subList)
				.getList(entity -> toDomain(entity)));
		});
		return resultList;
	}

	@Override
	public void insert(BusinessTypeOfEmployee businessTypeOfEmployee) {
		this.commandProxy().insert(toEntity(businessTypeOfEmployee));

	}

	@Override
	public void update(BusinessTypeOfEmployee businessTypeOfEmployee) {
		// Hop.NT update
		KrcmtBusinessTypeOfEmployeePK pk = new KrcmtBusinessTypeOfEmployeePK(businessTypeOfEmployee.getHistoryId());
		Optional<KrcmtBusinessTypeOfEmployee> bTypeOfEmp = this.queryProxy().find(pk,
				KrcmtBusinessTypeOfEmployee.class);

		if (bTypeOfEmp.isPresent()) {
			updateEntities(businessTypeOfEmployee, bTypeOfEmp.get());
			this.commandProxy().update(bTypeOfEmp.get());
		}
	}

	@Override
	public void delete(String historyId) {
		this.commandProxy().remove(KrcmtBusinessTypeOfEmployee.class, new KrcmtBusinessTypeOfEmployeePK(historyId));

	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 * @author hop.nt
	 */
	private void updateEntities(BusinessTypeOfEmployee domain, KrcmtBusinessTypeOfEmployee entity) {
		entity.businessTypeCode = domain.getBusinessTypeCode().v();

	}

	private static KrcmtBusinessTypeOfEmployee toEntity(BusinessTypeOfEmployee domain) {
		KrcmtBusinessTypeOfEmployeePK pk = new KrcmtBusinessTypeOfEmployeePK(domain.getHistoryId());
		return new KrcmtBusinessTypeOfEmployee(pk, domain.getSId(), domain.getBusinessTypeCode().v());
	}

	private static BusinessTypeOfEmployee toDomain(KrcmtBusinessTypeOfEmployee entity) {
		return BusinessTypeOfEmployee.createFromJavaType(entity.businessTypeCode,
				entity.krcmtBusinessTypeOfEmployeePK.historyId, entity.sId);
	}

	@Override
	public Optional<BusinessTypeOfEmployee> findByHistoryId(String historyId) {
		Optional<KrcmtBusinessTypeOfEmployee> entity = this.queryProxy()
				.find(new KrcmtBusinessTypeOfEmployeePK(historyId), KrcmtBusinessTypeOfEmployee.class);
		if (entity.isPresent()) {
			return Optional.of(toDomain(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<BusinessTypeOfEmp> getBySidAndHistId(String employeeId, String histId) {
		return this.queryProxy().query(FIND_BY_SID_HISTID, KrcmtBusinessTypeOfEmployee.class)
				.setParameter("employeeId", employeeId).setParameter("historyId", histId)
				.getSingle(x -> new BusinessTypeOfEmp(x.businessTypeCode, x.krcmtBusinessTypeOfEmployeePK.historyId,
						x.sId));
	}

	@Override
	public List<BusinessTypeOfEmployee> findAllByEmpAndDate(List<String> employeeIds, DatePeriod date) {
		List<BusinessTypeOfEmployee> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SEL_BUSINESS_TYPE, KrcmtBusinessTypeOfEmployee.class)
					.setParameter("lstSid", subList)
					.setParameter("endYmd", date.end())
					.setParameter("startYmd", date.start())
					.getList(entity -> toDomain(entity)));
		});
		return resultList;
	}

	@Override
	@SneakyThrows
	public List <BusinessTypeOfEmployee> findAllByHistIds(List <String> histIds) {
		 List < BusinessTypeOfEmployee > result = new ArrayList < > ();
		 
		 CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			 String sql = "SELECT * FROM KRCMT_BUS_TYPE_HIST_ITEM WHERE HIST_ID IN (" + NtsStatement.In.createParamsString(subList) + ")";
	
		  try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			   for (int i = 0; i < subList.size(); i++) {
			    stmt.setString(i + 1, subList.get(i));
			   }
		
			   List < BusinessTypeOfEmployee > entities = new NtsResultSet(stmt.executeQuery()).getList(rec -> {			    
			    return BusinessTypeOfEmployee.createFromJavaType(rec.getString("BUSINESS_TYPE_CD"),
			    		rec.getString("HIST_ID"), rec.getString("SID"));
			   });
		
			   result.addAll(entities);
		  } catch (SQLException e) {
		   throw new RuntimeException(e);
		  }
		 });
		 return result;
	}

	@Override
	public void addAll(List<BusinessTypeOfEmployee> domains) {
		String INS_SQL = "INSERT INTO KRCMT_BUS_TYPE_HIST_ITEM (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				+ " HIST_ID, SID, BUSINESS_TYPE_CD)"
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				+ " HIST_ID_VAL, SID_VAL, BUSINESS_TYPE_CD_VAL); ";
		String insCcd = AppContexts.user().companyCode();
		String insScd = AppContexts.user().employeeCode();
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		String updScd = insScd;
		String updPg = insPg;
		StringBuilder sb = new StringBuilder();

		domains.stream().forEach(c ->{
			String sql = INS_SQL;
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");

			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
			
			sql = sql.replace("HIST_ID_VAL", "'" + c.getHistoryId() + "'");
			sql = sql.replace("SID_VAL", "'" + c.getSId() + "'");
			sql = sql.replace("BUSINESS_TYPE_CD_VAL", "'" + c.getBusinessTypeCode().toString() + "'");
			
			sb.append(sql);
		});
		
		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

	@Override
	public void updateAll(List<BusinessTypeOfEmployee> domains) {
		String UP_SQL = "UPDATE KRCMT_BUS_TYPE_HIST_ITEM SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
				+ " BUSINESS_TYPE_CD = BUSINESS_TYPE_CD_VAL"
				+ " WHERE HIST_ID = HIST_ID_VAL AND SID = SID_VAL;";
		String updCcd = AppContexts.user().companyCode();
		String updScd = AppContexts.user().employeeCode();
		String updPg = AppContexts.programId();
		
		StringBuilder sb = new StringBuilder();

		domains.stream().forEach(c ->{
			String sql = UP_SQL;
			sql = UP_SQL.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("BUSINESS_TYPE_CD_VAL", "'" + c.getBusinessTypeCode().v() + "'");
			sql = sql.replace("HIST_ID_VAL", "'" + c.getHistoryId() +"'");
			sql = sql.replace("SID_VAL", "'" + c.getSId() +"'");
			
			sb.append(sql);
		});
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		System.out.println(records);
		
	}

}
