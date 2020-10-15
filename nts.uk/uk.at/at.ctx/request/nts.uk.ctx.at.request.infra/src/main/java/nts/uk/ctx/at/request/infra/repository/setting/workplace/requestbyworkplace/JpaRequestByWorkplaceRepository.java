package nts.uk.ctx.at.request.infra.repository.setting.workplace.requestbyworkplace;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplace;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;
import nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbyworkplace.KrqmtAppApvWkp;

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
		String sql = "select * from KRQMT_APP_APV_WKP where CID = @companyID and WKP_ID = @wkpID and APP_TYPE = @appType";
		
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

	@Override
	public List<RequestByWorkplace> findByCompany(String companyId) {
		List<KrqmtAppApvWkp> entities = this.queryProxy().query("select a from KrqmtAppApvWkp a where a.pk.companyId = :companyId", KrqmtAppApvWkp.class)
				.setParameter("companyId", companyId)
				.getList();
		Map<String, List<KrqmtAppApvWkp>> mapEntity = entities.stream().collect(Collectors.groupingBy(e -> e.getPk().workplaceId));
		return mapEntity.values().stream().map(KrqmtAppApvWkp::toDomain).collect(Collectors.toList());
	}

	@Override
	public Optional<RequestByWorkplace> findByCompanyAndWorkplace(String companyId, String workplaceId) {
		return Optional.ofNullable(
				KrqmtAppApvWkp.toDomain(
						this.queryProxy().query("select a from KrqmtAppApvWkp a where a.pk.companyId = :companyId and a.pk.workplaceId = :workplaceId", KrqmtAppApvWkp.class)
								.setParameter("companyId", companyId)
								.setParameter("workplaceId", workplaceId)
								.getList()
				)
		);
	}

	@Override
	public void save(RequestByWorkplace domain) {
		List<KrqmtAppApvWkp> entities = this.queryProxy().query("select a from KrqmtAppApvWkp a where a.pk.companyId = :companyId and a.pk.workplaceId = :workplaceId", KrqmtAppApvWkp.class)
				.setParameter("companyId", domain.getCompanyID())
				.setParameter("workplaceId", domain.getWorkplaceID())
				.getList();
		domain.getApprovalFunctionSet().getAppUseSetLst().forEach(setting -> {
			Optional<KrqmtAppApvWkp> optEntity = entities.stream().filter(e -> e.getPk().appType == setting.getAppType().value).findFirst();
			if (optEntity.isPresent()) {
				KrqmtAppApvWkp entity = optEntity.get();
				entity.update(setting);
				this.commandProxy().update(entity);
			} else {
				this.commandProxy().insert(KrqmtAppApvWkp.fromDomain(domain.getCompanyID(), domain.getWorkplaceID(), setting));
			}
		});
	}

	@Override
	public void delete(String companyId, String workplaceId) {
		List<KrqmtAppApvWkp> entities = this.queryProxy().query("select a from KrqmtAppApvWkp a where a.pk.companyId = :companyId and a.pk.workplaceId = :workplaceId", KrqmtAppApvWkp.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.getList();
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
	}

}
