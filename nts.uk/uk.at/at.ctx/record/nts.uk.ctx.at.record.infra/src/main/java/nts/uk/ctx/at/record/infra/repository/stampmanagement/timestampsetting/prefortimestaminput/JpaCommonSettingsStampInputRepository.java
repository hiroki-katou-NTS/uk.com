package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.dom.stamp.application.MapAddress;
import nts.uk.ctx.at.record.infra.entity.stamp.application.KrcmtStampFunction;

/**
 * 打刻入力の共通設定Repository
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCommonSettingsStampInputRepository extends JpaRepository implements CommonSettingsStampInputRepository {
	
	@Override
	public void insert(CommonSettingsStampInput domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(CommonSettingsStampInput domain) {
		Optional<KrcmtStampFunction> entityOpt = this.queryProxy().find(domain.getCompanyId(),
				KrcmtStampFunction.class);

		if (!entityOpt.isPresent()) {
			
			return;
		}
		
		KrcmtStampFunction entity =  entityOpt.get();
		
		Optional<KrcmtStampFunction> display = this.queryProxy().find(domain.getCompanyId(), KrcmtStampFunction.class);
		
		entity.update(domain, display.isPresent()? Optional.of(display.get().toDomain()): Optional.empty());
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<CommonSettingsStampInput> get(String comppanyID) {
		Optional<KrcmtStampFunction> entityOpt = this.queryProxy().find(comppanyID, KrcmtStampFunction.class);
		if (!entityOpt.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(toDomain(entityOpt.get()));
	}

	public CommonSettingsStampInput toDomain(KrcmtStampFunction entity) {
		return new CommonSettingsStampInput(entity.cid, entity.googleMapUseArt == 1,
				entity.mapAddress == null ? Optional.empty() : Optional.of(new MapAddress(entity.mapAddress)), null);
	}
	
	private Optional<KrcmtStampFunction> getEntity(String cId){
		return this.queryProxy().find(cId, KrcmtStampFunction.class);
	}
	
	public KrcmtStampFunction toEntity(CommonSettingsStampInput domain){
		Optional<KrcmtStampFunction> oldEntity = this.getEntity(domain.getCompanyId());
		return new KrcmtStampFunction(
				domain.getCompanyId(), 
				oldEntity.isPresent()? oldEntity.get().recordDisplayArt : 0, 
				domain.isGooglemap() ? 1 : 0,
				domain.getMapAddres().isPresent() ? domain.getMapAddres().get().v() : null,
				oldEntity.isPresent()? oldEntity.get().lstRecordDis : new ArrayList<>());
	}

}
