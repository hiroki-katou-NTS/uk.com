package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthlyRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MonPfmCorrectionFormatFinder {

	@Inject
	private MonPfmCorrectionFormatRepository repo;

	@Inject
	private InitialDisplayMonthlyRepository initialDisplayMonthlyRepo;

	public List<MonPfmCorrectionFormatDto> getAllMonPfmCorrectionFormat() {
		String companyID = AppContexts.user().companyId();
		List<MonPfmCorrectionFormatDto> data = repo.getAllMonPfm(companyID).stream().map(c -> MonPfmCorrectionFormatDto.fromDomain(c)).collect(Collectors.toList());
		if (data.isEmpty())
			return Collections.emptyList();
		for (MonPfmCorrectionFormatDto monPfmCorrectionFormat : data) {
			Optional<InitialDisplayMonthly> checkData = initialDisplayMonthlyRepo.getInitialDisplayMon(companyID, monPfmCorrectionFormat.getMonthlyPfmFormatCode());
			if (checkData.isPresent()) {
				monPfmCorrectionFormat.setSetFormatToDefault(false);
			} else {
				monPfmCorrectionFormat.setSetFormatToDefault(true);
			}

		}
		return data;

	}

	public MonPfmCorrectionFormatDto getMonPfmCorrectionFormat(String monthlyPfmFormatCode) {
		String companyID = AppContexts.user().companyId();
		Optional<MonPfmCorrectionFormat> data = repo.getMonPfmCorrectionFormat(companyID, monthlyPfmFormatCode);
		if (data.isPresent()) {
			MonPfmCorrectionFormatDto dataUi = MonPfmCorrectionFormatDto.fromDomain(data.get());
			Optional<InitialDisplayMonthly> checkData = initialDisplayMonthlyRepo.getInitialDisplayMon(companyID, data.get().getMonthlyPfmFormatCode().v());
			if (checkData.isPresent())
				dataUi.setSetFormatToDefault(false);
			else
				dataUi.setSetFormatToDefault(true);
			return dataUi;

		}
		return null;

	}
	

	public List<MonPfmCorrectionFormatDto> getMonPfmCorrectionFormat(String companyID, List<String> monthlyPfmFormatCode) {
		return repo.getMonPfmCorrectionFormat(companyID, monthlyPfmFormatCode)
				.stream()
				.map(item -> MonPfmCorrectionFormatDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
