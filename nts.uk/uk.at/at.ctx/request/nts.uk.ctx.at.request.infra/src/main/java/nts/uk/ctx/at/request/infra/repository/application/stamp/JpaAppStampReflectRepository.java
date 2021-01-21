package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampReflectRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.AppStampReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaAppStampReflectRepository extends JpaRepository implements AppStampReflectRepository {
	public static final String FIND_BY_ID = "SELECT *\r\n" + 
			"  FROM KRQMT_APP_STAMP WHERE CID = @cid";
	@Override
	public Optional<AppStampReflect> findByAppID(String companyID) {
		
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
				.paramString("cid", companyID)
				.getSingle(res -> toDomain(res));
	}

	@Override
	public void addStamp(AppStampReflect appStamp) {
		
	}

	@Override
	public void updateStamp(AppStampReflect appStamp) {
		
	}

	@Override
	public void delete(String companyID, String appID) {
		
	}
	
	public AppStampReflect toDomain(NtsResultRecord res) {
		return new AppStampReflect(
				EnumAdaptor.valueOf(res.getInt("CARE_TIME_REFLECT_ATR"), NotUseAtr.class),
				EnumAdaptor.valueOf(res.getInt("BREAK_TIME_REFLECT_ATR"), NotUseAtr.class),
				res.getString("CID"),
				EnumAdaptor.valueOf(res.getInt("WORK_TIME_REFLECT_ATR"), NotUseAtr.class),
				EnumAdaptor.valueOf(res.getInt("GOOUT_TIME_REFLECT_ATR"), NotUseAtr.class),
				EnumAdaptor.valueOf(res.getInt("SUP_TIME_REFLECT_ATR"), NotUseAtr.class),
				EnumAdaptor.valueOf(res.getInt("CHILD_CARE_TIME_REFLECT_ATR"), NotUseAtr.class),
				EnumAdaptor.valueOf(res.getInt("EXTRA_WORK_TIME_REFLECT_ATR"), NotUseAtr.class));	
	}
	

}
