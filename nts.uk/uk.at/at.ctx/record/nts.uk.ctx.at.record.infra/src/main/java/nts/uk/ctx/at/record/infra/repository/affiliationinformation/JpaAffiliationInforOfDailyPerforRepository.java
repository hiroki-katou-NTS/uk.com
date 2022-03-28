package nts.uk.ctx.at.record.infra.repository.affiliationinformation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.infra.entity.affiliationinformation.KrcdtDayAffInfo;
import nts.uk.ctx.at.record.infra.entity.affiliationinformation.KrcdtDaiAffiliationInfPK;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@Stateless
public class JpaAffiliationInforOfDailyPerforRepository extends JpaRepository
		implements AffiliationInforOfDailyPerforRepository {

	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDayAffInfo a ");
		builderString.append("WHERE a.krcdtDaiAffiliationInfPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiAffiliationInfPK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAY_AFF_INFO Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + ymd + "'" ;
		try {
			con.createStatement().executeUpdate(sqlQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void add(AffiliationInforOfDailyPerfor domain) {
		try {
			Connection con = this.getEntityManager().unwrap(Connection.class);
			String bonusPaycode = domain.getAffiliationInfor().getBonusPaySettingCode().isPresent() ? "'" + domain.getAffiliationInfor().getBonusPaySettingCode().get().v() + "'" : null;
			String businessTypeCode = domain.getAffiliationInfor().getBusinessTypeCode().isPresent() ? "'" + domain.getAffiliationInfor().getBusinessTypeCode().get().v() + "'" : null;
			String workplaceGroupId = domain.getAffiliationInfor().getWorkplaceGroupId().isPresent() ? domain.getAffiliationInfor().getWorkplaceGroupId().get() : null; 
			Integer	nursingLicenseClass =  domain.getAffiliationInfor().getNursingLicenseClass().isPresent() ? domain.getAffiliationInfor().getNursingLicenseClass().get().value : null;
			boolean nursingManager = domain.getAffiliationInfor().getIsNursingManager().isPresent() ? domain.getAffiliationInfor().getIsNursingManager().get() : false;
			
			String insertTableSQL = "INSERT INTO KRCDT_DAY_AFF_INFO ( SID , YMD , EMP_CODE, JOB_ID , CLS_CODE , WKP_ID , BONUS_PAY_CODE, WORK_TYPE_CODE, WKP_GROUP_ID, NURSE_LICENSE_ATR, IS_NURSE_ADMINISTRATOR ) "
					+ "VALUES( '" + domain.getEmployeeId() + "' , '"
					+ domain.getYmd() + "' , '"
					+ domain.getAffiliationInfor().getEmploymentCode().v() + "' , '"
					+ domain.getAffiliationInfor().getJobTitleID() + "' , '"
					+ domain.getAffiliationInfor().getClsCode().v() + "' , '"
					+ domain.getAffiliationInfor().getWplID() + "' , "
					+ bonusPaycode +" , " + businessTypeCode + " , '" + workplaceGroupId + "' , " + nursingLicenseClass + " , " + nursingManager + " )";
			Statement statementI = con.createStatement();
			statementI.executeUpdate(JDBCUtil.toInsertWithCommonField(insertTableSQL));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private KrcdtDayAffInfo toEntity(AffiliationInforOfDailyPerfor domain) {
		val entity = new KrcdtDayAffInfo();

		entity.krcdtDaiAffiliationInfPK = new KrcdtDaiAffiliationInfPK();
		entity.krcdtDaiAffiliationInfPK.employeeId = domain.getEmployeeId();
		entity.krcdtDaiAffiliationInfPK.ymd = domain.getYmd();
		entity.bonusPayCode = domain.getAffiliationInfor().getBonusPaySettingCode().isPresent()
				? domain.getAffiliationInfor().getBonusPaySettingCode().get().v() : null;
		entity.businessTypeCode = domain.getAffiliationInfor().getBusinessTypeCode().isPresent()
				? domain.getAffiliationInfor().getBusinessTypeCode().get().v() : null;
		entity.classificationCode = domain.getAffiliationInfor().getClsCode() == null ? null
				: domain.getAffiliationInfor().getClsCode().v();
		entity.employmentCode = domain.getAffiliationInfor().getEmploymentCode() == null ? null
				: domain.getAffiliationInfor().getEmploymentCode().v();
		entity.jobtitleID = domain.getAffiliationInfor().getJobTitleID();
		entity.workplaceID = domain.getAffiliationInfor().getWplID();

		entity.workplaceGroupId = domain.getAffiliationInfor().getWorkplaceGroupId().isPresent()? domain.getAffiliationInfor().getWorkplaceGroupId().get() : null;
		entity.nursingLicenseClass = domain.getAffiliationInfor().getNursingLicenseClass().isPresent()? domain.getAffiliationInfor().getNursingLicenseClass().get().value : null;
		entity.nursingManager = domain.getAffiliationInfor().getIsNursingManager().isPresent()? domain.getAffiliationInfor().getIsNursingManager().get(): false;
		
		return entity;
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<AffiliationInforOfDailyPerfor> findByKey(String employeeId, GeneralDate ymd) {
		Optional<AffiliationInforOfDailyPerfor> data = Optional.empty();
		String sql = "select * from KRCDT_DAY_AFF_INFO"
				+ " where SID = ?"
				+ " and YMD = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, employeeId);
			stmt.setDate(2, Date.valueOf(ymd.localDate()));
			data = new NtsResultSet(stmt.executeQuery()).getSingle(rec -> {
				AffiliationInforOfDailyPerfor ent = new AffiliationInforOfDailyPerfor(
						new EmploymentCode(rec.getString("EMP_CODE")), 
						employeeId, 
						rec.getString("JOB_ID"), 
						rec.getString("WKP_ID"), 
						ymd, 
						new ClassificationCode(rec.getString("CLS_CODE")), 
						new BonusPaySettingCode(rec.getString("BONUS_PAY_CODE")),
						rec.getString("WORK_TYPE_CODE")== null?null: new BusinessTypeCode(rec.getString("WORK_TYPE_CODE")),
						rec.getString("WKP_GROUP_ID"),
						rec.getInt("NURSE_LICENSE_ATR")== null?null: EnumAdaptor.valueOf(rec.getInt("NURSE_LICENSE_ATR"), LicenseClassification.class),
						rec.getBoolean("IS_NURSE_ADMINISTRATOR")	
						);
				return ent;
			});
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return data;
	}

	@Override
	public void updateByKey(AffiliationInforOfDailyPerfor domain) {
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String bonusPaycode = domain.getAffiliationInfor().getBonusPaySettingCode().isPresent() ? "'" + domain.getAffiliationInfor().getBonusPaySettingCode().get().v() + "'" : null;
		String businessTypeCode = domain.getAffiliationInfor().getBusinessTypeCode().isPresent() ? "'" + domain.getAffiliationInfor().getBusinessTypeCode().get().v() + "'" : null;
		String workplaceGroupId = domain.getAffiliationInfor().getWorkplaceGroupId().isPresent() ? domain.getAffiliationInfor().getWorkplaceGroupId().get() : null; 
		Integer	nursingLicenseClass =  domain.getAffiliationInfor().getNursingLicenseClass().isPresent() ? domain.getAffiliationInfor().getNursingLicenseClass().get().value : null;
		Integer nursingManager = domain.getAffiliationInfor().getIsNursingManager().isPresent() ? (domain.getAffiliationInfor().getIsNursingManager().get() ? 1 : 0 ) : null;
		
		String updateTableSQL = " UPDATE KRCDT_DAY_AFF_INFO SET EMP_CODE = '"
				+ domain.getAffiliationInfor().getEmploymentCode().v() + "' , JOB_ID = '" + domain.getAffiliationInfor().getJobTitleID()
				+ "' , CLS_CODE = '" + domain.getAffiliationInfor().getClsCode().v() + "' , WKP_ID = '" + domain.getAffiliationInfor().getWplID()
				+ "' , BONUS_PAY_CODE = " + bonusPaycode +",WORK_TYPE_CODE ="+ businessTypeCode + ", WKP_GROUP_ID = '"+ workplaceGroupId + "' , NURSE_LICENSE_ATR ="+ nursingLicenseClass +  " , IS_NURSE_ADMINISTRATOR = "+ nursingManager + "  WHERE SID = '"
				+ domain.getEmployeeId() + "' AND YMD = '" + domain.getYmd() + "'";
		try {
				con.createStatement().executeUpdate(JDBCUtil.toUpdateWithCommonField(updateTableSQL));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<AffiliationInforOfDailyPerfor> finds(List<String> employeeId, DatePeriod ymd) {
		List<AffiliationInforOfDailyPerfor> result = new ArrayList<>();
		
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQuery(ymd, empIds));
		});
		return result;
	}
	
	@SneakyThrows
	private List<AffiliationInforOfDailyPerfor> internalQuery(DatePeriod baseDate, List<String> empIds) {
//		String subEmp = NtsStatement.In.createParamsString(empIds);
		List<AffiliationInforOfDailyPerfor> result = new ArrayList<>();
		String sql = "select EMP_CODE, SID, JOB_ID, WKP_ID, YMD, CLS_CODE, BONUS_PAY_CODE,WORK_TYPE_CODE,WKP_GROUP_ID,NURSE_LICENSE_ATR,IS_NURSE_ADMINISTRATOR from KRCDT_DAY_AFF_INFO "
				+ " where SID in (" + NtsStatement.In.createParamsString(empIds) + ")"
				+ " and YMD <= ?"
				+ " and YMD >= ?";
		
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			
			int i = 0;
			for (; i < empIds.size(); i++) {
				stmt.setString(1 + i, empIds.get(i));
			}

			stmt.setDate(1 + i, Date.valueOf(baseDate.end().localDate()));
			stmt.setDate(2 + i, Date.valueOf(baseDate.start().localDate()));
			
			result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
				AffiliationInforOfDailyPerfor ent = new AffiliationInforOfDailyPerfor(new EmploymentCode(rec.getString("EMP_CODE")), 
						rec.getString("SID"), rec.getString("JOB_ID"), rec.getString("WKP_ID"), rec.getGeneralDate("YMD"), 
						new ClassificationCode(rec.getString("CLS_CODE")),
						new BonusPaySettingCode(rec.getString("BONUS_PAY_CODE")),
						rec.getString("WORK_TYPE_CODE")== null?null: new BusinessTypeCode(rec.getString("WORK_TYPE_CODE")),
						rec.getString("WKP_GROUP_ID"),
						rec.getInt("NURSE_LICENSE_ATR")== null?null: EnumAdaptor.valueOf(rec.getInt("NURSE_LICENSE_ATR"), LicenseClassification.class),
						rec.getBoolean("IS_NURSE_ADMINISTRATOR")
						);
				return ent;
			});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	@SneakyThrows
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<AffiliationInforOfDailyPerfor> finds(Map<String, List<GeneralDate>> param) {
		List<String> subList = param.keySet().stream().collect(Collectors.toList());
		List<GeneralDate> subListDate = param.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
		List<AffiliationInforOfDailyPerfor> result = new ArrayList<>();

		CollectionUtil.split(subList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQueryMap(subListDate, empIds));
		});
		return result;
	}
	
	@SneakyThrows
	private List<AffiliationInforOfDailyPerfor> internalQueryMap(List<GeneralDate> subListDate, List<String> subList) {
		String subEmp = NtsStatement.In.createParamsString(subList);
    	String subInDate = NtsStatement.In.createParamsString(subListDate);
    	
		StringBuilder query = new StringBuilder("SELECT EMP_CODE, SID, JOB_ID, WKP_ID, YMD, CLS_CODE, BONUS_PAY_CODE,WORK_TYPE_CODE,WKP_GROUP_ID,NURSE_LICENSE_ATR,IS_NURSE_ADMINISTRATOR FROM KRCDT_DAY_AFF_INFO");
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
				return new AffiliationInforOfDailyPerfor(new EmploymentCode(rec.getString("EMP_CODE")), 
						rec.getString("SID"), rec.getString("JOB_ID"), rec.getString("WKP_ID"), rec.getGeneralDate("YMD"), 
						new ClassificationCode(rec.getString("CLS_CODE")), new BonusPaySettingCode(rec.getString("BONUS_PAY_CODE")),
						rec.getString("WORK_TYPE_CODE")== null?null: new BusinessTypeCode(rec.getString("WORK_TYPE_CODE")),
						rec.getString("WKP_GROUP_ID"),
						rec.getInt("NURSE_LICENSE_ATR")== null?null: EnumAdaptor.valueOf(rec.getInt("NURSE_LICENSE_ATR"), LicenseClassification.class),
						rec.getBoolean("IS_NURSE_ADMINISTRATOR"));
			});
		}
	}
}
