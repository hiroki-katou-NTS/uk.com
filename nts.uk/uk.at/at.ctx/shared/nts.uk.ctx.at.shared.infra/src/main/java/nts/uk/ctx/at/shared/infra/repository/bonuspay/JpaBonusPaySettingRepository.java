package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KrcmtBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KrcmtBonusPaySettingPK;

@Stateless
public class JpaBonusPaySettingRepository extends JpaRepository implements BPSettingRepository {
	
	private static final String SELECT_BY_COMPANYID = "SELECT c FROM KrcmtBonusPaySetting c WHERE c.krcmtBonusPaySettingPK.companyId = :companyId ORDER BY c.krcmtBonusPaySettingPK.code ASC";
	
	private static final String IS_EXISTED = "SELECT COUNT(c) FROM KrcmtBonusPaySetting c WHERE c.krcmtBonusPaySettingPK.companyId = :companyId AND c.krcmtBonusPaySettingPK.code = :code";
	
	@Override
	public List<BonusPaySetting> getAllBonusPaySetting(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANYID, KrcmtBonusPaySetting.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPaySettingDomain(x));
	}

	@Override
	public void addBonusPaySetting(BonusPaySetting domain) {
		this.commandProxy().insert(this.toBonusPaySettingEntity(domain));
	}

	@Override
	public void updateBonusPaySetting(BonusPaySetting domain) {
		Optional<KrcmtBonusPaySetting> krcmtBonusPaySettingOptional = this.queryProxy().find(new KrcmtBonusPaySettingPK(domain.getCompanyId().toString(), domain.getCode().v()),KrcmtBonusPaySetting.class);
		if(krcmtBonusPaySettingOptional.isPresent()){
			KrcmtBonusPaySetting krcmtBonusPaySetting = krcmtBonusPaySettingOptional.get();
			krcmtBonusPaySetting.name=domain.getName().v();
			this.commandProxy().update(krcmtBonusPaySetting);
		}

	}

	@Override
	public void removeBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		Optional<KrcmtBonusPaySetting> krcmtBonusPaySetting = this.queryProxy()
				.find(new KrcmtBonusPaySettingPK(companyId, bonusPaySettingCode.v()), KrcmtBonusPaySetting.class);
		this.commandProxy().remove(krcmtBonusPaySetting.get());
	}

	private BonusPaySetting toBonusPaySettingDomain(KrcmtBonusPaySetting krcmtBonusPaySetting) {
		return BonusPaySetting.createFromJavaType(krcmtBonusPaySetting.krcmtBonusPaySettingPK.companyId,
				krcmtBonusPaySetting.krcmtBonusPaySettingPK.code, krcmtBonusPaySetting.name);
	}

	private KrcmtBonusPaySetting toBonusPaySettingEntity(BonusPaySetting bonusPaySetting) {
		return new KrcmtBonusPaySetting(
				new KrcmtBonusPaySettingPK(bonusPaySetting.getCompanyId().toString(),
						bonusPaySetting.getCode().toString()),
				bonusPaySetting.getName().toString());
	}

	@Override
	public Optional<BonusPaySetting> getBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		Optional<KrcmtBonusPaySetting> krcmtBonusPaySetting = this.queryProxy()
				.find(new KrcmtBonusPaySettingPK(companyId, bonusPaySettingCode.v()), KrcmtBonusPaySetting.class);
		
		if(krcmtBonusPaySetting.isPresent()){
			return  Optional.ofNullable(this.toBonusPaySettingDomain(krcmtBonusPaySetting.get()));
		}
		return Optional.empty();
	}

	@Override
	public boolean isExisted(String companyId, BonusPaySettingCode code) {
		return this.queryProxy().query(IS_EXISTED, Long.class)
				.setParameter("companyId", companyId)
				.setParameter("code", code.v())
				.getSingle().get() > 0;
	}

}
