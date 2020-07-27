package nts.uk.ctx.at.request.infra.repository.application.stamp;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
@Stateless
public class JpaAppStampRepository extends JpaRepository implements AppStampRepository{
	public static final String FIND_BY_APPID = "SELECT * FROM KRQDT_APP_STAMP WHERE CID = @cid and APP_ID = @appId";
	@Override
	public Optional<AppStamp> findByAppID(String companyID, String appID) {
		List<NtsResultRecord> listResult = new NtsStatement(FIND_BY_APPID, this.jdbcProxy())
		.paramString("cid", companyID)
		.paramString("appId", appID)
		.getList(res -> res);
		return toDomain(listResult);
				
	}

	@Override
	public void addStamp(AppStamp appStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStamp(AppStamp appStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String appID) {
		// TODO Auto-generated method stub
		
	}
	public Optional<AppStamp> toDomain(List<NtsResultRecord> listResult) {
		
		AppStamp appStamp = new AppStamp(null, null, null, null);
		
		return Optional.of(appStamp);
	}

}
