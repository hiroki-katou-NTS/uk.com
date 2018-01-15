package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.DailyRecordOperationDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.DailyRecordOperation;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.DailyRecordOperationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyRecordOperationFinder {
	@Inject
	private DailyRecordOperationRepository dailyRecordOperationRepository;

	public DailyRecordOperationDto getSettingUnit() {
		String companyId = AppContexts.user().companyId();
		Optional<DailyRecordOperation> settingUnit = this.dailyRecordOperationRepository.getSettingUnit(companyId);

		if (settingUnit.isPresent()) {
			return new DailyRecordOperationDto(companyId, settingUnit.get().getSettingUnit().value);
		}
		return new DailyRecordOperationDto(companyId, 1);
	}
}
