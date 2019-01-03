package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstWPBonusPaySettingPK;

@Stateless
public class JpaWorkplaceBPSetting extends JpaRepository implements WPBonusPaySettingRepository {

	private static final String SELECT_BY_LIST_ID = "SELECT c FROM KbpstWPBonusPaySetting c WHERE c.kbpstWPBonusPaySettingPK.workplaceId IN :workplaceIds AND c.kbpstWPBonusPaySettingPK.companyId = :companyId";

	@Override
	public List<WorkplaceBonusPaySetting> getListSetting(String companyId, List<WorkplaceId> ids) {
		List<WorkplaceBonusPaySetting> resultList = new ArrayList<>();
		CollectionUtil.split(ids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(queryProxy().query(SELECT_BY_LIST_ID, KbpstWPBonusPaySetting.class)
										  .setParameter("companyId", companyId)
										  .setParameter("workplaceIds", subList)
										  .getList(m -> toDomain(m)));
		});
		return resultList;
	}

	@Override
	public void addWPBPSetting(WorkplaceBonusPaySetting domain) {
		Optional<WorkplaceBonusPaySetting> update = getWPBPSetting(domain.getCompanyId(), new WorkplaceId(domain.getWorkplaceId()));

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
		Optional<WorkplaceBonusPaySetting> deleteable = getWPBPSetting(domain.getCompanyId(), new WorkplaceId(domain.getWorkplaceId()));

		if (deleteable.isPresent()) {
			this.commandProxy().remove(KbpstWPBonusPaySetting.class,
					new KbpstWPBonusPaySettingPK(domain.getWorkplaceId(), domain.getCompanyId()));
		}
	}

	@Override
	public Optional<WorkplaceBonusPaySetting> getWPBPSetting(String companyId, WorkplaceId id) {
		Optional<KbpstWPBonusPaySetting> entity = queryProxy().find(new KbpstWPBonusPaySettingPK(id.v(), companyId),
				KbpstWPBonusPaySetting.class);

		if (entity.isPresent()) {
			return Optional.ofNullable(toDomain(entity.get()));
		}

		return Optional.empty();
	}

	private KbpstWPBonusPaySetting toEntity(WorkplaceBonusPaySetting domain) {
		return new KbpstWPBonusPaySetting(new KbpstWPBonusPaySettingPK(domain.getWorkplaceId().toString(), domain.getCompanyId()),
				domain.getBonusPaySettingCode().toString());
	}

	private WorkplaceBonusPaySetting toDomain(KbpstWPBonusPaySetting entity) {
		return WorkplaceBonusPaySetting.createFromJavaType(entity.kbpstWPBonusPaySettingPK.companyId, entity.kbpstWPBonusPaySettingPK.workplaceId,
				entity.bonusPaySettingCode);

	}
}
