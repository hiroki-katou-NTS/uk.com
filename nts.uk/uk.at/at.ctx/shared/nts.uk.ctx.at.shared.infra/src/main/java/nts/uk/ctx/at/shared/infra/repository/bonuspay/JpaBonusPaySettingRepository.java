package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBonusPaySettingPK;

@Stateless
public class JpaBonusPaySettingRepository extends JpaRepository implements BPSettingRepository {
	private final String SELECT_BY_COMPANYID = "SELECT c FROM KbpmtBonusPaySetting c WHERE c.kbpmtBonusPaySettingPK.companyId = :companyId";

	@Override
	public List<BonusPaySetting> getAllBonusPaySetting(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANYID, KbpmtBonusPaySetting.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPaySettingDomain(x));
	}

	@Override
	public void addBonusPaySetting(BonusPaySetting domain) {
		this.commandProxy().insert(this.toBonusPaySettingEntity(domain));
	}

	@Override
	public void updateBonusPaySetting(BonusPaySetting domain) {
		this.commandProxy().update(this.toBonusPaySettingEntity(domain));

	}

	@Override
	public void removeBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		Optional<KbpmtBonusPaySetting> kbpmtBonusPaySetting = this.queryProxy()
				.find(new KbpmtBonusPaySettingPK(companyId, bonusPaySettingCode.v()), KbpmtBonusPaySetting.class);
		this.commandProxy().remove(kbpmtBonusPaySetting.get());
	}

	private BonusPaySetting toBonusPaySettingDomain(KbpmtBonusPaySetting kbpmtBonusPaySetting) {
		return BonusPaySetting.createFromJavaType(kbpmtBonusPaySetting.kbpmtBonusPaySettingPK.companyId,
				kbpmtBonusPaySetting.kbpmtBonusPaySettingPK.code, kbpmtBonusPaySetting.name, new ArrayList<BonusPayTimesheet>(), new ArrayList<SpecBonusPayTimesheet>());
	}

	private KbpmtBonusPaySetting toBonusPaySettingEntity(BonusPaySetting bonusPaySetting) {
		return new KbpmtBonusPaySetting(
				new KbpmtBonusPaySettingPK(bonusPaySetting.getCompanyId().toString(),
						bonusPaySetting.getCode().toString()),
				bonusPaySetting.getName().toString());
	}

}
