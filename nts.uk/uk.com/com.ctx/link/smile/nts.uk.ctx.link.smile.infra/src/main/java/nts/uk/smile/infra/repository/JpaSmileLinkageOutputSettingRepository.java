package nts.uk.smile.infra.repository;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.ExternalOutputConditionCode;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileCooperationOutputClassification;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.ctx.link.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.smile.infra.entity.smilelinked.LsmmtSmileLinkOutset;
import nts.uk.smile.infra.entity.smilelinked.LsmmtSmileLinkOutsetPK;

@Stateless
public class JpaSmileLinkageOutputSettingRepository extends JpaRepository
		implements SmileLinkageOutputSettingRepository {

	private LsmmtSmileLinkOutset toEntity(SmileLinkageOutputSetting domain) {
		LsmmtSmileLinkOutset entity = new LsmmtSmileLinkOutset(
				new LsmmtSmileLinkOutsetPK(AppContexts.user().contractCode(), AppContexts.user().companyId()),
				domain.getSalaryCooperationClassification().value,
				domain.getSalaryCooperationConditions().isPresent() ? domain.getSalaryCooperationConditions().get().v()
						: null,
				domain.getMonthlyLockClassification().value, domain.getMonthlyApprovalCategory().value);
		return entity;
	}

	private SmileLinkageOutputSetting toDomain(LsmmtSmileLinkOutset entity) {
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
		LsmmtSmileLinkOutsetPK miomtSmileLinkOutsetPK = new LsmmtSmileLinkOutsetPK(contractCode, companyId);
		this.commandProxy().remove(LsmmtSmileLinkOutset.class, miomtSmileLinkOutsetPK);
	}

	@Override
	public Optional<SmileLinkageOutputSetting> get(String contractCode, String companyId) {
		LsmmtSmileLinkOutsetPK miomtSmileLinkOutsetPK = new LsmmtSmileLinkOutsetPK(contractCode, companyId);
		Optional<LsmmtSmileLinkOutset> optinalMiomtSmileLinkOutset = this.queryProxy().find(miomtSmileLinkOutsetPK,
				LsmmtSmileLinkOutset.class);
		if (optinalMiomtSmileLinkOutset.isPresent()) {
			return Optional.of(this.toDomain(optinalMiomtSmileLinkOutset.get()));
		}
		return Optional.empty();
	}

}
