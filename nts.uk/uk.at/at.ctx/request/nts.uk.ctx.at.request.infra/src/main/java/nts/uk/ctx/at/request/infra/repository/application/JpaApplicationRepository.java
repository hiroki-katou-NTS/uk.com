package nts.uk.ctx.at.request.infra.repository.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.application.KrqdpApplication;
import nts.uk.ctx.at.request.infra.entity.application.KrqdtApplication;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApplicationRepository extends JpaRepository implements ApplicationRepository {

	@Override
	public Optional<Application> findByID(String companyID, String appID) {
		return this.findByID(appID);
	}

	@Override
	public void insert(Application application) {
		this.commandProxy().insert(KrqdtApplication.fromDomain(application));
		this.getEntityManager().flush();
		
	}

	@Override
	public void update(Application application) {
		this.commandProxy().update(KrqdtApplication.fromDomain(application));
		this.getEntityManager().flush();
	}

	@Override
	public void remove(String appID) {
		String companyID = AppContexts.user().companyId();
		this.commandProxy().remove(KrqdtApplication.class, new KrqdpApplication(companyID, appID));
		this.getEntityManager().flush();
	}

	@Override
	public Optional<Application> findByID(String appID) {
		String sql = "select a.EXCLUS_VER as aEXCLUS_VER, a.CID as aCID, a.APP_ID as aAPP_ID, a.PRE_POST_ATR as aPRE_POST_ATR, a.INPUT_DATE as aINPUT_DATE, a.ENTERED_PERSON_SID as aENTERED_PERSON_SID, " +
				"a.REASON_REVERSION as aREASON_REVERSION, a.APP_DATE as aAPP_DATE, a.FIXED_REASON as aFIXED_REASON, a.APP_REASON as aAPP_REASON, a.APP_TYPE as aAPP_TYPE, " +
				"a.APPLICANTS_SID as aAPPLICANTS_SID, a.APP_START_DATE as aAPP_START_DATE, a.APP_END_DATE as aAPP_END_DATE, a.STAMP_OPTION_ATR as aSTAMP_OPTION_ATR, " +
				"b.CID as bCID, b.APP_ID as bAPP_ID, b.APP_DATE as bAPP_DATE, b.REFLECT_PLAN_STATE as bREFLECT_PLAN_STATE, b.REFLECT_PER_STATE as bREFLECT_PER_STATE, " +
				"b.REFLECT_PLAN_SCHE_REASON as bREFLECT_PLAN_SCHE_REASON, b.REFLECT_PLAN_TIME as bREFLECT_PLAN_TIME, " +
				"b.REFLECT_PER_SCHE_REASON as bREFLECT_PER_SCHE_REASON, b.REFLECT_PER_TIME as bREFLECT_PER_TIME, " +
				"b.CANCEL_PLAN_SCHE_REASON as bCANCEL_PLAN_SCHE_REASON, b.CANCEL_PLAN_TIME as bCANCEL_PLAN_TIME, " +
				"b.CANCEL_PER_SCHE_REASON as bCANCEL_PER_SCHE_REASON, b.CANCEL_PER_TIME as bCANCEL_PER_TIME " +
				"from KRQDT_APPLICATION a left join KRQDT_APP_REFLECT_STATE b " +
				"on a.CID = b.CID and a.APP_ID = b.APP_ID " +
				"where a.APP_ID = @appID";
		List<Map<String, Object>> mapLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("appID", appID)
				.getList(rec -> toObject(rec));
		List<Application> applicationLst = convertToDomain(mapLst);
		if(CollectionUtil.isEmpty(applicationLst)) {
			return Optional.empty();
		}
		return Optional.of(applicationLst.get(0));
	}
	
	private Map<String, Object> toObject(NtsResultRecord rec) {
		Map<String, Object> map = new HashMap<String, Object>();
		// KRQDT_APPLICATION
		map.put("aEXCLUS_VER", rec.getInt("aEXCLUS_VER"));
		map.put("aCID", rec.getString("aCID"));
		map.put("aAPP_ID", rec.getString("aAPP_ID"));
		map.put("aPRE_POST_ATR", rec.getInt("aPRE_POST_ATR"));
		map.put("aINPUT_DATE", rec.getGeneralDateTime("aINPUT_DATE"));
		map.put("aENTERED_PERSON_SID", rec.getString("aENTERED_PERSON_SID"));
		map.put("aREASON_REVERSION", rec.getString("aREASON_REVERSION"));
		map.put("aAPP_DATE", rec.getGeneralDate("aAPP_DATE"));
		map.put("aFIXED_REASON", rec.getInt("aFIXED_REASON"));
		map.put("aAPP_REASON", rec.getString("aAPP_REASON"));
		map.put("aAPP_TYPE", rec.getInt("aAPP_TYPE"));
		map.put("aAPPLICANTS_SID", rec.getString("aAPPLICANTS_SID"));
		map.put("aAPP_START_DATE", rec.getGeneralDate("aAPP_START_DATE"));
		map.put("aAPP_END_DATE", rec.getGeneralDate("aAPP_END_DATE"));
		map.put("aSTAMP_OPTION_ATR", rec.getInt("aSTAMP_OPTION_ATR"));
		// KRQDT_APP_REFLECT_STATE
		map.put("bCID", rec.getString("bCID"));
		map.put("bAPP_ID", rec.getString("bAPP_ID"));
		map.put("bAPP_DATE", rec.getGeneralDate("bAPP_DATE"));
		map.put("bREFLECT_PLAN_STATE", rec.getInt("bREFLECT_PLAN_STATE"));
		map.put("bREFLECT_PER_STATE", rec.getInt("bREFLECT_PER_STATE"));
		map.put("bREFLECT_PLAN_SCHE_REASON", rec.getInt("bREFLECT_PLAN_SCHE_REASON"));
		map.put("bREFLECT_PLAN_TIME", rec.getGeneralDateTime("bREFLECT_PLAN_TIME"));
		map.put("bREFLECT_PER_SCHE_REASON", rec.getInt("bREFLECT_PER_SCHE_REASON"));
		map.put("bREFLECT_PER_TIME", rec.getGeneralDateTime("bREFLECT_PER_TIME"));
		map.put("bCANCEL_PLAN_SCHE_REASON", rec.getInt("bCANCEL_PLAN_SCHE_REASON"));
		map.put("bCANCEL_PLAN_TIME", rec.getGeneralDateTime("bCANCEL_PLAN_TIME"));
		map.put("bCANCEL_PER_SCHE_REASON", rec.getInt("bCANCEL_PER_SCHE_REASON"));
		map.put("bCANCEL_PER_TIME", rec.getGeneralDateTime("bCANCEL_PER_TIME"));
		return map;
	}
	
	private List<Application> convertToDomain(List<Map<String, Object>> mapLst) {
		List<Application> result = mapLst.stream().collect(Collectors.groupingBy(x -> x.get("aAPP_ID"))).entrySet()
			.stream().map(x -> {
				List<ReflectionStatusOfDay> reflectionStatusOfDayLst = x.getValue().stream().collect(Collectors.groupingBy(y -> y.get("bAPP_DATE"))).entrySet()
					.stream().map(y -> {
						int scheReflectStatus = (int) y.getValue().get(0).get("bREFLECT_PLAN_STATE");
						int actualReflectStatus = (int) y.getValue().get(0).get("bREFLECT_PER_STATE");
						GeneralDateTime opActualReflectDateTime = (GeneralDateTime) y.getValue().get(0).get("bREFLECT_PER_TIME");
						GeneralDateTime opScheReflectDateTime = (GeneralDateTime) y.getValue().get(0).get("bREFLECT_PLAN_TIME");
						Integer opReasonActualCantReflect = (Integer) y.getValue().get(0).get("bREFLECT_PER_SCHE_REASON");
						Integer opReasonScheCantReflect = (Integer) y.getValue().get(0).get("bREFLECT_PLAN_SCHE_REASON");
						GeneralDateTime opActualReflectDateTimeCancel = (GeneralDateTime) y.getValue().get(0).get("bCANCEL_PER_TIME");
						GeneralDateTime opScheReflectDateTimeCancel = (GeneralDateTime) y.getValue().get(0).get("bCANCEL_PLAN_TIME");
						Integer opReasonActualCantReflectCancel = (Integer) y.getValue().get(0).get("bCANCEL_PER_SCHE_REASON");
						Integer opReasonScheCantReflectCancel = (Integer) y.getValue().get(0).get("bCANCEL_PLAN_SCHE_REASON");
						Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppReflect = Optional.empty();
						if(opActualReflectDateTime != null || opScheReflectDateTime != null ||
								opReasonActualCantReflect != null || opReasonScheCantReflect != null) {
							opUpdateStatusAppReflect = Optional.of(DailyAttendanceUpdateStatus.createNew(
									opActualReflectDateTime, 
									opScheReflectDateTime, 
									opReasonActualCantReflect, 
									opReasonScheCantReflect));
						}
						Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppCancel = Optional.empty();
						if(opActualReflectDateTimeCancel != null || opScheReflectDateTimeCancel != null ||
								opReasonActualCantReflectCancel != null || opReasonScheCantReflectCancel != null) {
							opUpdateStatusAppCancel = Optional.of(DailyAttendanceUpdateStatus.createNew(
									opActualReflectDateTimeCancel, 
									opScheReflectDateTimeCancel, 
									opReasonActualCantReflectCancel, 
									opReasonScheCantReflectCancel));
						}
						ReflectionStatusOfDay reflectionStatusOfDay = new ReflectionStatusOfDay(
								EnumAdaptor.valueOf(actualReflectStatus, ReflectedState.class), 
								EnumAdaptor.valueOf(scheReflectStatus, ReflectedState.class), 
								(GeneralDate) y.getValue().get(0).get("bAPP_DATE"), 
								opUpdateStatusAppReflect, 
								opUpdateStatusAppCancel);
						return reflectionStatusOfDay;
					}).collect(Collectors.toList());
				Application application = new Application(
						(String) x.getValue().get(0).get("aAPP_ID"), 
						EnumAdaptor.valueOf((int) x.getValue().get(0).get("aPRE_POST_ATR"), PrePostAtr.class), 
						(String) x.getValue().get(0).get("aAPPLICANTS_SID"), 
						EnumAdaptor.valueOf((int) x.getValue().get(0).get("aAPP_TYPE"), ApplicationType.class), 
						new ApplicationDate((GeneralDate) x.getValue().get(0).get("aAPP_DATE")), 
						(String) x.getValue().get(0).get("aENTERED_PERSON_SID"), 
						(GeneralDateTime) x.getValue().get(0).get("aINPUT_DATE"), 
						new ReflectionStatus(reflectionStatusOfDayLst));
				application.setVersion((int) x.getValue().get(0).get("aEXCLUS_VER"));
				Integer opStampRequestMode = (Integer) x.getValue().get(0).get("aSTAMP_OPTION_ATR");
				if(opStampRequestMode != null) {
					application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(opStampRequestMode, StampRequestMode.class)));
				}
				String opReversionReason = (String) x.getValue().get(0).get("aREASON_REVERSION");
				if(opReversionReason != null) {
					application.setOpReversionReason(Optional.of(new ReasonForReversion(opReversionReason)));
				}
				GeneralDate opAppStartDate = (GeneralDate) x.getValue().get(0).get("aAPP_START_DATE");
				if(opAppStartDate != null) {
					application.setOpAppStartDate(Optional.of(new ApplicationDate(opAppStartDate)));
				}
				GeneralDate opAppEndDate = (GeneralDate) x.getValue().get(0).get("aAPP_END_DATE");
				if(opAppEndDate != null) {
					application.setOpAppEndDate(Optional.of(new ApplicationDate(opAppEndDate)));
				}
				String opAppReason = (String) x.getValue().get(0).get("aAPP_REASON");
				if(opAppReason != null) {
					application.setOpAppReason(Optional.of(new AppReason(opAppReason)));
				}
				Integer opAppStandardReasonCD = (Integer) x.getValue().get(0).get("aFIXED_REASON");
				if(opAppStandardReasonCD != null) {
					application.setOpAppStandardReasonCD(Optional.of(new AppStandardReasonCode(opAppStandardReasonCD)));
				}
				return application;
			}).collect(Collectors.toList());
		return result;
	}

}
