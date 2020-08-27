package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;


import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChange;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChangePk;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppWorkChangeRepository extends JpaRepository implements AppWorkChangeRepository {

	public static final String FIND_BY_ID = "SELECT *  "
			+ "FROM KRQDT_APP_WORK_CHANGE as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID"
			+ " WHERE a.APP_ID = @appID AND a.CID = @companyId";

	@Override
	public Optional<AppWorkChange> findbyID(String companyId, String appID) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
				.paramString("appID", appID)
				.paramString("companyId", companyId)
				.getSingle(res -> toDomain(res));
	}

	@Override
	public void add(AppWorkChange appWorkChange) {
		this.commandProxy().insert(toEntity(appWorkChange));

	}

	@Override
	public void update(AppWorkChange appWorkChange) {
		KrqdtAppWorkChange krqdtAppWorkChange = toEntity(appWorkChange);
		Optional<KrqdtAppWorkChange> updateWorkChange = this.queryProxy().find(krqdtAppWorkChange.appWorkChangePk, KrqdtAppWorkChange.class);
    	if (!updateWorkChange.isPresent()) {
			return;
		}
    	updateWorkChange.get().goWorkAtr = krqdtAppWorkChange.goWorkAtr;
    	updateWorkChange.get().backHomeAtr = krqdtAppWorkChange.backHomeAtr;
    	updateWorkChange.get().workTypeCd = krqdtAppWorkChange.workTypeCd;
    	updateWorkChange.get().workTimeCd = krqdtAppWorkChange.workTimeCd;
    	updateWorkChange.get().workTimeStart1 = krqdtAppWorkChange.workTimeStart1;
    	updateWorkChange.get().workTimeEnd1 = krqdtAppWorkChange.workTimeEnd1;
    	updateWorkChange.get().workTimeStart2 = krqdtAppWorkChange.workTimeStart2;
    	updateWorkChange.get().workTimeEnd2 = krqdtAppWorkChange.workTimeEnd2;
    	this.commandProxy().update(updateWorkChange.get());
	}

	@Override
	public void remove(String companyID, String appID) {
		this.commandProxy().remove(KrqdtAppWorkChange.class, new KrqdtAppWorkChangePk(companyID, appID)); 

	}

	private KrqdtAppWorkChange toEntity(AppWorkChange domain) {
		TimeZoneWithWorkNo timeZoneWithWorkNo1 = null;
		TimeZoneWithWorkNo timeZoneWithWorkNo2 = null;
		for (TimeZoneWithWorkNo item : domain.getTimeZoneWithWorkNoLst()) {
			if(item.getWorkNo().v() == 1) {
				timeZoneWithWorkNo1 = item;
			}else if (item.getWorkNo().v() == 2){
				timeZoneWithWorkNo2 = item;
			}
		}
		String contractCD = AppContexts.user().contractCode();
		
		return new KrqdtAppWorkChange(
				// do have value of companyID
				new KrqdtAppWorkChangePk(AppContexts.user().companyId(), domain.getAppID()),
				contractCD,
				domain.getOpWorkTypeCD().isPresent() ? domain.getOpWorkTypeCD().get().v() : null,
				domain.getOpWorkTimeCD().isPresent() ? domain.getOpWorkTimeCD().get().v() : null,
				domain.getStraightGo().value,
				domain.getStraightBack().value,
				timeZoneWithWorkNo1 == null ? null : timeZoneWithWorkNo1.getTimeZone().getStartTime().v(),
				timeZoneWithWorkNo1 == null ? null : timeZoneWithWorkNo1.getTimeZone().getEndTime().v(),
				timeZoneWithWorkNo2 == null ? null : timeZoneWithWorkNo2.getTimeZone().getStartTime().v(),
				timeZoneWithWorkNo2 == null ? null : timeZoneWithWorkNo2.getTimeZone().getEndTime().v());
	}
	public AppWorkChange toDomain(NtsResultRecord res) {
		String pattern = "yyyy/MM/dd HH:mm:ss";
		String pattern2 = "yyyy/MM/dd";
		DateFormat df = new SimpleDateFormat(pattern);
		DateFormat df2 = new SimpleDateFormat(pattern2);
		String appID = res.getString("APP_ID");
		Integer version = res.getInt("EXCLUS_VER");
		PrePostAtr prePostAtr = EnumAdaptor.valueOf(res.getInt("PRE_POST_ATR"), PrePostAtr.class);
		String enteredPerson = res.getString("ENTERED_PERSON_SID");
		GeneralDateTime inputDate = GeneralDateTime.fromString(df.format(res.getDate("INPUT_DATE")), pattern);
		ApplicationDate appDate = new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2));
		ApplicationType appType = EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class);
		String employeeID = res.getString("APPLICANTS_SID");
		Application application = new Application(version, appID, prePostAtr, employeeID, appType, appDate, enteredPerson, inputDate, null);
		if (res.getString("REASON_REVERSION") == null) {
			application.setOpReversionReason(Optional.ofNullable(null));
		}else {
			application.setOpReversionReason(Optional.ofNullable(new ReasonForReversion(res.getString("REASON_REVERSION"))));			
		}
