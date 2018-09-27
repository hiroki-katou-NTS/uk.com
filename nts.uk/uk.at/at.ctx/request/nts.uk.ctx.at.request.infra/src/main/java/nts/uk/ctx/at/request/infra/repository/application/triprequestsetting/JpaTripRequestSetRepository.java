package nts.uk.ctx.at.request.infra.repository.application.triprequestsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.TripRequestSetRepository;
import nts.uk.ctx.at.request.infra.entity.application.triprequestsetting.KrqstTripRequestSet;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaTripRequestSetRepository extends JpaRepository implements TripRequestSetRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static TripRequestSet toDomain(KrqstTripRequestSet entity){
		TripRequestSet domain = TripRequestSet.createFromJavaType(entity.companyId, 
				entity.comment1, entity.color1, entity.weight1, entity.comment2, entity.color2, 
				entity.weight2, entity.workType, entity.workChange, entity.workChangeTime, 
				entity.contractCheck, entity.lateLeave);
		return domain;
	}
	private static KrqstTripRequestSet toEntity(TripRequestSet domain){
		val entity = new KrqstTripRequestSet();
		entity.companyId = domain.getCompanyId();
		entity.color1 = domain.getColor1().v();
		entity.color2 = domain.getColor2().v();
		entity.comment1 = domain.getComment1().isPresent() ? domain.getComment1().get().v() : null;
		entity.comment2 = domain.getComment2().isPresent() ? domain.getComment2().get().v() : null;
		entity.contractCheck = domain.getContractCheck().value;
		entity.lateLeave = domain.getLateLeave().value;
		entity.weight1 = domain.getWeight1().value;
		entity.weight2 = domain.getWeight2().value;
		entity.workChange = domain.getWorkChange().value;
		entity.workType = domain.getWorkType().value;
		entity.workChangeTime = domain.getWorkChangeTime().value;
		return entity;
	}
	/**
	 * get trip request set
	 * @return
	 * @author yennth
	 */
	@Override
	public Optional<TripRequestSet> findByCid() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstTripRequestSet.class).map(x -> toDomain(x));
	}
	/**
	 * update trip request set
	 * @param tripRequest
	 * @author yennth
	 */
	@Override
	public void update(TripRequestSet tripRequest) {
		KrqstTripRequestSet entity = toEntity(tripRequest);
		KrqstTripRequestSet oldEntity = this.queryProxy().find(entity.companyId, KrqstTripRequestSet.class).get();
		oldEntity.color1 = entity.color1;
		oldEntity.color2 = entity.color2;
		oldEntity.comment1 = entity.comment1;
		oldEntity.comment2 = entity.comment2;
		oldEntity.contractCheck = entity.contractCheck;
		oldEntity.lateLeave = entity.lateLeave;
		oldEntity.weight1 = entity.weight1;
		oldEntity.weight2 = entity.weight2;
		oldEntity.workChange = entity.workChange;
		oldEntity.workChangeTime = entity.workChangeTime;
		oldEntity.workType = entity.workType;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert trip request set
	 * @param tripRequest
	 * @author yennth 
	 */
	@Override
	public void insert(TripRequestSet tripRequest) {
		KrqstTripRequestSet entity = toEntity(tripRequest);
		this.commandProxy().insert(entity);
	}
}
