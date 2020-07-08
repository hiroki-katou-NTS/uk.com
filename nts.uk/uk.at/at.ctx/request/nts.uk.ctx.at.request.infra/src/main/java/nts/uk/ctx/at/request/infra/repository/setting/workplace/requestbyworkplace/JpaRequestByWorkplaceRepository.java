package nts.uk.ctx.at.request.infra.repository.setting.workplace.requestbyworkplace;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRequestByWorkplaceRepository extends JpaRepository implements RequestByWorkplaceRepository {

	@Override
	public Optional<ApprovalFunctionSet> findByWkpAndAppType(String companyID, String workplaceID,
			ApplicationType appType) {
		String sql = "select * from KRQST_APP_APV_WKP where CID = @companyID and WKP_ID = @wkpID and APP_TYPE = @appType";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramString("wkpID", workplaceID)
				.paramInt("appType", appType.value)
				.getSingle(rec -> {
					ApplicationUseSetting applicationUseSetting = ApplicationUseSetting.createNew(
							rec.getInt("USE_ATR"), 
							rec.getInt("APP_TYPE"), 
							rec.getString("MEMO"));
					return new ApprovalFunctionSet(Arrays.asList(applicationUseSetting));	
				});
	}

}
