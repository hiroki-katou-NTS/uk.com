package nts.uk.ctx.exio.app.find.exo.condset;

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
		OutputPeriodSettingDto dto = new OutputPeriodSettingDto();
		domain.setMemento(dto);
		return dto;
	}

}
