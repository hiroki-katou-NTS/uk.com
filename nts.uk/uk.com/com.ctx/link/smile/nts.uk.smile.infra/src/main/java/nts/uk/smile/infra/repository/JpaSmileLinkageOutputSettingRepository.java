package nts.uk.smile.infra.repository;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileCooperationOutputClassification;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;
import nts.uk.smile.infra.entity.smilelinked.MiomtSmileLinkOutset;
import nts.uk.smile.infra.entity.smilelinked.MiomtSmileLinkOutsetPK;

@Stateless
public class JpaSmileLinkageOutputSettingRepository extends JpaRepository
		implements SmileLinkageOutputSettingRepository {

	private MiomtSmileLinkOutset toEntity(SmileLinkageOutputSetting domain) {
		MiomtSmileLinkOutset entity = new MiomtSmileLinkOutset(
				new MiomtSmileLinkOutsetPK(AppContexts.user().contractCode(), AppContexts.user().companyId()),
				domain.getSalaryCooperationClassification().value,
				domain.getSalaryCooperationConditions().isPresent() ? domain.getSalaryCooperationConditions().get().v()
						: null,
				domain.getMonthlyLockClassification().value, domain.getMonthlyApprovalCategory().value);
		return entity;
	}

	private SmileLinkageOutputSetting toDomain(MiomtSmileLinkOutset entity) {
		ExternalOutputConditionCode externalOutputConditionCode = null;
		if (entity.getSalaryCooperationCond() != null) {
			externalOutputConditionCode = new ExternalOutputConditionCode(entity.getSalaryCooperationCond());
		}
		SmileLinkageOutputSetting domain = new SmileLinkageOutputSetting(
				EnumAdaptor.valueOf(entity.getSalaryCooperationtAtr(), SmileCooperationOutputClassification.class),
				EnumAdaptor.valueOf(entity.getMonthLockAtr(), SmileCooperationOutputClassification.class),
				EnumAdaptor.valueOf(entity.getMonthApproAtr(), SmileCooperationOutputClassification.class),
				Optional.ofNullable(externalOutputConditionCode));
		return domain;
	}

	@Override
	public void insert(SmileLinkageOutputSetting domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	@Override
	public void update(SmileLinkageOutputSetting domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	@Override
	public void delete(String contractCode, String companyId) {
		MiomtSmileLinkOutsetPK miomtSmileLinkOutsetPK = new MiomtSmileLinkOutsetPK(contractCode, companyId);
		this.commandProxy().remove(MiomtSmileLinkOutset.class, miomtSmileLinkOutsetPK);
	}

	@Override
	public SmileLinkageOutputSetting get(String contractCode, String companyId) {
		MiomtSmileLinkOutsetPK miomtSmileLinkOutsetPK = new MiomtSmileLinkOutsetPK(contractCode, companyId);
		Optional<MiomtSmileLinkOutset> optinalMiomtSmileLinkOutset = this.queryProxy().find(miomtSmileLinkOutsetPK,
				MiomtSmileLinkOutset.class);
		if (optinalMiomtSmileLinkOutset.isPresent()) {
			return this.toDomain(optinalMiomtSmileLinkOutset.get());
		}
		return null;
	}

}
