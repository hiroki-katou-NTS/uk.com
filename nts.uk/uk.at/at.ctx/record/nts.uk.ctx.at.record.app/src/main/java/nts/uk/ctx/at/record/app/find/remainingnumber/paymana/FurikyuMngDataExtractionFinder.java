package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.FurikyuMngDataExtractionService;

@Stateless
public class FurikyuMngDataExtractionFinder {

	@Inject
	private FurikyuMngDataExtractionService furikyuMngDataExtractionService;

	public List<FurikyuMngDataExtractionDto> getFurikyuMngDataExtraction(String empId, GeneralDate startDate,
			GeneralDate endDate, boolean isPeriod) {
		return furikyuMngDataExtractionService.getFurikyuMngDataExtraction(empId, startDate, endDate, isPeriod).stream()
				.map(item -> new FurikyuMngDataExtractionDto(item)).collect(Collectors.toList());
	}
}