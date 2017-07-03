package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.CompanyBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstCompanyBPSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstCompanyBPSettingPK;

@Stateless
public class JpaCompanyBPSettingRepository extends JpaRepository implements CPBonusPaySettingRepository {

	private final String SELECT_BY_COMPANYID = "SELECT c FROM KbpstCompanyBPSetting c WHERE c.kbpstCompanyBPSettingPK.companyId = :companyId";
	@Override
	public Optional<CompanyBonusPaySetting> getSetting(String companyId) {
		Optional<KbpstCompanyBPSetting> kbpstCompanyBPSetting = this.queryProxy()
				.query(SELECT_BY_COMPANYID, KbpstCompanyBPSetting.class).setParameter("companyId", companyId)
				.getSingle();
		if(kbpstCompanyBPSetting.isPresent()){
			return Optional.ofNullable(this.toCompanyBonusPaySettingDomain(kbpstCompanyBPSetting.get()));
		}
		return Optional.empty();
	}

	@Override
	public void addSetting(CompanyBonusPaySetting setting) {
		this.commandProxy().insert(this.toCompanyBonusPaySettingEntity(setting));
	}

	@Override
	public void updateSetting(CompanyBonusPaySetting setting) {
		this.commandProxy().update(this.toCompanyBonusPaySettingEntity(setting));
	}

	@Override
	public void removeSetting(CompanyBonusPaySetting setting) {
		this.commandProxy().remove(this.toCompanyBonusPaySettingEntity(setting));
	}

	private KbpstCompanyBPSetting toCompanyBonusPaySettingEntity(CompanyBonusPaySetting companyBonusPaySetting) {

		return new KbpstCompanyBPSetting(new KbpstCompanyBPSettingPK(companyBonusPaySetting.getCompanyId().toString()),
				companyBonusPaySetting.getBonusPaySettingCode().toString());
	}

	private CompanyBonusPaySetting toCompanyBonusPaySettingDomain(KbpstCompanyBPSetting kbpstCompanyBPSetting) {
		return CompanyBonusPaySetting.createFromJavaType(kbpstCompanyBPSetting.kbpstCompanyBPSettingPK.companyId,
				kbpstCompanyBPSetting.bonusPaySettingCode);
	}


}
