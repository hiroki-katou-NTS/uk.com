package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

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

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffParam;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHisPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;


@Stateless
public class JpaEmpCorpHealthOffHisRepository extends JpaRepository implements EmpCorpHealthOffHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND  f.empCorpOffHisPk.historyId =:hisId ";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId ";
    private static final String SELECT_BY_KEY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId IN :employeeIds  AND f.empCorpOffHisPk.cid =:cid AND f.startDate <= :startDate AND f.endDate >= :startDate ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.cid =:cid AND f.empCorpOffHisPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate ";
    private static final String SELECT_BY_SIDS = SELECT_ALL_QUERY_STRING + " WHERE f.empCorpOffHisPk.employeeId IN :sids AND f.empCorpOffHisPk.cid =:cid";
    private static final String SELECT_BY_ID_AND_BASE_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empCorpOffHisPk.employeeId =:sid AND f.empCorpOffHisPk.cid =:cid AND f.startDate <= :baseDate AND f.endDate >= :baseDate ";
    private static final String SELECT_BY_EMPID_DESC = SELECT_BY_EMPID + " ORDER BY f.startDate DESC ";
    private static final String SELECT_BY_EMPID_ASC = SELECT_BY_EMPID + " ORDER BY f.startDate ASC ";


    @Override
    public List<EmpCorpHealthOffHis> getAllEmpCorpHealthOffHis(){
        return null;
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId, String hisId){
        val result = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", employeeId)
                .setParameter("hisId", hisId)
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpCorpOffHis.toDomain(result));
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(String employeeId) {
        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_EMPID, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", employeeId)
                .getList();
       return Optional.ofNullable(QqsmtEmpCorpOffHis.toDomain(qqsmtEmpCorpOffHis));

    }

    @Override
    public Optional<EmpCorpHealthOffHis> getBySidDesc(String employeeId) {
        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_EMPID_DESC, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", employeeId)
                .getList();
        return Optional.ofNullable(QqsmtEmpCorpOffHis.toDomainCps(qqsmtEmpCorpOffHis));

    }

    @Override
    public Optional<EmpCorpHealthOffHis> getBySidAsc(String employeeId) {
        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_EMPID_ASC, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", employeeId)
                .getList();
        return Optional.ofNullable(QqsmtEmpCorpOffHis.toDomainCps(qqsmtEmpCorpOffHis));

    }

    @Override
    public Optional<String> getSocialInsuranceOfficeCd(String cid, String employeeId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpCorpOffHis.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .setParameter("baseDate",baseDate)
                .getSingle(x -> x.socialInsuranceOfficeCd);

    }

    @Override
    public Optional<EmpCorpHealthOffHis> getEmpCorpHealthOffHisById(List<String> employeeIds, GeneralDate startDate) {

        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_KEY_EMPID, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeIds", employeeIds)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("startDate", startDate)
                .getList();

        return qqsmtEmpCorpOffHis.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpCorpOffHis.toDomain(qqsmtEmpCorpOffHis));
    }

    @Override
    public Optional<EmpCorpHealthOffHis> getBySidAndBaseDate(String sid, GeneralDate baseDate){
        List<QqsmtEmpCorpOffHis> result =  this.queryProxy().query(SELECT_BY_ID_AND_BASE_DATE, QqsmtEmpCorpOffHis.class)
                .setParameter("sid", sid)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("baseDate", baseDate)
                .getList();
        return result.isEmpty() ? Optional.empty() : Optional.of(QqsmtEmpCorpOffHis.toDomain(result));
    }

    @Override
    public List<EmpCorpHealthOffHis> getByCidAndSids(List<String> sids){
        List<QqsmtEmpCorpOffHis> qqsmtEmpCorpOffHis =  this.queryProxy().query(SELECT_BY_SIDS, QqsmtEmpCorpOffHis.class)
                .setParameter("sids", sids)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();
        Map<String, List<QqsmtEmpCorpOffHis>> mapResult = qqsmtEmpCorpOffHis
                .stream().collect(Collectors.groupingBy(e -> e.empCorpOffHisPk.employeeId));
        List<EmpCorpHealthOffHis> result = new ArrayList<>();
        mapResult.forEach( (key,value) -> {
            result.add(QqsmtEmpCorpOffHis.toDomain(value));
        });
        return result;
    }

    @Override
    public void add(EmpCorpHealthOffHis domain, DateHistoryItem itemAdded, AffOfficeInformation itemInfo) {
        this.commandProxy().insert(new QqsmtEmpCorpOffHis(
                new QqsmtEmpCorpOffHisPk(AppContexts.user().companyId(), domain.getEmployeeId(), itemAdded.identifier()),
                itemAdded.start(),
                itemAdded.end(),
                itemInfo.getSocialInsurOfficeCode().v()
        ));
    }

    @Override
    public void update(DateHistoryItem historyItem) {
        String UP_SQL = "UPDATE QQSDT_SYAHO_OFFICE_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        String cid = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        String sql = UP_SQL;
        sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
        sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
        sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
        sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");

        sql = sql.replace("START_DATE_VAL", "'" + historyItem.start() + "'");
        sql = sql.replace("END_DATE_VAL","'" +  historyItem.end() + "'");

        sql = sql.replace("HIST_ID_VAL", "'" + historyItem.identifier() +"'");
        sql = sql.replace("CID_VAL", "'" + cid +"'");

        sb.append(sql);

        int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void update(DateHistoryItem historyItem, AffOfficeInformation info){
        String UP_SQL = "UPDATE QQSDT_SYAHO_OFFICE_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL, SYAHO_OFFICE_CD = SYAHO_OFFICE_CD_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        String cid = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        String sql = UP_SQL;
        sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
        sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
        sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
        sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");

        sql = sql.replace("START_DATE_VAL", "'" + historyItem.start() + "'");
        sql = sql.replace("END_DATE_VAL","'" +  historyItem.end() + "'");
        sql = sql.replace("SYAHO_OFFICE_CD_VAL","'" +  info.getSocialInsurOfficeCode().v() + "'");

        sql = sql.replace("HIST_ID_VAL", "'" + historyItem.identifier() +"'");
        sql = sql.replace("CID_VAL", "'" + cid +"'");

        sb.append(sql);

        int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void delete(String hisId, String sid) {
        String cid = AppContexts.user().companyId();
        this.commandProxy().remove(QqsmtEmpCorpOffHis.class, new QqsmtEmpCorpOffHisPk(cid, sid, hisId));
    }

    @Override
    public void addAll(List<EmpCorpHealthOffParam> domains) {
        String INS_SQL = "INSERT INTO QQSDT_SYAHO_OFFICE_INFO (INS_DATE, INS_CCD , INS_SCD , INS_PG,"
                + " UPD_DATE , UPD_CCD , UPD_SCD , UPD_PG,"
                + " HIST_ID, SID, CID,"
                + " START_DATE, END_DATE, SYAHO_OFFICE_CD)"
                + " VALUES (INS_DATE_VAL, INS_CCD_VAL, INS_SCD_VAL, INS_PG_VAL,"
                + " UPD_DATE_VAL, UPD_CCD_VAL, UPD_SCD_VAL, UPD_PG_VAL,"
                + " HIST_ID_VAL, SID_VAL, CID_VAL, START_DATE_VAL, END_DATE_VAL, SYAHO_OFFICE_CD_VAL); ";
        String cid = AppContexts.user().companyId();
        String insCcd = AppContexts.user().companyCode();
        String insScd = AppContexts.user().employeeCode();
        String insPg = AppContexts.programId();

        String updCcd = insCcd;
        String updScd = insScd;
        String updPg = insPg;
        StringBuilder sb = new StringBuilder();
        domains.stream().forEach(c ->{
            String sql = INS_SQL;
            DateHistoryItem dateHistItem = c.getHistoryItem();
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
            sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() + "'");
            sql = sql.replace("START_DATE_VAL", "'" + dateHistItem.start() + "'");
            sql = sql.replace("END_DATE_VAL","'" +  dateHistItem.end() + "'");
            sql = sql.replace("SYAHO_OFFICE_CD_VAL","'" +  c.getSocialInsurOfficeCode() + "'");

            sb.append(sql);
        });

        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        System.out.println(records);
    }

    @Override
    public void updateAll(List<DateHistoryItem> items) {
        String UP_SQL = "UPDATE QQSDT_SYAHO_OFFICE_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL;";
        String cid = AppContexts.user().companyId();
        String updCcd = AppContexts.user().companyCode();
        String updScd = AppContexts.user().employeeCode();
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        items.stream().forEach(c ->{
            String sql = UP_SQL;
            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
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
	public Map<String, DateHistoryItem> getAllEmpCorpHealthOffHisBySidsAndBaseDate(String cid, List<String> sids,
			GeneralDate standardDate) {
		
		Map<String, DateHistoryItem> result = new HashMap<String, DateHistoryItem>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			
			String sql = "SELECT * FROM QQSDT_SYAHO_OFFICE_INFO "
					
					+ "WHERE CID =? and START_DATE <= ? "
					
					+ "AND END_DATE >= ? AND SID IN ( "+ NtsStatement.In.createParamsString(subList)+")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				stmt.setString(1, cid);
				
				stmt.setDate(2, Date.valueOf(standardDate.localDate()));
				
				stmt.setDate(3, Date.valueOf(standardDate.localDate()));
				
				for (int i = 0 ; i < subList.size(); i++) {
					
					stmt.setString( 4 + i, subList.get(i));
					
				}
				
				new NtsResultSet(stmt.executeQuery()).forEach(rec -> {
					
					result.put(rec.getString("SID"), new DateHistoryItem(rec.getString("HIST_ID"),
							
							new DatePeriod(rec.getGeneralDate("START_DATE"), rec.getGeneralDate("END_DATE"))));
				
				});
				
			}catch (SQLException e) {
				
				throw new RuntimeException(e);
			}
			
		});
		
		return result;
	}

	@Override
	public List<EmpCorpHealthOffHis> getByCidAndSidsDesc(String cid, List<String> sids) {
		
		List<EmpCorpHealthOffHis> result = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			
			String sql = "SELECT * FROM QQSDT_SYAHO_OFFICE_INFO "
					
					+ " WHERE CID =?"
					
					+ " AND SID IN ( "+ NtsStatement.In.createParamsString(subList)+")"
					
					+ " ORDER BY SID, START_DATE DESC";;
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				stmt.setString(1, cid);
				
				for (int i = 0 ; i < subList.size(); i++) {
					
					stmt.setString( 2 + i, subList.get(i));
					
				}
				
				List<QqsmtEmpCorpOffHis> entities= new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return new QqsmtEmpCorpOffHis(
			                new QqsmtEmpCorpOffHisPk(rec.getString("CID"), rec.getString("SID"), rec.getString("HIST_ID")),
			                rec.getGeneralDate("START_DATE"),
			                rec.getGeneralDate("END_DATE"),
			                rec.getString("SYAHO_OFFICE_CD"));
				
				});
				
				Map<String, List<QqsmtEmpCorpOffHis>> mapResult = entities
		                .stream().collect(Collectors.groupingBy(e -> e.empCorpOffHisPk.employeeId));
				
				mapResult.forEach( (key,value) -> {
					
		            result.add(QqsmtEmpCorpOffHis.toDomainCps(value));
		            
		        });

			}catch (SQLException e) {
				
				throw new RuntimeException(e);
			}
			
		});
		
		return result;
	}

	@Override
	public void updateAllCps003(List<EmpCorpHealthOffParam> domains) {
        
		String UP_SQL = "UPDATE QQSDT_SYAHO_OFFICE_INFO SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
                + " START_DATE = START_DATE_VAL, END_DATE = END_DATE_VAL, SYAHO_OFFICE_CD = SYAHO_OFFICE_CD_VAL"
                + " WHERE HIST_ID = HIST_ID_VAL AND CID = CID_VAL AND SID = SID_VAL;";
        
		String cid = AppContexts.user().companyId();
		
        String updCcd = AppContexts.user().companyCode();
        
        String updScd = AppContexts.user().employeeCode();
        
        String updPg = AppContexts.programId();

        StringBuilder sb = new StringBuilder();
        
        domains.stream().forEach(c ->{
        	
            String sql = UP_SQL;
            
            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() +"'");
            
            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd +"'");
            
            sql = sql.replace("UPD_SCD_VAL", "'" + updScd +"'");
            
            sql = sql.replace("UPD_PG_VAL", "'" + updPg +"'");
            
            sql = sql.replace("START_DATE_VAL", "'" + c.getHistoryItem().start() + "'");
            
            sql = sql.replace("END_DATE_VAL","'" +  c.getHistoryItem().end() + "'");
            
            sql = sql.replace("SYAHO_OFFICE_CD_VAL", "'" + c.getSocialInsurOfficeCode() +"'");

            sql = sql.replace("HIST_ID_VAL", "'" + c.getHistoryItem().identifier() +"'");
            
            sql = sql.replace("CID_VAL", "'" + cid +"'");
            
            sql = sql.replace("SID_VAL", "'" + c.getEmployeeId() +"'");
            
            sb.append(sql);
            
        });
        
        int  records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
        
        System.out.println(records);
        
	}
	
}
