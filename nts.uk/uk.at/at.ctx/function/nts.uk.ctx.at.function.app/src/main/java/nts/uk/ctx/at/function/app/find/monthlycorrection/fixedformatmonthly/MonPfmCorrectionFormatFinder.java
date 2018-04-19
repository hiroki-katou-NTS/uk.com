package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;

@Stateless
public class MonPfmCorrectionFormatFinder {
	@Inject
	MonPfmCorrectionFormatRepository monPfmCorrectionFormatRepository;

	public List<MonPfmCorrectionFormatDto> getMonPfmCorrectionFormat(String companyID, List<String> monthlyPfmFormatCode) {
		return monPfmCorrectionFormatRepository.getMonPfmCorrectionFormat(companyID, monthlyPfmFormatCode)
				.stream()
				.map(item -> MonPfmCorrectionFormatDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
