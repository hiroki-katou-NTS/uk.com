package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.PersonalBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstPersonalBPSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstPersonalBPSettingPK;

@Stateless
public class JpaPSBPSettingRepository extends JpaRepository implements PSBonusPaySettingRepository {

	private static final String SELECT_BY_LIST_ID = "SELECT c FROM KbpstPersonalBPSetting c WHERE c.kbpstPersonalBPSettingPK.employeeId IN :employeeIds";

	@Override
	public Optional<PersonalBonusPaySetting> getPersonalBonusPaySetting(String id) {
		Optional<KbpstPersonalBPSetting> entity = queryProxy().find(new KbpstPersonalBPSettingPK(id),
				KbpstPersonalBPSetting.class);

		if (entity.isPresent()) {
			return Optional.ofNullable(toDomain(entity.get()));
		}

		return Optional.empty();
	}

	@Override
	public List<PersonalBonusPaySetting> getListSetting(List<String> lstEmployeeId) {
		List<PersonalBonusPaySetting> resultList = new ArrayList<>();
		CollectionUtil.split(lstEmployeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(queryProxy().query(SELECT_BY_LIST_ID, KbpstPersonalBPSetting.class)
				.setParameter("employeeIds", lstEmployeeId)
				.getList(m -> toDomain(m)));
		});
		return resultList;
	}

	@Override
	public void addPBPSetting(PersonalBonusPaySetting domain) {
		Optional<PersonalBonusPaySetting> update = getPersonalBonusPaySetting(domain.getEmployeeId());
		if (update.isPresent()) {
			this.commandProxy().update(this.toEntity(domain));
		} else {
			this.commandProxy().insert(this.toEntity(domain));
		}
	}

	@Override
	public void updatePBPSetting(PersonalBonusPaySetting domain) {
		addPBPSetting(domain);
	}

	@Override
	public void removePBPSetting(PersonalBonusPaySetting domain) {
		Optional<PersonalBonusPaySetting> update = getPersonalBonusPaySetting(domain.getEmployeeId());
		if (update.isPresent()) {
			this.commandProxy().remove(KbpstPersonalBPSetting.class,
					new KbpstPersonalBPSettingPK(domain.getEmployeeId()));
		}
	}

	private KbpstPersonalBPSetting toEntity(PersonalBonusPaySetting domain) {
		return new KbpstPersonalBPSetting(new KbpstPersonalBPSettingPK(domain.getEmployeeId()),
				domain.getBonusPaySettingCode().v());
	}

	private PersonalBonusPaySetting toDomain(KbpstPersonalBPSetting entity) {
		return PersonalBonusPaySetting.createFromJavaType(entity.kbpstPersonalBPSettingPK.employeeId,
				entity.bonusPaySettingCode);
	}
}
