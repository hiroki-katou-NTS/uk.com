package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.applatearrival;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequest;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationlatearrival.KrqstLateEarlyRequest;
import nts.uk.shr.com.context.AppContexts;

/**
 * Jpa Late Early Request Repository
 * TanLV
 *
 */
@Stateless
public class JpaLateEarReqRepository extends JpaRepository implements LateEarlyRequestRepository {

	/**
	 * Get data
	 */
	@Override
	public Optional<LateEarlyRequest> getLateEarlyRequest() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstLateEarlyRequest.class).map(x -> toDomain(x));
	}

	/**
	 * To Domain
	 * @param entity
	 * @return
	 */
	private static LateEarlyRequest toDomain(KrqstLateEarlyRequest entity) {
		LateEarlyRequest domain = LateEarlyRequest.createFromJavaType(
				entity.getCompanyId(), 
				entity.getShowResult());
		
		return domain;
	}

	/**
	 * Add
	 */
	@Override
	public void addLateEarlyRequest(LateEarlyRequest lateEarlyRequest) {
		KrqstLateEarlyRequest entity = toEntity(lateEarlyRequest);
		this.commandProxy().insert(entity);
	}

	/**
	 * To Entity
	 * @param lateEarlyRequest
	 * @return
	 */
	private static KrqstLateEarlyRequest toEntity(LateEarlyRequest lateEarlyRequest) {
		val entity = new KrqstLateEarlyRequest();
		entity.setCompanyId(lateEarlyRequest.getCompanyId());
		entity.setShowResult(lateEarlyRequest.getShowResult().value);
		
		return entity;
	}

	/**
	 * Update
	 */
	@Override
	public void updateLateEarlyRequest(LateEarlyRequest lateEarlyRequest) {
		KrqstLateEarlyRequest entity = toEntity(lateEarlyRequest);
		KrqstLateEarlyRequest oldEntity = this.queryProxy().find(entity.getCompanyId(), KrqstLateEarlyRequest.class).get();
		
		oldEntity.setCompanyId(entity.getCompanyId());
		oldEntity.setShowResult(entity.getShowResult());
		
		this.commandProxy().update(oldEntity);
	}
}
