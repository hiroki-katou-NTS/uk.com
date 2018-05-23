package nts.uk.ctx.at.record.pubimp.workrecord.operationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;
import nts.uk.ctx.at.record.pub.workrecord.operationsetting.FormatPerformanceExport;
import nts.uk.ctx.at.record.pub.workrecord.operationsetting.FormatPerformancePub;

@Stateless
public class FormatPerformancePubImpl implements FormatPerformancePub {

	@Inject
	private FormatPerformanceRepository formatPerformanceRepository;

	@Override
	public Optional<FormatPerformanceExport> findByCompanyId(String companyId) {

		Optional<FormatPerformanceExport> formatPerformanceExport = Optional.empty();

		Optional<FormatPerformance> formatPerformance = this.formatPerformanceRepository
				.getFormatPerformanceById(companyId);

		if (formatPerformance.isPresent()) {
			FormatPerformanceExport performanceExport = this.convertToExport(formatPerformance.get());
			formatPerformanceExport = Optional.of(performanceExport);
		}

		return formatPerformanceExport;
	}

	private FormatPerformanceExport convertToExport(FormatPerformance domain) {
		return new FormatPerformanceExport(domain.getCid(), domain.getSettingUnitType().value);
	}

}
