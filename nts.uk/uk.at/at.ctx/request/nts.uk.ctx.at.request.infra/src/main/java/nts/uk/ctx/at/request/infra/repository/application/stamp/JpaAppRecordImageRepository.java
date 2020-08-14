package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutAtr;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppSampNR;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakout.GoOutReasonAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaAppRecordImageRepository extends JpaRepository implements AppRecordImageRepository {
	
	
	public static final String FIND_BY_ID = "SELECT *\r\n" + 
			"  FROM KRQDT_APP_STAMP_NR WHERE CID = @cid AND APP_ID = @appId";
	
	@Override
	public Optional<AppRecordImage> findByAppID(String companyID, String appID) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
				.paramString("cid", companyID)
				.paramString("appId", appID)
				.getSingle(res -> toDomain(res));
	}

	@Override
	public void addStamp(AppRecordImage appRecordImage) {
		this.commandProxy().insert(toEntity(appRecordImage));
		this.getEntityManager().flush();

	}

	@Override
	public void updateStamp(AppRecordImage appStamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String companyID, String appID) {
		// TODO Auto-generated method stub

	}
	
	public KrqdtAppSampNR toEntity(AppRecordImage appRecordImage) {
		KrqdtAppSampNR krqdtAppSampNR = new KrqdtAppSampNR();
		krqdtAppSampNR.krqdtAppSampNRPk.companyID = AppContexts.user().companyId();
		krqdtAppSampNR.krqdtAppSampNRPk.appID = appRecordImage.getAppID();
		
		krqdtAppSampNR.stampAtr = appRecordImage.getAppStampCombinationAtr().value;
		krqdtAppSampNR.appTime = appRecordImage.getAttendanceTime().v();
		krqdtAppSampNR.goOutAtr = appRecordImage.getAppStampGoOutAtr().isPresent() ? appRecordImage.getAppStampGoOutAtr().get().value : null;
		
		return krqdtAppSampNR;		
	}
	
	public AppRecordImage toDomain(NtsResultRecord res) {
		Optional<GoOutReasonAtr> appStampGoOutAtr = Optional.empty();
		Integer gouOutAtr = res.getInt("GO_OUT_ATR");
		if (gouOutAtr != null) {
			
			Optional.of(EnumAdaptor.valueOf(gouOutAtr, AppStampGoOutAtr.class));
		}
		
		return new AppRecordImage(
				EnumAdaptor.valueOf(res.getInt("STAMP_ATR"), AppStampCombinationAtr.class),
				new AttendanceTime(res.getInt("APP_TIME")),
				appStampGoOutAtr);
	}

}
