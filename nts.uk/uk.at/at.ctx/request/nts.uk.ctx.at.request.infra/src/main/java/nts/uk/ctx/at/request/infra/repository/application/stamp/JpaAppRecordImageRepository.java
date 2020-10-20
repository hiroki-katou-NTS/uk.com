package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.stamp.EngraveAtr;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppSampNR;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppSampNRPk;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.AttendanceClock;

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
		KrqdtAppSampNR krqdtAppSampNR = toEntity(appStamp);
		Optional<KrqdtAppSampNR> krOptional = this.queryProxy().find(krqdtAppSampNR.krqdtAppSampNRPk, KrqdtAppSampNR.class);
		if (!krOptional.isPresent()) {
			
			return;
		}
		krOptional.get().stampAtr = krqdtAppSampNR.stampAtr;
		krOptional.get().appTime = krqdtAppSampNR.appTime;
		krOptional.get().goOutAtr = krqdtAppSampNR.goOutAtr;
		this.commandProxy().update(krOptional.get());
	}

	@Override
	public void delete(String companyID, String appID) {
		this.commandProxy().remove(KrqdtAppSampNR.class, new KrqdtAppSampNRPk(companyID, appID));
	}
	
	public KrqdtAppSampNR toEntity(AppRecordImage appRecordImage) {
		KrqdtAppSampNR krqdtAppSampNR = new KrqdtAppSampNR();
		krqdtAppSampNR.krqdtAppSampNRPk = new KrqdtAppSampNRPk();
		krqdtAppSampNR.krqdtAppSampNRPk.companyID = AppContexts.user().companyId();
		krqdtAppSampNR.krqdtAppSampNRPk.appID = appRecordImage.getAppID();
		
		krqdtAppSampNR.stampAtr = appRecordImage.getAppStampCombinationAtr().value;
		krqdtAppSampNR.appTime = appRecordImage.getAttendanceTime().v();
		krqdtAppSampNR.goOutAtr = appRecordImage.getAppStampGoOutAtr().isPresent() ? appRecordImage.getAppStampGoOutAtr().get().value : null;
		
		return krqdtAppSampNR;		
	}
	
	public AppRecordImage toDomain(NtsResultRecord res) {
		Optional<GoingOutReason> appStampGoOutAtr = Optional.empty();
		Integer gouOutAtr = res.getInt("GO_OUT_ATR");
		if (gouOutAtr != null) {
			appStampGoOutAtr = Optional.of(EnumAdaptor.valueOf(gouOutAtr, GoingOutReason.class));
		}
		
		return new AppRecordImage(
				EnumAdaptor.valueOf(res.getInt("STAMP_ATR"), EngraveAtr.class),
				new AttendanceClock(res.getInt("APP_TIME")),
				appStampGoOutAtr);
	}

}
