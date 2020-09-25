package nts.uk.ctx.at.request.infra.repository.application.gobackdirectly;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectly;
import nts.uk.ctx.at.request.infra.entity.application.gobackdirectly.KrqdtGoBackDirectlyPK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author hoangnd
 *
 */
@Stateless
public class JpaGoBackDirectlyRepository extends JpaRepository implements GoBackDirectlyRepository {
	public static final String FIND_BY_ID = "SELECT *\r\n" + 
			"  FROM KRQDT_APP_GOBACK_DIRECTLY as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID WHERE a.APP_ID = @appId and a.CID = @companyId";

	@Override
	public Optional<GoBackDirectly> find(String companyId, String appId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
				.paramString("appId", appId)
				.paramString("companyId", companyId)
				.getSingle(res -> toDomain(res));
	}

	@Override
	public void add(GoBackDirectly domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(GoBackDirectly domain) {
		KrqdtGoBackDirectly krqdtGoBackDirectly = toEntity(domain);
		Optional<KrqdtGoBackDirectly> update = this.queryProxy().find(krqdtGoBackDirectly.krqdtGoBackDirectlyPK, KrqdtGoBackDirectly.class);
		if (!update.isPresent()) return;
		
		update.get().workTypeCD = krqdtGoBackDirectly.workTypeCD;
		update.get().workTimeCD = krqdtGoBackDirectly.workTimeCD;
		update.get().workChangeAtr = krqdtGoBackDirectly.workChangeAtr;
		update.get().goWorkAtr = krqdtGoBackDirectly.goWorkAtr;
		update.get().backHomeAtr = krqdtGoBackDirectly.backHomeAtr;
		this.commandProxy().update(update.get());
			
	}

	@Override
	public void remove(GoBackDirectly domain) {
		this.commandProxy().remove(KrqdtGoBackDirectly.class,
				new KrqdtGoBackDirectlyPK(AppContexts.user().companyId(), domain.getAppID()));

	}

	public GoBackDirectly toDomain(NtsResultRecord res) {
		// parse application domain
		Application application = new Application();
		String pattern = "yyyy/MM/dd HH:mm:ss";
		String pattern2 = "yyyy/MM/dd";
		DateFormat df = new SimpleDateFormat(pattern);
		DateFormat df2 = new SimpleDateFormat(pattern2);
		application.setAppID(res.getString("APP_ID"));
		application.setVersion(res.getInt("EXCLUS_VER"));
		application.setPrePostAtr(EnumAdaptor.valueOf(res.getInt("PRE_POST_ATR"), PrePostAtr.class));
		application.setInputDate(GeneralDateTime.fromString(df.format(res.getDate("INPUT_DATE")), pattern));
		application.setEnteredPersonID(res.getString("ENTERED_PERSON_SID"));
		if (res.getString("REASON_REVERSION") == null) {
			application.setOpReversionReason(Optional.ofNullable(null));
		}else {
			application.setOpReversionReason(Optional.ofNullable(new ReasonForReversion(res.getString("REASON_REVERSION"))));			
		}
		application.setAppDate(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2)));
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
		application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
		application.setEmployeeID(res.getString("APPLICANTS_SID"));
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
		
		
		GoBackDirectly goBackDirectly = new GoBackDirectly();
		goBackDirectly.setStraightDistinction(EnumAdaptor.valueOf(res.getInt("GO_WORK_ATR"), NotUseAtr.class));
		goBackDirectly.setStraightLine(EnumAdaptor.valueOf(res.getInt("BACK_HOME_ATR"), NotUseAtr.class));
		if (Optional.ofNullable(res.getInt("WORK_CHANGE_ATR")).isPresent()) {
			goBackDirectly.setIsChangedWork(
					Optional.of(EnumAdaptor.valueOf(res.getInt("WORK_CHANGE_ATR"), NotUseAtr.class)));
		}
		if (Optional.ofNullable(res.getString("WORK_TYPE_CD")).isPresent()
				|| Optional.ofNullable(res.getString("WORK_TIME_CD")).isPresent()) {
			WorkInformation dataWork = new WorkInformation(null, "");
			goBackDirectly.setDataWork(Optional.of(dataWork));
			if (Optional.ofNullable(res.getString("WORK_TYPE_CD")).isPresent()) {
				dataWork.setWorkTypeCode(new WorkTypeCode(res.getString("WORK_TYPE_CD")));
			}
			if (Optional.ofNullable(res.getString("WORK_TIME_CD")).isPresent()) {
				dataWork.setWorkTimeCode(new WorkTimeCode(res.getString("WORK_TIME_CD")));
			}
		}

		return goBackDirectly;
	}

	public KrqdtGoBackDirectly toEntity(GoBackDirectly domain) {
		KrqdtGoBackDirectly krqdtGoBackDirectly = new KrqdtGoBackDirectly();
		krqdtGoBackDirectly.krqdtGoBackDirectlyPK = new KrqdtGoBackDirectlyPK(AppContexts.user().companyId(),
				domain.getAppID());
		if (domain.getDataWork().isPresent()) {
			WorkInformation dataWork = domain.getDataWork().get();
			krqdtGoBackDirectly.workTypeCD = dataWork.getWorkTypeCode().v();
			if (dataWork.getWorkTimeCode() != null) {
				krqdtGoBackDirectly.workTimeCD = dataWork.getWorkTimeCode().v();
			}
		}
		if (domain.getIsChangedWork().isPresent()) {
			krqdtGoBackDirectly.workChangeAtr = domain.getIsChangedWork().get().value;			
		}
		krqdtGoBackDirectly.goWorkAtr = domain.getStraightDistinction().value;
		krqdtGoBackDirectly.backHomeAtr = domain.getStraightLine().value;
		krqdtGoBackDirectly.contractCd = AppContexts.user().contractCode();

		return krqdtGoBackDirectly;
	}

	@Override
	public void delete(String companyId, String appId) {
		this.commandProxy().remove(KrqdtGoBackDirectly.class,
				new KrqdtGoBackDirectlyPK(AppContexts.user().companyId(), appId));
		
	}

}
