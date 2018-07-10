package nts.uk.ctx.exio.app.find.exo.awdataformatsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.dataformat.AwDataFormatSet;
import nts.uk.ctx.exio.dom.exo.dataformat.AwDataFormatSetRepository;

@Stateless
public class AWDataScreenFinder {

	@Inject
	AwDataFormatSetRepository awDataFormatSetRepository;

	public AwDataFormatDTO getAWData(AWOutputTypeSettingDTO outputTypeSettingDTO) {
		AwDataFormatDTO awDataFormatDTO = null;
		Optional<AwDataFormatSet> awDataFormatSet = awDataFormatSetRepository
				.getAwDataFormatSetById(outputTypeSettingDTO.getCid());
		if ("individual".equals(outputTypeSettingDTO.getClassification())
				|| "initial".equals(outputTypeSettingDTO.getClassification())) {
			if (awDataFormatSet.isPresent()) {
				awDataFormatSet.get().getAbsenceOutput().ifPresent(x -> {
					awDataFormatDTO.setAbsenceOutput(x.v());
				});
				awDataFormatSet.get().getAtWorkOutput().ifPresent(x -> {
					awDataFormatDTO.setAtWorkOutput(x.v());
				});
				awDataFormatSet.get().getClosedOutput().ifPresent(x -> {
					awDataFormatDTO.setClosedOutput(x.v());
				});
				awDataFormatSet.get().getRetirementOutput().ifPresent(x -> {
					awDataFormatDTO.setRetirementOutput(x.v());
				});
				awDataFormatSet.get().getValueOfFixedValue().ifPresent(x -> {
					awDataFormatDTO.setValueOfFixedValue(x.v());
				});
				awDataFormatDTO.setFixedValue(awDataFormatSet.get().getFixedValue().value);
			}
		}
		return awDataFormatDTO;
	}
}
