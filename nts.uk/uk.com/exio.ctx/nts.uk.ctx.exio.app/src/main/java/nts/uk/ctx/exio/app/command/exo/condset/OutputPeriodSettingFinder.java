package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OutputPeriodSettingFinder {

	@Inject
	private OutputPeriodSettingRepository repo;

	public OutputPeriodSettingDto findByConditionSetCode(String conditionSetCode) {
		String cId = AppContexts.user().companyId();
		Optional<OutputPeriodSetting> oDomain = this.repo.findById(cId, conditionSetCode);
		return oDomain.map(d -> toDto(d)).orElse(null);
	}

	private OutputPeriodSettingDto toDto(OutputPeriodSetting domain) {
		return OutputPeriodSettingDto.builder()
				.cid(domain.getCid())
				.periodSetting(domain.getPeriodSetting().value)
				.conditionSetCode(domain.getConditionSetCode().v())
				.baseDateClassification(domain.getBaseDateClassification()
						.map(v -> v.value)
						.orElse(null))
				.baseDateSpecify(domain.getBaseDateSpecify().orElse(null))
				.startDateClassification(domain.getStartDateClassification()
						.map(v -> v.value)
						.orElse(null))
				.startDateSpecify(domain.getStartDateSpecify().orElse(null))
				.startDateAdjustment(domain.getStartDateAdjustment()
						.map(v -> v.v())
						.orElse(null))
				.endDateClassification(domain.getEndDateClassification()
						.map(v -> v.value)
						.orElse(null))
				.endDateSpecify(domain.getEndDateSpecify().orElse(null))
				.endDateAdjustment(domain.getEndDateAdjustment()
						.map(v -> v.v())
						.orElse(null))
				.deadlineClassification(domain.getDeadlineClassification().orElse(null))
				.build();
	}

}
