package nts.uk.ctx.basic.dom.organization.classification;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ClassificationDomainService {

	@Inject
	private ClassificationRepository classificationRepository;

	public void add(Classification classification) {
		if (classificationRepository.isExisted(classification.getCompanyCode(),
				classification.getClassificationCode())) {
			// throw err[ER005]
		}
		classificationRepository.add(classification);
	}

	public void remove(String companyCode, ClassificationCode classificationCode) {
		if (!classificationRepository.isExisted(companyCode, classificationCode)) {
			// throw err[ER010]
		}
		classificationRepository.remove(companyCode, classificationCode);
	}

	public void update(Classification classification) {
		if (!classificationRepository.isExisted(classification.getCompanyCode(),
				classification.getClassificationCode())) {
			// throw err[ER026]
		}
		classificationRepository.update(classification);
	}

}
