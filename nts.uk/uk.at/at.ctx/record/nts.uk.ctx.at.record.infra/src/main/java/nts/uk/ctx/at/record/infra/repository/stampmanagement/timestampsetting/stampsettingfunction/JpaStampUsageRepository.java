package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.stampsettingfunction;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossingRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.KrcmtStampUsage;

@Stateless
public class JpaStampUsageRepository extends JpaRepository implements SettingsUsingEmbossingRepository {

	@Override
	public void insert(SettingsUsingEmbossing domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	@Override
	public void update(SettingsUsingEmbossing domain) {
		Optional<KrcmtStampUsage> entityOpt = this.queryProxy().find(domain.getCompanyId(),
				KrcmtStampUsage.class);

		if (!entityOpt.isPresent()) {
			
			return;
		}
		
		KrcmtStampUsage entity =  entityOpt.get();
		entity.update(domain);
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<SettingsUsingEmbossing> get(String comppanyID) {
		Optional<KrcmtStampUsage> entityOpt = this.queryProxy().find(comppanyID, KrcmtStampUsage.class);
		if (!entityOpt.isPresent()) {
			
			return Optional.empty();
		}
		
		SettingsUsingEmbossing domain = toDoamin(entityOpt.get());

		return Optional.of(domain);
	}

	public KrcmtStampUsage toEntity(SettingsUsingEmbossing domain) {
		KrcmtStampUsage entity = new KrcmtStampUsage();
		
		entity.companyId = domain.getCompanyId();
		entity.nameSelection = domain.isName_selection() ? 1 : 0;
		entity.fingerAuthentication = domain.isFinger_authc() ? 1 : 0;
		entity.ICCardStamp = domain.isIc_card() ? 1 : 0;
		entity.personStamp = domain.isIndivition() ? 1 : 0;
		entity.portalStamp = domain.isPortal() ? 1 : 0;
		entity.smartPhoneStamp = domain.isSmart_phone() ? 1 : 0;
		
		return entity;
	}
	
	public SettingsUsingEmbossing toDoamin(KrcmtStampUsage entity) {
		return new SettingsUsingEmbossing(entity.companyId,
				entity.nameSelection == 1 ? true : false,
				entity.fingerAuthentication == 1 ? true : false, entity.ICCardStamp == 1 ? true : false,
				entity.personStamp == 1 ? true : false, entity.portalStamp == 1 ? true : false,
				entity.smartPhoneStamp == 1 ? true : false);
	}

}
