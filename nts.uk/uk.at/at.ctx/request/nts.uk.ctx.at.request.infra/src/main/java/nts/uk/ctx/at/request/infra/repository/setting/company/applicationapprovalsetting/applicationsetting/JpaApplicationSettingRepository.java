package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.*;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqmtApplicationSet;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApplicationSettingRepository extends JpaRepository implements ApplicationSettingRepository {

	@Override
	public ApplicationSetting findByAppType(String companyID, ApplicationType appType) {
		String sql = "select a.CID as aCID, a.BASE_DATE_SET as aBASE_DATE_SET, a.MON_ATD_CONFIRM_ATR as aMON_ATD_CONFIRM_ATR, a.ATD_LOCK_ATR as aATD_LOCK_ATR, " +
					"a.ATD_CONFIRM_ATR as aATD_CONFIRM_ATR, a.DAY_ATD_CONFIRM_ATR as aDAY_ATD_CONFIRM_ATR, a.REASON_REQUIRE_ATR as aREASON_REQUIRE_ATR, " +
					"a.FIXED_REASON_REQUIRE_ATR as aFIXED_REASON_REQUIRE_ATR, a.PRE_POST_DISPLAY_ATR as aPRE_POST_DISPLAY_ATR, " +
					"a.SEND_MAIL_INI_ATR as aSEND_MAIL_INI_ATR, a.TIME_NIGHT_REFLECT_ATR as aTIME_NIGHT_REFLECT_ATR, " +
					"b.CID as bCID, b.APP_TYPE as bAPP_TYPE, b.PRE_POST_INIT_ATR as bPRE_POST_INIT_ATR, b.PRE_POST_CHANGE_ATR as bPRE_POST_CHANGE_ATR, " + 
					"b.APV_SEND_MAIL_ATR as bAPV_SEND_MAIL_ATR, b.APP_SEND_MAIL_ATR as bAPP_SEND_MAIL_ATR, b.POST_FUTURE_ALLOW_ATR as bPOST_FUTURE_ALLOW_ATR, " + 
					"b.PRE_EARLY_DAYS as bPRE_EARLY_DAYS, b.USE_ATR as bUSE_ATR, b.PRE_OT_CHECK_SET as bPRE_OT_CHECK_SET, b.PRE_OT_BEF_WORK_TIME as bPRE_OT_BEF_WORK_TIME, " + 
					"b.PRE_OT_AFT_WORK_TIME as bPRE_OT_AFT_WORK_TIME, b.PRE_OT_BEF_AFT_WORK_TIME as bPRE_OT_BEF_AFT_WORK_TIME, " +
					"c.CID as cCID, c.CLOSURE_ID as cCLOSURE_ID, c.MCLOSE_CRITERIA_ATR as cMCLOSE_CRITERIA_ATR, c.MCLOSE_DAYS as cMCLOSE_DAYS, c.USE_ATR as cUSE_ATR, " + 
					"d.CID as dCID, d.APP_TYPE as dAPP_TYPE, d.OPTION_ATR as dOPTION_ATR " +
					"from KRQST_APPLICATION a left join KRQST_APP_TYPE b on a.CID =  b.CID " +
					"left join KRQST_APP_MCLOSE c on a.CID = c.CID " +
					"left join KRQST_REPRESENT_APP d on b.CID = d.CID and b.APP_TYPE = d.APP_TYPE " +
					"where b.CID = @companyID and b.APP_TYPE = @appType";
		List<Map<String, Object>> mapLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramInt("appType", appType.value)
				.getList(rec -> toObject(rec));
		List<ApplicationSetting> applicationSettingLst = convertToDomain(mapLst);
		if(CollectionUtil.isEmpty(applicationSettingLst)) {
			throw new RuntimeException("setting master data");
		}
		return applicationSettingLst.get(0);
	}

	@Override
	public Optional<ApplicationSetting> findByCompanyId(String companyId) {
		return this.queryProxy().find(companyId, KrqmtApplicationSet.class).map(KrqmtApplicationSet::toDomainApplicationSetting);
	}

	@Override
	public Integer getNightOvertimeReflectAtr(String companyId) {
		return this.queryProxy().find(companyId, KrqmtApplicationSet.class).map(KrqmtApplicationSet::getTimeNightReflectAtr).orElse(null);
	}

	@Override
	public void save(ApplicationSetting domain, List<DisplayReason> reasonDisplaySettings, int nightOvertimeReflectAtr) {
		Optional<KrqmtApplicationSet> optEntity = this.queryProxy().find(domain.getCompanyID(), KrqmtApplicationSet.class);
		if (optEntity.isPresent()) {
			KrqmtApplicationSet oldEntity = optEntity.get();
			KrqmtApplicationSet newEntity = KrqmtApplicationSet.create(domain, reasonDisplaySettings, nightOvertimeReflectAtr);
			newEntity.setContractCd(oldEntity.getContractCd());
//			newEntity.setInsDate(oldEntity.getInsDate());
//			newEntity.setInsCcd(oldEntity.getInsCcd());
//			newEntity.setInsScd(oldEntity.getInsScd());
//			newEntity.setInsPg(oldEntity.getInsPg());
			newEntity.getAppDeadlineSetings().forEach(ads -> {
			    ads.setContractCd(oldEntity.getContractCd());
            });
			newEntity.getAppProxySettings().forEach(aps -> {
			    aps.setContractCd(oldEntity.getContractCd());
            });
			newEntity.getAppTypeSettings().forEach(ats -> {
			    ats.setContractCd(oldEntity.getContractCd());
            });
			this.commandProxy().update(newEntity);
		} else {
			this.commandProxy().insert(KrqmtApplicationSet.create(domain, reasonDisplaySettings, nightOvertimeReflectAtr));
		}
	}

	private Map<String, Object> toObject(NtsResultRecord rec) {
		Map<String, Object> map = new HashMap<String, Object>();
		// KRQST_APPLICATION
		map.put("aCID", rec.getString("aCID"));
		map.put("aBASE_DATE_SET", rec.getInt("aBASE_DATE_SET"));
		map.put("aMON_ATD_CONFIRM_ATR", rec.getInt("aMON_ATD_CONFIRM_ATR"));
		map.put("aATD_LOCK_ATR", rec.getInt("aATD_LOCK_ATR"));
		map.put("aATD_CONFIRM_ATR", rec.getInt("aATD_CONFIRM_ATR"));
		map.put("aDAY_ATD_CONFIRM_ATR", rec.getInt("aDAY_ATD_CONFIRM_ATR"));
		map.put("aREASON_REQUIRE_ATR", rec.getInt("aREASON_REQUIRE_ATR"));
		map.put("aFIXED_REASON_REQUIRE_ATR", rec.getInt("aFIXED_REASON_REQUIRE_ATR"));
		map.put("aPRE_POST_DISPLAY_ATR", rec.getInt("aPRE_POST_DISPLAY_ATR"));
		map.put("aSEND_MAIL_INI_ATR", rec.getInt("aSEND_MAIL_INI_ATR"));
		map.put("aTIME_NIGHT_REFLECT_ATR", rec.getInt("aTIME_NIGHT_REFLECT_ATR"));
		// KRQST_APP_TYPE
		map.put("bCID", rec.getString("bCID"));
		map.put("bAPP_TYPE", rec.getInt("bAPP_TYPE"));
		map.put("bPRE_POST_INIT_ATR", rec.getInt("bPRE_POST_INIT_ATR"));
		map.put("bPRE_POST_CHANGE_ATR", rec.getInt("bPRE_POST_CHANGE_ATR"));
		map.put("bAPV_SEND_MAIL_ATR", rec.getInt("bAPV_SEND_MAIL_ATR"));
		map.put("bAPP_SEND_MAIL_ATR", rec.getInt("bAPP_SEND_MAIL_ATR"));
		map.put("bPOST_FUTURE_ALLOW_ATR", rec.getInt("bPOST_FUTURE_ALLOW_ATR"));
		map.put("bPRE_EARLY_DAYS", rec.getInt("bPRE_EARLY_DAYS"));
		map.put("bUSE_ATR", rec.getInt("bUSE_ATR"));
		map.put("bPRE_OT_CHECK_SET", rec.getInt("bPRE_OT_CHECK_SET"));
		map.put("bPRE_OT_BEF_WORK_TIME", rec.getInt("bPRE_OT_BEF_WORK_TIME"));
		map.put("bPRE_OT_AFT_WORK_TIME", rec.getInt("bPRE_OT_AFT_WORK_TIME"));
		map.put("bPRE_OT_BEF_AFT_WORK_TIME", rec.getInt("bPRE_OT_BEF_AFT_WORK_TIME"));
		// KRQST_APP_MCLOSE
		map.put("cCID", rec.getString("cCID"));
		map.put("cCLOSURE_ID", rec.getInt("cCLOSURE_ID"));
		map.put("cMCLOSE_CRITERIA_ATR", rec.getInt("cMCLOSE_CRITERIA_ATR"));
		map.put("cMCLOSE_DAYS", rec.getInt("cMCLOSE_DAYS"));
		map.put("cUSE_ATR", rec.getInt("cUSE_ATR"));
		// KRQST_REPRESENT_APP
		map.put("dCID", rec.getString("dCID"));
		map.put("dAPP_TYPE", rec.getInt("dAPP_TYPE"));
		map.put("dOPTION_ATR", rec.getInt("dOPTION_ATR"));
		return map;
	}
	
	private List<ApplicationSetting> convertToDomain(List<Map<String, Object>> mapLst) {
//		List<ApplicationSetting> result = mapLst.stream().collect(Collectors.groupingBy(x -> x.get("aCID"))).entrySet()
//			.stream().map(x -> {
//				List<AppDeadlineSetting> appDeadlineSetLst = x.getValue().stream().collect(Collectors.groupingBy(y -> y.get("cCLOSURE_ID"))).entrySet()
//					.stream().map(y -> {
//						AppDeadlineSetting appDeadlineSetting = AppDeadlineSetting.createNew(
//								(int) y.getValue().get(0).get("cUSE_ATR"),
//								(int) y.getValue().get(0).get("cCLOSURE_ID"),
//								(int) y.getValue().get(0).get("cMCLOSE_DAYS"),
//								(int) y.getValue().get(0).get("cMCLOSE_CRITERIA_ATR"));
//						return appDeadlineSetting;
//					}).collect(Collectors.toList());
//				ApplicationType dAppType = EnumAdaptor.valueOf((int) x.getValue().get(0).get("dAPP_TYPE"), ApplicationType.class);
//				ApplicationSetting applicationSetting = new ApplicationSetting(
//						(String) x.getValue().get(0).get("aCID"),
//						new AppLimitSetting(
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("aMON_ATD_CONFIRM_ATR")),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("aATD_LOCK_ATR")),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("aATD_CONFIRM_ATR")),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("aREASON_REQUIRE_ATR")),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("aFIXED_REASON_REQUIRE_ATR")),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("aDAY_ATD_CONFIRM_ATR"))),
//						AppTypeSetting.createNew(
//								(int) x.getValue().get(0).get("bAPP_TYPE"),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("bAPP_SEND_MAIL_ATR")),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("bAPV_SEND_MAIL_ATR")),
//								(int) x.getValue().get(0).get("bPRE_POST_INIT_ATR"),
//								BooleanUtils.toBoolean((int)x.getValue().get(0).get("bPRE_POST_CHANGE_ATR"))),
//						new AppSetForProxyApp(
//								dAppType,
//								dAppType == ApplicationType.OVER_TIME_APPLICATION
//									? Optional.of(EnumAdaptor.valueOf((int) x.getValue().get(0).get("dOPTION_ATR"), OvertimeAppAtr.class))
//									: Optional.empty(),
//								dAppType == ApplicationType.STAMP_APPLICATION
//									? Optional.of(EnumAdaptor.valueOf((int) x.getValue().get(0).get("dOPTION_ATR"), StampRequestMode.class))
//									: Optional.empty()),
//						appDeadlineSetLst,
//						AppDisplaySetting.createNew(
//								(int) x.getValue().get(0).get("aPRE_POST_DISPLAY_ATR"),
//								(int) x.getValue().get(0).get("aSEND_MAIL_INI_ATR")),
//						new ReceptionRestrictionSetting(
//								OTAppBeforeAccepRestric.createNew(
//										(int) x.getValue().get(0).get("bPRE_OT_CHECK_SET"),
//										(int) x.getValue().get(0).get("bPRE_EARLY_DAYS"),
//										BooleanUtils.toBoolean((int)x.getValue().get(0).get("bUSE_ATR")),
//										(Integer) x.getValue().get(0).get("bPRE_OT_BEF_WORK_TIME"),
//										(Integer) x.getValue().get(0).get("bPRE_OT_AFT_WORK_TIME"),
//										(Integer) x.getValue().get(0).get("bPRE_OT_BEF_AFT_WORK_TIME")),
//								new AfterhandRestriction(BooleanUtils.toBoolean((int)x.getValue().get(0).get("bPOST_FUTURE_ALLOW_ATR"))),
//								BeforehandRestriction.createNew(
//										(int) x.getValue().get(0).get("bPRE_EARLY_DAYS"),
//										BooleanUtils.toBoolean((int)x.getValue().get(0).get("bUSE_ATR"))),
//								EnumAdaptor.valueOf((int) x.getValue().get(0).get("bAPP_TYPE"), ApplicationType.class)),
//						EnumAdaptor.valueOf((int) x.getValue().get(0).get("aBASE_DATE_SET"), RecordDate.class));
//				return applicationSetting;
//			}).collect(Collectors.toList());
//		return result;
		return new ArrayList<>();
	}

}
