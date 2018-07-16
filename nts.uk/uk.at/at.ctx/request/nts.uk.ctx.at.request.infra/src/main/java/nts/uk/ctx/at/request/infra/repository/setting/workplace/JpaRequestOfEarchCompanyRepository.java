package nts.uk.ctx.at.request.infra.repository.setting.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.infra.entity.setting.workplace.KrqstComAppConfig;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaRequestOfEarchCompanyRepository extends JpaRepository implements RequestOfEachCompanyRepository {

	private static final String FIND = "SELECT c " + "FROM KrqstComAppConfig c " + "WHERE c.companyId = :companyId ";

	@Override
	public Optional<RequestOfEachCompany> getRequestByCompany(String companyId) {
		return this.queryProxy().query(FIND, KrqstComAppConfig.class).setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<ApprovalFunctionSetting> getFunctionSetting(String companyId, Integer appType) {
		Optional<RequestOfEachCompany> requestOfEachCompany = this.queryProxy().query(FIND, KrqstComAppConfig.class)
				.setParameter("companyId", companyId).getSingle(c -> c.toDomain());
		if (!requestOfEachCompany.isPresent()) {
			return Optional.empty();
		}
		return requestOfEachCompany.get().getListApprovalFunctionSetting().stream()
				.filter(x -> x.getAppUseSetting().getAppType().value == appType).findAny();
	}

	@Override
	public void updateRequestOfEachCompany(RequestOfEachCompany company) {
		KrqstComAppConfig entity = KrqstComAppConfig.toEntity(company);
		Optional<KrqstComAppConfig> oldEntity = this.queryProxy().find(entity.companyId, KrqstComAppConfig.class);

		oldEntity.ifPresent(_entity -> {
			_entity.selectOfApproversFlg = entity.selectOfApproversFlg;
			_entity.krqstComAppConfigDetails = entity.krqstComAppConfigDetails;
			
			this.commandProxy().update(_entity);
		});
	}

	@Override
	public void insertRequestOfEachCompany(RequestOfEachCompany company) {
		KrqstComAppConfig entity = KrqstComAppConfig.toEntity(company);
		this.commandProxy().insert(entity);

	}

}
