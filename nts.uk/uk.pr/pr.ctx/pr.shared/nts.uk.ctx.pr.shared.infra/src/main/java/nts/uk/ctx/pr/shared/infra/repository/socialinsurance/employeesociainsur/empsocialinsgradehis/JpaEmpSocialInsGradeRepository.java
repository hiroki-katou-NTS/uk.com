package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGrade;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisInter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtSyahoGraHist;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtSyahoGraHistPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaEmpSocialInsGradeRepository extends JpaRepository implements EmpSocialInsGradeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSyahoGraHist f";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE f.syahoGraHistPk.cid =:cid AND f.syahoGraHistPk.sid =:sid ORDER BY f.startYm DESC";
    private static final String SELECT_BY_SID_AND_BASE_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.syahoGraHistPk.cid =:cid AND f.syahoGraHistPk.sid =:sid AND f.startYm <= :standardYm AND f.endYm >= :standardYm ";

    @Override
    public void add(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info) {
        this.commandProxy().insert(QqsmtSyahoGraHist.toEntity(history, info));
    }

    @Override
    public void update(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info) {
        this.commandProxy().update(QqsmtSyahoGraHist.toEntity(history, info));
    }

    @Override
    public void delete(String cId, String sId, String histId) {
        this.commandProxy().remove(QqsmtSyahoGraHist.class, new QqsmtSyahoGraHistPk(cId, sId, histId));
    }

    @Override
    public Optional<EmpSocialInsGrade> getByEmpIdAndBaseDate(String companyId, String employeeId, GeneralDate standardDate) {
        if (standardDate == null) {
            return Optional.empty();
        }

        List<QqsmtSyahoGraHist> entities = this.queryProxy()
                .query(SELECT_BY_SID_AND_BASE_DATE, QqsmtSyahoGraHist.class)
                .setParameter("cid", companyId)
                .setParameter("sid", employeeId)
                .setParameter("standardYm", standardDate.yearMonth().v())
                .getList();

        if (!entities.isEmpty()) {
            return Optional.of(toDomainObject(entities));
        }

        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGrade> getByKey(String companyId, String employeeId, String historyId) {
        return this.queryProxy()
                .find(new QqsmtSyahoGraHistPk(companyId, employeeId, historyId), QqsmtSyahoGraHist.class)
                .map(this::toDomainObject);
    }

    @Override
    public Optional<EmpSocialInsGrade> getByEmpId(String companyId, String employeeId) {
        List<QqsmtSyahoGraHist> entities = this.queryProxy()
                .query(SELECT_BY_EMP_ID, QqsmtSyahoGraHist.class)
                .setParameter("cid", companyId)
                .setParameter("sid", employeeId)
                .getList();

        if (!entities.isEmpty()) {
            return Optional.of(toDomainObject(entities));
        }

        return Optional.empty();
    }

    private EmpSocialInsGradeHis toHistoryDomain(List<QqsmtSyahoGraHist> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return new EmpSocialInsGradeHis(
                entities.get(0).syahoGraHistPk.cid,
                entities.get(0).syahoGraHistPk.sid,
                entities.stream()
                        .map(e -> new YearMonthHistoryItem(e.syahoGraHistPk.histId, new YearMonthPeriod(new YearMonth(e.startYm), new YearMonth(e.endYm))))
                        .collect(Collectors.toList())
        );
    }

    private List<EmpSocialInsGradeInfo> toInfoDomains(List<QqsmtSyahoGraHist> entities) {
        return entities.stream()
                .map(e -> new EmpSocialInsGradeInfo(
                        e.syahoGraHistPk.histId,
                        e.syahoHosyuReal,
                        e.calAtr,
                        e.kenhoHosyu,
                        e.kenhoToq,
                        e.kouhoHosyu,
                        e.kouhoToq))
                .collect(Collectors.toList());
    }

    private EmpSocialInsGrade toDomainObject(List<QqsmtSyahoGraHist> entities) {
        return new EmpSocialInsGrade(toHistoryDomain(entities), toInfoDomains(entities));
    }

    private EmpSocialInsGrade toDomainObject(QqsmtSyahoGraHist entity) {
        return toDomainObject(Arrays.asList(entity));
    }

	@Override
	public Map<String, EmpSocialInsGrade> getBySidsAndBaseDate(String cid, List<String> employeeId, GeneralDate standardDate) {
		
		if (CollectionUtil.isEmpty(employeeId)) {

			return new HashMap<>();
		}

		Map<String, EmpSocialInsGrade> result = new HashMap<>();

		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {

			String sql = "SELECT * FROM QQSMT_SYAHO_GRA_HIST a"

					+ " WHERE  a.CID =?"
					
					+ " AND a.START_YM <=?"
					
					+ " AND a.END_YM >=?"
					
					+ " AND  a.SID in (" + NtsStatement.In.createParamsString(subList) + ")";

			try (PreparedStatement statement = this.connection().prepareStatement(sql)) {

				statement.setString(1, cid);
				
				statement.setInt(2, standardDate.yearMonth().v() );
				
				statement.setInt(3, standardDate.yearMonth().v());

				for (int i = 0; i < subList.size(); i++) {

					statement.setString(4 + i, subList.get(i));
					
				}
				

				 List<QqsmtSyahoGraHist> entities  = new NtsResultSet(statement.executeQuery()).getList(rec -> {

					 QqsmtSyahoGraHistPk pk = new QqsmtSyahoGraHistPk(rec.getString("CID"), rec.getString("SID"),
							rec.getString("HIST_ID"));

					 QqsmtSyahoGraHist entity = new QqsmtSyahoGraHist(pk,
							 
							rec.getInt("START_YM"), rec.getInt("END_YM"), 
							
							rec.getInt("SYAHO_HOSYU_REAL"),	rec.getInt("KENHO_TOQ"), 
							
							rec.getInt("KENHO_HOSYU"), rec.getInt("KOUHO_TOQ"), 
							
							rec.getInt("KOUHO_HOSYU"),rec.getInt("CAL_ATR"));

					return entity;
					
				});
				 
				Map<String, List<QqsmtSyahoGraHist>> mapEntities = entities.stream()
						
						.collect(Collectors.groupingBy(c -> c.syahoGraHistPk.sid));

				mapEntities.forEach((key, value) ->{
					
					result.put(key, toDomainObject(value));
					
				});

			} catch (SQLException e) {

				throw new RuntimeException(e);

			};
			
		});
		
		return result;
	}

	@Override
	public Map<String, EmpSocialInsGrade> getBySidsAndCid(String cid, List<String> sids) {

		if (CollectionUtil.isEmpty(sids)) {

			return new HashMap<>();
		}

		Map<String, EmpSocialInsGrade> result = new HashMap<>();

		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {

			String sql = "SELECT * FROM QQSMT_SYAHO_GRA_HIST a"

					+ " WHERE  a.CID = ?"

					+ " AND  a.SID in (" + NtsStatement.In.createParamsString(subList) + ")"

					+ " ORDER BY SID, START_YM DESC";

			try (PreparedStatement statement = this.connection().prepareStatement(sql)) {

				statement.setString(1, cid);

				for (int i = 0; i < subList.size(); i++) {

					statement.setString(2 + i, subList.get(i));

				}

				List<QqsmtSyahoGraHist> entities = new NtsResultSet(statement.executeQuery()).getList(rec -> {

					QqsmtSyahoGraHistPk pk = new QqsmtSyahoGraHistPk(rec.getString("CID"), rec.getString("SID"),

							rec.getString("HIST_ID"));

					QqsmtSyahoGraHist entity = new QqsmtSyahoGraHist(pk,

							rec.getInt("START_YM"), rec.getInt("END_YM"),

							rec.getInt("SYAHO_HOSYU_REAL"), rec.getInt("KENHO_TOQ"),

							rec.getInt("KENHO_HOSYU"), rec.getInt("KOUHO_TOQ"),

							rec.getInt("KOUHO_HOSYU"), rec.getInt("CAL_ATR"));

					return entity;

				});

				Map<String, List<QqsmtSyahoGraHist>> mapEntities = entities.stream()

						.collect(Collectors.groupingBy(c -> c.syahoGraHistPk.sid));

				mapEntities.forEach((key, value) -> {

					result.put(key, toDomainObject(value));

				});

			} catch (SQLException e) {

				throw new RuntimeException(e);

			};

		});

		return result;
	}

	@Override
	public void addAll(List<EmpSocialInsGradeHisInter> params) {
		
		List<QqsmtSyahoGraHist> entities = params.stream().map(c -> {
			
			return QqsmtSyahoGraHist.toEntity(c.getHistory(), c.getInfo());
			
		}).collect(Collectors.toList());
				
		this.commandProxy().insertAll(entities);		
		
	}

	@Override
	public void updateAll(List<EmpSocialInsGradeHisInter> params) {

		List<QqsmtSyahoGraHist> entities = params.stream().map(c -> {

			return QqsmtSyahoGraHist.toEntity(c.getHistory(), c.getInfo());

		}).collect(Collectors.toList());

		this.commandProxy().updateAll(entities);

	}

	@Override
	public void updateAllInfo(List<EmpSocialInsGradeInfo> params) {
		
	     String UP_SQL = "UPDATE QQSMT_SYAHO_GRA_HIST SET UPD_DATE = UPD_DATE_VAL, UPD_CCD = UPD_CCD_VAL, UPD_SCD = UPD_SCD_VAL, UPD_PG = UPD_PG_VAL,"
	                + " SYAHO_HOSYU_REAL = SYAHO_HOSYU_REAL_VAL, "
	                + " KENHO_TOQ = KENHO_TOQ_VAL, KENHO_HOSYU = KENHO_HOSYU_VAL, "
	                + " KOUHO_TOQ = KOUHO_TOQ_VAL, KOUHO_HOSYU = KOUHO_HOSYU_VAL, "
	                + " CAL_ATR = CAL_ATR_VAL"
	                + " WHERE HIST_ID = HIST_ID_VAL;";
	     
	        String updCcd = AppContexts.user().companyCode();
	        
	        String updScd = AppContexts.user().employeeCode();
	        
	        String updPg = AppContexts.programId();

	        StringBuilder sb = new StringBuilder();
	        
	        params.stream().forEach(c -> {
	        	
	            String sql = UP_SQL;
	            
	            sql = sql.replace("UPD_DATE_VAL", "'" + GeneralDateTime.now() + "'");
	            
	            sql = sql.replace("UPD_CCD_VAL", "'" + updCcd + "'");
	            
	            sql = sql.replace("UPD_SCD_VAL", "'" + updScd + "'");
	            
	            sql = sql.replace("UPD_PG_VAL", "'" + updPg + "'");
	            
	            sql = sql.replace("SYAHO_HOSYU_REAL_VAL", ""+  c.getSocInsMonthlyRemune().v() +"");
	            
	            sql = sql.replace("KENHO_TOQ_VAL", ""+ c.getHealInsGrade().map(PrimitiveValueBase::v).orElse(null) +"");
	            
	            sql = sql.replace("KENHO_HOSYU_VAL", ""+ c.getHealInsStandMonthlyRemune().map(PrimitiveValueBase::v).orElse(null) +"");

	            sql = sql.replace("KOUHO_TOQ_VAL", ""+ c.getPensionInsGrade().map(PrimitiveValueBase::v).orElse(null) +"");
	            
	            sql = sql.replace("KOUHO_HOSYU_VAL", ""+ c.getPensionInsStandCompenMonthly().map(PrimitiveValueBase::v).orElse(null)+"");

	            sql = sql.replace("CAL_ATR_VAL", "" + c.getCalculationAtr().value + "");
	            
	            sql = sql.replace("HIST_ID_VAL", "'" + c.getHistId() + "'");
	            
	            sb.append(sql);
	        });
	        
	        int records = this.getEntityManager().createNativeQuery(sb.toString()).executeUpdate();
	        
	        System.out.println(records);
	}
	
}
