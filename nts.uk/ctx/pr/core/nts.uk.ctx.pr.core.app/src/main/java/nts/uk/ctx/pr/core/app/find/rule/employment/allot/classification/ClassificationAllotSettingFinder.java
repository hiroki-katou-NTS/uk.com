package nts.uk.ctx.pr.core.app.find.rule.employment.allot.classification;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.classification.ClassificationAllotSettingRespository;
@Stateless
public class ClassificationAllotSettingFinder {
	@Inject
	private ClassificationAllotSettingRespository classificationAllotSettingRespository;

	public List<ClassificationAllotSettingDto> getAllClassificationAllotSetting(String companyCode){
		return this.classificationAllotSettingRespository.findAll(companyCode).stream()
				.map(m -> ClassificationAllotSettingDto.fromDomain(m))
				.collect(Collectors.toList());
	}
}