package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.CommonSettingsStampInputRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampFunction;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampWkpSelectRole;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 打刻入力の共通設定Repository
 * 
 * @author chungnt
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCommonSettingsStampInputRepository extends JpaRepository implements CommonSettingsStampInputRepository {
	
	
	private static final String SELECT_ALL_ROLES = "SELECT r FROM KrcmtStampWkpSelectRole r WHERE r.pk.companyId = :companyId";

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
		entity.update(domain);
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<CommonSettingsStampInput> get(String comppanyID) {
		Optional<KrcmtStampFunction> entityOpt = this.queryProxy().find(comppanyID, KrcmtStampFunction.class);
		if (!entityOpt.isPresent()) {
			
			return Optional.empty();
		}
		
		List<String> roles = this.queryProxy().query(SELECT_ALL_ROLES, KrcmtStampWkpSelectRole.class).getList().stream().map(r-> r.pk.roleId).collect(Collectors.toList());
		
		CommonSettingsStampInput domain = toDomain(entityOpt.get(), roles);
		
		return Optional.of(domain);
	}

	public KrcmtStampFunction toEntity(CommonSettingsStampInput domain) {
		KrcmtStampFunction entity = new KrcmtStampFunction();
		
		entity.cid = domain.getCompanyId();
		entity.googleMapUseArt = domain.isGooglemap() ? 1 : 0;
		StampResultDisplay display = new StampResultDisplay(AppContexts.user().companyId(),NotUseAtr.USE);
		entity.recordDisplayArt = display.getUsrAtr().value;
		entity.mapAddress = "";
		
		return entity;
	}
	
	public CommonSettingsStampInput toDomain(KrcmtStampFunction entity, List<String> roles) {
		return new CommonSettingsStampInput(entity.cid, roles, entity.googleMapUseArt == 1,entity.mapAddress);
	}

}
