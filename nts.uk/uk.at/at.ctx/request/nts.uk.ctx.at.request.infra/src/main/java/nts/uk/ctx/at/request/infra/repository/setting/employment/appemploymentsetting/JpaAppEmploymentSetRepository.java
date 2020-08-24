package nts.uk.ctx.at.request.infra.repository.setting.employment.appemploymentsetting;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.TargetWorkTypeByApp;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppEmploymentSetRepository extends JpaRepository implements AppEmploymentSetRepository {

	@Override
	public Optional<AppEmploymentSet> findByCompanyIDAndEmploymentCD(String companyID, String employmentCD) {
		String sql = "select a.CID as aCID, a.EMPLOYMENT_CODE as aEMPLOYMENT_CODE, a.APP_TYPE as aAPP_TYPE, " +
					"a.HOLIDAY_OR_PAUSE_TYPE as aHOLIDAY_OR_PAUSE_TYPE, a.HOLIDAY_TYPE_USE_FLG as aHOLIDAY_TYPE_USE_FLG, a.DISPLAY_FLAG as aDISPLAY_FLAG, " +
					"b.CID as bCID, b.EMPLOYMENT_CODE as bEMPLOYMENT_CODE, b.APP_TYPE as bAPP_TYPE, " +
					"b.HOLIDAY_OR_PAUSE_TYPE as bHOLIDAY_OR_PAUSE_TYPE, b.WORK_TYPE_CODE as bWORK_TYPE_CODE " +
					"from KRQMT_APP_APV_EMP a left join KRQMT_APP_WORKTYPE_EMP b " +
					"on a.CID = b.CID and a.EMPLOYMENT_CODE = b.EMPLOYMENT_CODE and a.APP_TYPE = b.APP_TYPE " +
					"where a.CID = @companyID and a.EMPLOYMENT_CODE = @employmentCD";
		List<Map<String, Object>> mapLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramString("employmentCD", employmentCD)
				.getList(rec -> toObject(rec));
		List<AppEmploymentSet> appEmploymentSetLst = convertToDomain(mapLst);
		if(CollectionUtil.isEmpty(appEmploymentSetLst)) {
			return Optional.empty();
		}
		return Optional.of(appEmploymentSetLst.get(0));
	}
	
	private Map<String, Object> toObject(NtsResultRecord rec) {
		Map<String, Object> map = new HashMap<String, Object>();
		// KRQMT_APP_APV_EMP
		map.put("aCID", rec.getString("aCID"));
		map.put("aEMPLOYMENT_CODE", rec.getString("aEMPLOYMENT_CODE"));
		map.put("aAPP_TYPE", rec.getInt("aAPP_TYPE"));
		map.put("aHOLIDAY_OR_PAUSE_TYPE", rec.getInt("aHOLIDAY_OR_PAUSE_TYPE"));
		map.put("aHOLIDAY_TYPE_USE_FLG", rec.getInt("aHOLIDAY_TYPE_USE_FLG"));
		map.put("aDISPLAY_FLAG", rec.getInt("aDISPLAY_FLAG"));
		// KRQMT_APP_WORKTYPE_EMP
		map.put("bCID", rec.getString("bCID"));
		map.put("bEMPLOYMENT_CODE", rec.getString("bEMPLOYMENT_CODE"));
		map.put("bAPP_TYPE", rec.getInt("bAPP_TYPE"));
		map.put("bHOLIDAY_OR_PAUSE_TYPE", rec.getInt("bHOLIDAY_OR_PAUSE_TYPE"));
		map.put("bWORK_TYPE_CODE", rec.getString("bWORK_TYPE_CODE"));
		return map;
	}
	
	private List<AppEmploymentSet> convertToDomain(List<Map<String, Object>> mapLst) {
		List<AppEmploymentSet> appEmploymentSetLst = mapLst.stream().collect(Collectors.groupingBy(x -> x.get("aEMPLOYMENT_CODE"))).entrySet()
			.stream().map(x -> {
				List<TargetWorkTypeByApp> targetWorkTypeByAppLst = x.getValue().stream().collect(Collectors.groupingBy(y -> y.get("aAPP_TYPE"))).entrySet()
					.stream().map(y -> {
						List<String> workTypeCDLst = y.getValue().stream().map(z -> (String) z.get("bWORK_TYPE_CODE")).collect(Collectors.toList());
						ApplicationType appType = EnumAdaptor.valueOf((int) y.getValue().get(0).get("aAPP_TYPE"), ApplicationType.class);
						TargetWorkTypeByApp targetWorkTypeByApp = TargetWorkTypeByApp.createNew(
								appType.value, 
								(int) y.getValue().get(0).get("aDISPLAY_FLAG") == 0 ? false : true, 
								workTypeCDLst, 
								appType == ApplicationType.COMPLEMENT_LEAVE_APPLICATION ? (int) y.getValue().get(0).get("aHOLIDAY_OR_PAUSE_TYPE") : null, 
								(Integer) y.getValue().get(0).get("aHOLIDAY_TYPE_USE_FLG") == null 
									? null
									: (int) y.getValue().get(0).get("aHOLIDAY_TYPE_USE_FLG") == 0 ? true : false, 
								appType == ApplicationType.ABSENCE_APPLICATION ? (int) y.getValue().get(0).get("aHOLIDAY_OR_PAUSE_TYPE") : null, 
								appType == ApplicationType.BUSINESS_TRIP_APPLICATION ? (int) y.getValue().get(0).get("aHOLIDAY_OR_PAUSE_TYPE") : null);
						return targetWorkTypeByApp;
					}).collect(Collectors.toList());
				return new AppEmploymentSet(
						(String) x.getValue().get(0).get("aCID"), 
						(String) x.getValue().get(0).get("aEMPLOYMENT_CODE"), 
						targetWorkTypeByAppLst);
			}).collect(Collectors.toList());
		return appEmploymentSetLst;
	}

}
