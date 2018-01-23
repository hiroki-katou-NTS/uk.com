package nts.uk.ctx.at.request.infra.repository.setting.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplace;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.request.infra.entity.setting.workplace.KrqstWpAppConfig;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRequestOfEarchWorkplaceRepository extends JpaRepository implements RequestOfEachWorkplaceRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstWpAppConfig c "
			+ "WHERE c.krqstWpAppConfigPK.companyId = :companyId "
			+ "AND c.krqstWpAppConfigPK.workplaceId = :workplaceId";

	/**
	 * get request of earch workplace by app type
	 */
	@Override
	public Optional<RequestOfEachWorkplace> getRequestByWorkplace(String companyId, String workplaceId) {
		return this.queryProxy()
				.query(FIND, KrqstWpAppConfig.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.getSingle(c -> c.toDomain());
	}
	
	@Override
	public Optional<ApprovalFunctionSetting> getFunctionSetting(String companyId, String workplaceId,
			Integer appType) {
		Optional<RequestOfEachWorkplace> requestOfEachWorkplace = this.queryProxy()
				.query(FIND, KrqstWpAppConfig.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.getSingle(c -> c.toDomain());
		if(!requestOfEachWorkplace.isPresent()){
			return Optional.empty();
		}
		return requestOfEachWorkplace.get().getListApprovalFunctionSetting()
				.stream().filter(x -> x.getAppUseSetting().getAppType().value == appType).findAny();
	}

}
