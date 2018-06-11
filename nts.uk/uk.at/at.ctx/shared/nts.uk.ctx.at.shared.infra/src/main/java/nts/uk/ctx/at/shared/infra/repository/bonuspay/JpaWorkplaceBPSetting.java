package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySettingPK;

@Stateless
public class JpaWorkplaceBPSetting extends JpaRepository implements WPBonusPaySettingRepository {

	private static final String SELECT_BY_LIST_ID = "SELECT c FROM KbpstWPBonusPaySetting c WHERE c.kbpstWPBonusPaySettingPK.workplaceId IN :workplaceIds";

	@Override
	public List<WorkplaceBonusPaySetting> getListSetting(List<WorkplaceId> ids) {
		return queryProxy().query(SELECT_BY_LIST_ID, KbpstWPBonusPaySetting.class).setParameter("workplaceIds", ids)
				.getList(m -> toDomain(m));
	}

	@Override
	public void addWPBPSetting(WorkplaceBonusPaySetting domain) {
		Optional<WorkplaceBonusPaySetting> update = getWPBPSetting(new WorkplaceId(domain.getWorkplaceId()));

		if (!update.isPresent()) {
			this.commandProxy().insert(toEntity(domain));
		} else {
			this.commandProxy().update(toEntity(domain));
		}
	}

	@Override
	public void updateWPBPSetting(WorkplaceBonusPaySetting domain) {
		addWPBPSetting(domain);
	}

	@Override
	public void removeWPBPSetting(WorkplaceBonusPaySetting domain) {
		Optional<WorkplaceBonusPaySetting> deleteable = getWPBPSetting(new WorkplaceId(domain.getWorkplaceId()));

		if (deleteable.isPresent()) {
			this.commandProxy().remove(KbpstWPBonusPaySetting.class,
					new KbpstWPBonusPaySettingPK(domain.getWorkplaceId()));
		}
	}

	@Override
	public Optional<WorkplaceBonusPaySetting> getWPBPSetting(WorkplaceId id) {
		Optional<KbpstWPBonusPaySetting> entity = queryProxy().find(new KbpstWPBonusPaySettingPK(id.v()),
				KbpstWPBonusPaySetting.class);

		if (entity.isPresent()) {
			return Optional.ofNullable(toDomain(entity.get()));
		}

		return Optional.empty();
	}

	private KbpstWPBonusPaySetting toEntity(WorkplaceBonusPaySetting domain) {
		return new KbpstWPBonusPaySetting(new KbpstWPBonusPaySettingPK(domain.getWorkplaceId().toString()),
				domain.getBonusPaySettingCode().toString());
	}

	private WorkplaceBonusPaySetting toDomain(KbpstWPBonusPaySetting entity) {
		return WorkplaceBonusPaySetting.createFromJavaType(entity.kbpstWPBonusPaySettingPK.workplaceId,
				entity.bonusPaySettingCode);

	}
}
