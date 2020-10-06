package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.CompanyBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstCompanyBPSetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstCompanyBPSettingPK;

@Stateless
public class JpaCompanyBPSettingRepository extends JpaRepository implements CPBonusPaySettingRepository {
	private static final String SELECT_BY_COMPANYID = "SELECT c FROM KbpstCompanyBPSetting c WHERE c.kbpstCompanyBPSettingPK.companyId = :companyId";

	@Override
	public Optional<CompanyBonusPaySetting> getSetting(String companyId) {
		Optional<KbpstCompanyBPSetting> kbpstCompanyBPSetting = this.queryProxy()
				.query(SELECT_BY_COMPANYID, KbpstCompanyBPSetting.class).setParameter("companyId", companyId)
				.getSingle();

		if (kbpstCompanyBPSetting.isPresent()) {
			return Optional.ofNullable(toDomain(kbpstCompanyBPSetting.get()));
		}

		return Optional.empty();
	}

	@Override
	public void saveSetting(CompanyBonusPaySetting setting) {
		Optional<CompanyBonusPaySetting> domain = this.getSetting(setting.getCompanyId());
		if (domain.isPresent()) {
			this.commandProxy().update(toEntity(setting));
		} else {
			this.commandProxy().insert(toEntity(setting));
		}
	}

	@Override
	public void removeSetting(CompanyBonusPaySetting setting) {
		Optional<CompanyBonusPaySetting> domain = this.getSetting(setting.getCompanyId());
		if (domain.isPresent()) {

			this.commandProxy().remove(KbpstCompanyBPSetting.class,
					new KbpstCompanyBPSettingPK(setting.getCompanyId()));
		}
	}

	private KbpstCompanyBPSetting toEntity(CompanyBonusPaySetting domain) {
		return new KbpstCompanyBPSetting(new KbpstCompanyBPSettingPK(domain.getCompanyId()),
				domain.getBonusPaySettingCode().v());
	}

	private CompanyBonusPaySetting toDomain(KbpstCompanyBPSetting entity) {
		return CompanyBonusPaySetting.createFromJavaType(entity.kbpstCompanyBPSettingPK.companyId,
				entity.bonusPaySettingCode);
	}

}
