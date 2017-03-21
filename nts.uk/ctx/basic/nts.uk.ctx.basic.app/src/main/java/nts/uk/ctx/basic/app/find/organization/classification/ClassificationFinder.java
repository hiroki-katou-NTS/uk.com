package nts.uk.ctx.basic.app.find.organization.classification;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.classification.Classification;
import nts.uk.ctx.basic.dom.organization.classification.ClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ClassificationFinder {

	@Inject
	private ClassificationRepository classificationRepository;

	/**
	 * get All Classification
	 * @return List<ClassificationDto>
	 */
	public List<ClassificationDto> init() {
		String companyCode = AppContexts.user().companyCode();
		return classificationRepository.findAll(companyCode).stream().map(e -> {
			return convertToDto(e);
		}).collect(Collectors.toList());
	}


/**
 * Convert from DOM Layer to InFra Layer
 * @param classification
 * @return ClassificationDto
 */
	private ClassificationDto convertToDto(Classification classification) {
		ClassificationDto classificationDto = new ClassificationDto(
				classification.getClassificationCode().toString(),
				classification.getClassificationName().toString(),
				classification.getClassificationMemo().toString());
		return classificationDto;
	}

}
