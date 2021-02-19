package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBonusPaySetting;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpmtBonusPaySettingPK;

@Stateless
public class JpaBonusPaySettingRepository extends JpaRepository implements BPSettingRepository {
	
	private static final String SELECT_BY_COMPANYID = "SELECT c FROM KbpmtBonusPaySetting c WHERE c.kbpmtBonusPaySettingPK.companyId = :companyId ORDER BY c.kbpmtBonusPaySettingPK.code ASC";
	
	private static final String IS_EXISTED = "SELECT COUNT(c) FROM KbpmtBonusPaySetting c WHERE c.kbpmtBonusPaySettingPK.companyId = :companyId AND c.kbpmtBonusPaySettingPK.code = :code";
	
	private static final String SELECT_BY_COMPANYID_AND_CODE = "SELECT c FROM KbpmtBonusPaySetting c"
			+ " WHERE c.kbpmtBonusPaySettingPK.companyId = :companyId"
			+ "		AND c.kbpmtBonusPaySettingPK.code = :codeLst"
			+ " ORDER BY c.kbpmtBonusPaySettingPK.code ASC";
	
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
		Optional<KbpmtBonusPaySetting> kbpmtBonusPaySettingOptional = this.queryProxy().find(new KbpmtBonusPaySettingPK(domain.getCompanyId().toString(), domain.getCode().v()),KbpmtBonusPaySetting.class);
		if(kbpmtBonusPaySettingOptional.isPresent()){
			KbpmtBonusPaySetting kbpmtBonusPaySetting = kbpmtBonusPaySettingOptional.get();
			kbpmtBonusPaySetting.name=domain.getName().v();
			this.commandProxy().update(kbpmtBonusPaySetting);
		}

	}

	@Override
	public void removeBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		Optional<KbpmtBonusPaySetting> kbpmtBonusPaySetting = this.queryProxy()
				.find(new KbpmtBonusPaySettingPK(companyId, bonusPaySettingCode.v()), KbpmtBonusPaySetting.class);
		this.commandProxy().remove(kbpmtBonusPaySetting.get());
	}

	private BonusPaySetting toBonusPaySettingDomain(KbpmtBonusPaySetting kbpmtBonusPaySetting) {
		return BonusPaySetting.createFromJavaType(kbpmtBonusPaySetting.kbpmtBonusPaySettingPK.companyId,
				kbpmtBonusPaySetting.kbpmtBonusPaySettingPK.code, kbpmtBonusPaySetting.name);
	}

	private KbpmtBonusPaySetting toBonusPaySettingEntity(BonusPaySetting bonusPaySetting) {
		return new KbpmtBonusPaySetting(
				new KbpmtBonusPaySettingPK(bonusPaySetting.getCompanyId().toString(),
						bonusPaySetting.getCode().toString()),
				bonusPaySetting.getName().toString());
	}

	@Override
	public Optional<BonusPaySetting> getBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		Optional<KbpmtBonusPaySetting> kbpmtBonusPaySetting = this.queryProxy()
				.find(new KbpmtBonusPaySettingPK(companyId, bonusPaySettingCode.v()), KbpmtBonusPaySetting.class);
		
		if(kbpmtBonusPaySetting.isPresent()){
			return  Optional.ofNullable(this.toBonusPaySettingDomain(kbpmtBonusPaySetting.get()));
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

	@Override
	public List<BonusPaySetting> findByCompanyAndCode(String companyId, List<String> bonusPaySettingCodeLst) {
		if (bonusPaySettingCodeLst.isEmpty()) {
			return new ArrayList<BonusPaySetting>();
		}
		return this.queryProxy().query(SELECT_BY_COMPANYID_AND_CODE, KbpmtBonusPaySetting.class)
				.setParameter("companyId", companyId)
				.setParameter("codeLst", bonusPaySettingCodeLst)
				.getList(t -> toBonusPaySettingDomain(t));
	}

}
