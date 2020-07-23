package nts.uk.ctx.at.request.infra.repository.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.infra.entity.application.KrqdpAppReflectState;
import nts.uk.ctx.at.request.infra.entity.application.KrqdpApplication;
import nts.uk.ctx.at.request.infra.entity.application.KrqdtAppReflectState;
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
		KrqdtApplication krqdtApplication = this.findEntityByID(application.getAppID()).get();
		krqdtApplication.setOpReversionReason(application.getOpReversionReason().map(x -> x.v()).orElse(null));
		krqdtApplication.setOpAppReason(application.getOpAppReason().map(x -> x.v()).orElse(null));
		krqdtApplication.setOpAppStandardReasonCD(application.getOpAppStandardReasonCD().map(x -> x.v()).orElse(null));
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			Optional<KrqdtAppReflectState> opKrqdtAppReflectState = krqdtApplication.getKrqdtAppReflectStateLst().stream()
					.filter(x -> x.getPk().getTargetDate().equals(reflectionStatusOfDay.getTargetDate())).findAny();
			if(opKrqdtAppReflectState.isPresent()) {
				KrqdtAppReflectState krqdtAppReflectState = opKrqdtAppReflectState.get();
				krqdtAppReflectState.setScheReflectStatus(reflectionStatusOfDay.getScheReflectStatus().value);
				krqdtAppReflectState.setActualReflectStatus(reflectionStatusOfDay.getActualReflectStatus().value);
				krqdtAppReflectState.setOpReasonScheCantReflect(
						reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpReasonScheCantReflect().map(y -> y.value).orElse(null)).orElse(null));
				krqdtAppReflectState.setOpScheReflectDateTime(
						reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpScheReflectDateTime().orElse(null)).orElse(null));
				krqdtAppReflectState.setOpReasonActualCantReflect(
						reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpReasonActualCantReflect().map(y -> y.value).orElse(null)).orElse(null));
				krqdtAppReflectState.setOpActualReflectDateTime(
						reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpActualReflectDateTime().orElse(null)).orElse(null));
				krqdtAppReflectState.setOpReasonScheCantReflectCancel(
						reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpReasonScheCantReflect().map(y -> y.value).orElse(null)).orElse(null));
				krqdtAppReflectState.setOpScheReflectDateTimeCancel(
						reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpScheReflectDateTime().orElse(null)).orElse(null));
				krqdtAppReflectState.setOpReasonActualCantReflectCancel(
						reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpReasonActualCantReflect().map(y -> y.value).orElse(null)).orElse(null));
				krqdtAppReflectState.setOpActualReflectDateTimeCancel(
						reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpActualReflectDateTime().orElse(null)).orElse(null));
			}
		}
		this.commandProxy().update(krqdtApplication);
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
		return this.findEntityByID(appID).map(x -> x.toDomain());
	}
	
	private Map<String, Object> toObject(NtsResultRecord rec) {
		Map<String, Object> map = new HashMap<String, Object>();
		// KRQDT_APPLICATION
		map.put("aEXCLUS_VER", rec.getInt("aEXCLUS_VER"));
		map.put("aCONTRACT_CD", rec.getString("aCONTRACT_CD"));
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
		map.put("bCONTRACT_CD", rec.getString("bCONTRACT_CD"));
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
	
	private List<KrqdtApplication> convertToEntity(List<Map<String, Object>> mapLst) {
		List<KrqdtApplication> result = mapLst.stream().collect(Collectors.groupingBy(x -> x.get("aAPP_ID"))).entrySet()
			.stream().map(x -> {
				List<KrqdtAppReflectState> krqdtAppReflectStateLst = x.getValue().stream().collect(Collectors.groupingBy(y -> y.get("bAPP_DATE"))).entrySet()
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
						KrqdtAppReflectState krqdtAppReflectState = new KrqdtAppReflectState(
								new KrqdpAppReflectState(
										(String) y.getValue().get(0).get("bCID"), 
										(String) y.getValue().get(0).get("bAPP_ID"), 
										(GeneralDate) y.getValue().get(0).get("bAPP_DATE")), 
								scheReflectStatus, 
								actualReflectStatus, 
								opReasonScheCantReflect, 
								opScheReflectDateTime, 
								opReasonActualCantReflect, 
								opActualReflectDateTime, 
								opReasonScheCantReflectCancel, 
								opScheReflectDateTimeCancel, 
								opReasonActualCantReflectCancel, 
								opActualReflectDateTimeCancel, 
								null);
						krqdtAppReflectState.contractCd = (String) y.getValue().get(0).get("bCONTRACT_CD");
						return krqdtAppReflectState;
					}).collect(Collectors.toList());
				KrqdtApplication krqdtApplication = new KrqdtApplication(
						new KrqdpApplication(
								(String) x.getValue().get(0).get("aCID"), 
								(String) x.getValue().get(0).get("aAPP_ID")), 
						(int) x.getValue().get(0).get("aEXCLUS_VER"), 
						(int) x.getValue().get(0).get("aPRE_POST_ATR"), 
						(GeneralDateTime) x.getValue().get(0).get("aINPUT_DATE"), 
						(String) x.getValue().get(0).get("aENTERED_PERSON_SID"), 
						(String) x.getValue().get(0).get("aREASON_REVERSION"), 
						(GeneralDate) x.getValue().get(0).get("aAPP_DATE"), 
						(Integer) x.getValue().get(0).get("aFIXED_REASON"), 
						(String) x.getValue().get(0).get("aAPP_REASON"), 
						(int) x.getValue().get(0).get("aAPP_TYPE"), 
						(String) x.getValue().get(0).get("aAPPLICANTS_SID"), 
						(GeneralDate) x.getValue().get(0).get("aAPP_START_DATE"), 
						(GeneralDate) x.getValue().get(0).get("aAPP_END_DATE"), 
						(Integer) x.getValue().get(0).get("aSTAMP_OPTION_ATR"), 
						krqdtAppReflectStateLst);
				krqdtApplication.contractCd = (String) x.getValue().get(0).get("aCONTRACT_CD");
				return krqdtApplication;
			}).collect(Collectors.toList());
		return result;
	}
	
	private Optional<KrqdtApplication> findEntityByID(String appID) {
		String sql = "select a.EXCLUS_VER as aEXCLUS_VER, a.CONTRACT_CD as aCONTRACT_CD, a.CID as aCID, a.APP_ID as aAPP_ID, a.PRE_POST_ATR as aPRE_POST_ATR, " +
				"a.INPUT_DATE as aINPUT_DATE, a.ENTERED_PERSON_SID as aENTERED_PERSON_SID, " +
				"a.REASON_REVERSION as aREASON_REVERSION, a.APP_DATE as aAPP_DATE, a.FIXED_REASON as aFIXED_REASON, a.APP_REASON as aAPP_REASON, a.APP_TYPE as aAPP_TYPE, " +
				"a.APPLICANTS_SID as aAPPLICANTS_SID, a.APP_START_DATE as aAPP_START_DATE, a.APP_END_DATE as aAPP_END_DATE, a.STAMP_OPTION_ATR as aSTAMP_OPTION_ATR, " +
				"b.CONTRACT_CD as bCONTRACT_CD, b.CID as bCID, b.APP_ID as bAPP_ID, b.APP_DATE as bAPP_DATE, b.REFLECT_PLAN_STATE as bREFLECT_PLAN_STATE, b.REFLECT_PER_STATE as bREFLECT_PER_STATE, " +
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
		List<KrqdtApplication> krqdtApplicationLst = convertToEntity(mapLst);
		if(CollectionUtil.isEmpty(krqdtApplicationLst)) {
			return Optional.empty();
		}
		return Optional.of(krqdtApplicationLst.get(0));
	}

}
