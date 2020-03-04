package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforParams;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQi;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo.QqsmtEmpHealInsurQiPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
<<<<<<< HEAD

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
=======
>>>>>>> pj/pr/develop

@Stateless
public class JpaEmplHealInsurQualifiInforRepository extends JpaRepository implements EmplHealInsurQualifiInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpHealInsurQi f";

    private static final String SELECT_BY_LIST_EMP_START = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.endDate >= :startDate AND f.endDate <= :endDate";
    //private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId ";
    private static final String SELECT_BY_LIST_EMP = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.startDate >= :startDate AND f.startDate <= :endDate";
    //private static final String SELECT_BY_ID_AND_DATE = SELECT_ALL_QUERY_STRING + "f.empHealInsurQiPk.cid =:cid WHERE AND f.startDate <= :date AND f.endDate >= date";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_ID_HIS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate AND f.empHealInsurQiPk.hisId =:hisId";
    private static final String SELECT_BY_ID_LIST = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_ID_EMPIDS = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.cid =:cid AND f.empHealInsurQiPk.employeeId IN :employeeId";
    
    private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.employeeId =:employeeId ORDER BY f.startDate DESC ";
    private static final String SELECT_BY_EMPID_AND_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empHealInsurQiPk.employeeId =:employeeId AND f.empHealInsurQiPk.cid =:cid AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empHealInsurQiPk.employeeId =:employeeId AND  f.empHealInsurQiPk.hisId =:hisId ";

    private static final String SELECT_BY_LIST_EMP_CPS003 =  "SELECT f FROM QqsmtEmpHealInsurQi f WHERE  f.empHealInsurQiPk.employeeId IN :employeeIds  AND f.empHealInsurQiPk.cid =:cid";

    @Override
    public boolean checkEmplHealInsurQualifiInforEndDate(GeneralDate start, GeneralDate end, List<String> empIds) {
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi = this.queryProxy().query(SELECT_BY_LIST_EMP_START, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public boolean checkEmplHealInsurQualifiInforStartDate(GeneralDate start, GeneralDate end, List<String> empIds) {
        List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi = this.queryProxy().query(SELECT_BY_LIST_EMP, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpHealInsurQi.isEmpty();
    }

    @Override
    public Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", baseDate)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public Optional<HealInsurNumberInfor> getEmplHealInsurQualifiInforByEmpId(String cid, String empId, GeneralDate date, String historyId) {
        return this.queryProxy().query(SELECT_BY_ID_HIS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", date)
                .setParameter("hisId", historyId)
                .getSingle(x -> x.toHealInsurNumberInfor());
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, String empId, GeneralDate date) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", date)
                .getSingle(x -> x.toDomain());
    }


    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds, GeneralDate date) {
        return this.queryProxy().query(SELECT_BY_ID_LIST, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empIds)
                .setParameter("baseDate", date)
                .getList(x -> x.toDomain());
    }

    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInfor(String cid, List<String> empIds) {
        return this.queryProxy().query(SELECT_BY_ID_EMPIDS, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeId", empIds)
                .getList(x -> x.toDomain());
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getByEmpIdAndBaseDate(String empId, GeneralDate baseDate) {
        List<QqsmtEmpHealInsurQi> dataList = this.queryProxy()
                .query(SELECT_BY_EMPID_AND_DATE, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .setParameter("baseDate", baseDate)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();
        return dataList.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpHealInsurQi.toDomain(dataList));
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String empId) {
        List<QqsmtEmpHealInsurQi> list = this.queryProxy()
                .query(SELECT_BY_SID, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .getList();
        return Optional.ofNullable(QqsmtEmpHealInsurQi.toDomain(list));
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getEmpHealInsQualifiinfoById(String empId, String hisId) {
        val result = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpHealInsurQi.class)
                .setParameter("employeeId", empId)
                .setParameter("hisId", hisId)
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpHealInsurQi.toDomain(result));
    }

    @Override
    public EmplHealInsurQualifiInfor getEmpHealInsQualifiInfoOfEmp(String empId) {
        return null;
    }

    @Override
    public Optional<EmplHealInsurQualifiInfor> getByHistoryId(String historyId) {
        return null;
    }

    @Override
    public void add(EmplHealInsurQualifiInfor domain, EmpHealthInsurBenefits itemAdded, HealInsurNumberInfor hisItem) {
        this.commandProxy().insert(new QqsmtEmpHealInsurQi(
                new QqsmtEmpHealInsurQiPk(domain.getEmployeeId(), itemAdded.identifier(), AppContexts.user().companyId()),
                itemAdded.start(),
                itemAdded.end(),
                hisItem.getCareInsurNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null),
                hisItem.getHealInsNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null)
        ));
    }

    @Override
    public void update(EmpHealthInsurBenefits domain) {
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        String cid = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        String sql = UP_SQL;

        sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
        sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
        sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
        sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");

        sql = sql.replace("START_DATE_VAL", "'" + domain.start() + "'");
        sql = sql.replace("END_DATE_VAL", "'" + domain.end() + "'");

        sql = sql.replace("HIST_ID_VAL", "'" + domain.identifier() + "'");
        sql = sql.replace("CID_VAL","'" + cid + "'");
        sb.append(sql);

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void update(EmpHealthInsurBenefits domain, HealInsurNumberInfor item, String sid) {
        val oldEntity = this.queryProxy().find(new QqsmtEmpHealInsurQiPk(sid, item.getHistoryId(), AppContexts.user().companyId()), QqsmtEmpHealInsurQi.class);
        if (!oldEntity.isPresent()) {
            return;
        }
        oldEntity.get().startDate = domain.getDatePeriod().start();
        oldEntity.get().endDate = domain.getDatePeriod().end();
        oldEntity.get().healInsurNumber = item.getHealInsNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null);
        oldEntity.get().careIsNumber = item.getCareInsurNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null);

        this.commandProxy().update(oldEntity.get());
    }

    @Override
    public void remove(String employeeId, String hisId) {
        String cid = AppContexts.user().companyId();
        this.commandProxy().remove(QqsmtEmpHealInsurQi.class, new QqsmtEmpHealInsurQiPk(employeeId, hisId, cid));
    }
    
    @Override
    public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforDescCps003(String cid, List<String> empIds) {
    	
    	List<EmplHealInsurQualifiInfor> resultList = new ArrayList<>();
    	
    	List<QqsmtEmpHealInsurQi>  listEntity = this.queryProxy().query(SELECT_BY_LIST_EMP_CPS003, QqsmtEmpHealInsurQi.class)
                .setParameter("cid", cid)
                .setParameter("employeeIds", empIds)
                .getList();
    	if (listEntity.isEmpty()) {
			return new ArrayList<>();
		}
    	
    	// mapping sid with list list entity
    	Map<String, List<QqsmtEmpHealInsurQi>> entitiesByEmployee = listEntity.stream()
				.collect(Collectors.groupingBy(item -> item.getEmpHealInsurQiPk().getEmployeeId()));
    	
    	entitiesByEmployee.forEach((employeeId, entitiesOfEmp) -> {
    		List<EmpHealthInsurBenefits> mourPeriod = convertToHistoryItems(entitiesOfEmp);
    		EmplHealInsurQualifiInfor domain = new EmplHealInsurQualifiInfor(employeeId, mourPeriod);
			resultList.add(domain);
		});
    	
		return resultList;
    }

	private List<EmpHealthInsurBenefits> convertToHistoryItems(List<QqsmtEmpHealInsurQi> entities) {
		List<EmpHealthInsurBenefits> mourPeriod =  entities.stream()
				.map(ent -> new EmpHealthInsurBenefits(ent.empHealInsurQiPk.hisId, new DateHistoryItem(ent.empHealInsurQiPk.hisId, new DatePeriod(ent.startDate, ent.endDate))))
				.collect(Collectors.toList());
		return mourPeriod;
	}

	@Override
	public List<EmplHealInsurQualifiInfor> getEmplHealInsurQualifiInforDesc(String cid, List<String> empIds) {
		
		List<EmplHealInsurQualifiInfor> results = new ArrayList<>();
		
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			
			String sql = "SELECT * FROM QQSDT_KENHO_INFO WHERE CID = ? AND SID IN ("
					
					+ NtsStatement.In.createParamsString(subList) + ")" + " ORDER BY SID, START_DATE DESC";

			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				stmt.setString(1, cid);
				
				for (int i = 0; i < subList.size(); i++) {
					
					stmt.setString(2 + i, subList.get(i));
					
				}
				
				List<QqsmtEmpHealInsurQi> entities = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					
					QqsmtEmpHealInsurQi entity =  new QqsmtEmpHealInsurQi(
							
							new QqsmtEmpHealInsurQiPk(r.getString("SID"), r.getString("HIST_ID"),  r.getString("CID")),
							
							r.getGeneralDate("START_DATE"), r.getGeneralDate("END_DATE"),
							
							r.getString("KAIHO_NUM"),  r.getString("KENHO_NUM"));
					return entity;
				});
				
				
				if(!CollectionUtil.isEmpty(entities)) {
					Map<String, List<QqsmtEmpHealInsurQi>> entitieMaps = entities.stream().collect(Collectors.groupingBy(c -> c.empHealInsurQiPk.employeeId));
					
					entitieMaps.forEach((k, v) ->{
						
						results.add(QqsmtEmpHealInsurQi.toDomain(v));
						
					});
				}
				

			} catch (SQLException e) {
				
				throw new RuntimeException(e);
				
			}
			
		});
		
		return results;
		
	}

	@Override
	public void addAll(List<EmplHealInsurQualifiInforParams> params) {
		
		String INS_SQL = "INSERT INTO QQSDT_KENHO_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
				
				+ " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG," 
				
				+ " SID, HIST_ID, CID, START_DATE, END_DATE, KAIHO_NUM, KENHO_NUM)"
				
				+ " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
				
				+ " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
				
				+ " SID_VAL, HIST_ID_VAL, CID_VAL, START_DATE_VAL, END_DATE_VAL, KAIHO_NUM_VAL, KENHO_NUM_VAL); ";
		
		String insCcd = AppContexts.user().companyCode();
		
		String insScd = AppContexts.user().employeeCode();
		
		String insPg = AppContexts.programId();
		
		String updCcd = insCcd;
		
		String updScd = insScd;
		
		String updPg = insPg;
		
		StringBuilder sb = new StringBuilder();
		
		params.stream().forEach(c -> {
			 
			String sql = INS_SQL;
			
			sql = sql.replace("INS_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			
			sql = sql.replace("INS_CCD_VAL", "'" + insCcd + "'");
			
			sql = sql.replace("INS_SCD_VAL", "'" + insScd + "'");
			
			sql = sql.replace("INS_PG_VAL", "'" + insPg + "'");
			
			sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
			
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
			
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
			
			sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
			
			sql = sql.replace("SID_VAL", "'" + c.getDomain().getEmployeeId() + "'");
			
			sql = sql.replace("HIST_ID_VAL", "'" + c.getItemAdded().identifier() + "'");
			
			sql = sql.replace("CID_VAL", "'" + c.getCid() + "'");
			
			sql = sql.replace("START_DATE_VAL", "'" + c.getItemAdded().start() + "'");
			
			sql = sql.replace("END_DATE_VAL","'" +   c.getItemAdded().end() + "'");
			
			sql = sql.replace("KAIHO_NUM_VAL", "'" + c.getHisItem().getCareInsurNumber().map(e -> e.v().isEmpty() ? "" : e.v()).orElse("")+ "'");
			
			sql = sql.replace("KENHO_NUM_VAL", "'" + c.getHisItem().getHealInsNumber().map(e -> e.v().isEmpty() ? "" : e.v()).orElse("") + "'");
			
			sb.append(sql);
		});
		
		int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		
		System.out.println(records);
		
		
	}

	@Override
	public void updateAllStartEndDate(List<EmpHealthInsurBenefits> benefits) {
		
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                
        		+ " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
        		
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        
        String cid = AppContexts.user().companyId();
        
        String updCcd = AppContexts.user().companyCode();
        
        String updScd = AppContexts.user().employeeCode();
        
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        
        benefits.stream().forEach(c ->{
			
			String sql = UP_SQL;
			
			sql = UP_SQL.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
			
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("START_DATE_VAL", "'" + c.start() + "'");
			
			sql = sql.replace("END_DATE_VAL","'" +  c.end() + "'");
			
			sql = sql.replace("HIST_ID_VAL", "'" + c.identifier() +"'");
			
			sql = sql.replace("CID_VAL", "'" + cid +"'");
			
			sb.append(sql);
			
		});
        
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		
		System.out.println(records);
		
	}

	@Override
	public void updateAllDomain(List<EmplHealInsurQualifiInforParams> params) {

		
        String UP_SQL = "UPDATE QQSDT_KENHO_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                
        		+ " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL, KAIHO_NUM = KAIHO_NUM_VAL, KENHO_NUM = KENHO_NUM_VAL"
        		
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL AND SID = SID_VAL ;";
        
        String updCcd = AppContexts.user().companyCode();
        
        String updScd = AppContexts.user().employeeCode();
        
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        
        params.stream().forEach(c ->{
			
			String sql = UP_SQL;
			
			sql = UP_SQL.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
			
			sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
			
			sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
			
			sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
			
			sql = sql.replace("START_DATE_VAL", "'" + c.getItemAdded().start() + "'");
			
			sql = sql.replace("END_DATE_VAL","'" +  c.getItemAdded().end() + "'");
			
			sql = sql.replace("KAIHO_NUM_VAL", "'" + c.getHisItem().getCareInsurNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null)+ "'");
			
			sql = sql.replace("KENHO_NUM_VAL", "'" + c.getHisItem().getHealInsNumber().map(e -> e.v().isEmpty() ? null : e.v()).orElse(null) + "'");
			
			sql = sql.replace("HIST_ID_VAL", "'" + c.getItemAdded().identifier() +"'");
			
			sql = sql.replace("CID_VAL", "'" + c.getCid() +"'");
			
			sql = sql.replace("SID_VAL", "'" + c.getDomain().getEmployeeId() +"'");
			
			sb.append(sql);
			
		});
        
		int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
		
		System.out.println(records);
	}
}
