package nts.uk.ctx.at.request.infra.repository.setting.workplace.requestbycompany;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRequestByCompanyRepository extends JpaRepository implements RequestByCompanyRepository {

	@Override
	public Optional<ApprovalFunctionSet> findByAppType(String companyID, ApplicationType appType) {
		String sql = "select * from KRQMT_APP_APV_CMP where CID = @companyID and APP_TYPE = @appType";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.paramInt("appType", appType.value)
				.getSingle(rec -> {
					ApplicationUseSetting applicationUseSetting = ApplicationUseSetting.createNew(
							rec.getInt("USE_ATR"), 
							rec.getInt("APP_TYPE"), 
							rec.getString("MEMO"));
					return new ApprovalFunctionSet(Arrays.asList(applicationUseSetting));	
				});
	}

	@Override
	public Optional<ApprovalFunctionSet> findByCompanyID(String companyID) {
		String sql = "select * from KRQMT_APP_APV_CMP where CID = @companyID";
		
		List<ApplicationUseSetting> applicationUseSettingLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyID", companyID)
				.getList(rec -> {
					return ApplicationUseSetting.createNew(
							rec.getInt("USE_ATR"), 
							rec.getInt("APP_TYPE"), 
							rec.getString("MEMO"));
				});
		if(CollectionUtil.isEmpty(applicationUseSettingLst)) {
			return Optional.empty();
		}
		return Optional.of(new ApprovalFunctionSet(applicationUseSettingLst));
	}

}
