package nts.uk.ctx.pr.core.app.find.rule.employment.allot.classification;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingHeaderRepository;

@Stateless
public class ClassificationAllotSettingHeaderFinder {

	@Inject
	private ClassificationAllotSettingHeaderRepository classificationAllotSettingHeaderRespository;

	public List<ClassificationAllotSettingHeaderDto> getAllClassificationAllotSettingHeader(String companyCode) {
		return this.classificationAllotSettingHeaderRespository.findAll(companyCode).stream()
				.map(m -> ClassificationAllotSettingHeaderDto.fromDomain(m)).collect(Collectors.toList());

	}

}