//		application.setAppDate(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2)));
		if (res.getInt("FIXED_REASON") == null) {
			application.setOpAppStandardReasonCD(Optional.ofNullable(null));
		}else {
			application.setOpAppStandardReasonCD(Optional.ofNullable(new AppStandardReasonCode(res.getInt("FIXED_REASON"))));			
		}
		if (res.getString("APP_REASON") == null) {
			application.setOpAppReason(Optional.ofNullable(null));
		}else {
			application.setOpAppReason(Optional.ofNullable(new AppReason(res.getString("APP_REASON"))));			
		}
//		application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
//		application.setEmployeeID(res.getString("APPLICANTS_SID"));
		if (res.getDate("APP_START_DATE") == null) {
			application.setOpAppStartDate(Optional.ofNullable(null));
		}else {
			application.setOpAppStartDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_START_DATE")), pattern2))));
		}
		if (res.getDate("APP_END_DATE") == null) {
			application.setOpAppEndDate(Optional.ofNullable(null));
		} else {
			application.setOpAppEndDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_END_DATE")), pattern2))));
		}
		
		if (res.getInt("STAMP_OPTION_ATR") == null) {
			application.setOpStampRequestMode(Optional.ofNullable(null));
		}else {
			application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(res.getInt("STAMP_OPTION_ATR"), StampRequestMode.class)));
		}
		AppWorkChange appWorkChange = new AppWorkChange(application);
		appWorkChange.setStraightGo(EnumAdaptor.valueOf(res.getInt("GO_WORK_ATR"), NotUseAtr.class));
		appWorkChange.setStraightBack(EnumAdaptor.valueOf(res.getInt("BACK_HOME_ATR"), NotUseAtr.class));
		if (res.getString("WORK_TYPE_CD") == null) {
			appWorkChange.setOpWorkTypeCD(Optional.empty());
		}else {
			appWorkChange.setOpWorkTypeCD(Optional.of(new WorkTypeCode(res.getString("WORK_TYPE_CD"))));
		}
		
		if (res.getString("WORK_TIME_CD") == null) {
			appWorkChange.setOpWorkTimeCD(Optional.empty());
		}else {
			appWorkChange.setOpWorkTimeCD(Optional.of(new WorkTimeCode(res.getString("WORK_TIME_CD"))));
		}
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = new ArrayList<TimeZoneWithWorkNo>();
		if (res.getInt("WORK_TIME_START1") != null && res.getInt("WORK_TIME_END1") != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo1 = new TimeZoneWithWorkNo(1, res.getInt("WORK_TIME_START1"), res.getInt("WORK_TIME_END1"));
			timeZoneWithWorkNoLst.add(timeZoneWithWorkNo1);
			
		}
		if (res.getInt("WORK_TIME_START2") != null && res.getInt("WORK_TIME_END2") != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo2 = new TimeZoneWithWorkNo(2, res.getInt("WORK_TIME_START2"), res.getInt("WORK_TIME_END2"));
			timeZoneWithWorkNoLst.add(timeZoneWithWorkNo2);			
		}
		if (timeZoneWithWorkNoLst.isEmpty()) {
			timeZoneWithWorkNoLst = Collections.emptyList();
		}
		
		appWorkChange.setTimeZoneWithWorkNoLst(timeZoneWithWorkNoLst);
		
		
		return appWorkChange;
	}

}
