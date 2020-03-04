package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;

@Stateless
public class QualificationGroupSettingFinder {

	@Inject
	private QualificationGroupSettingRepository qualificationGroupSettingRepository;

	public List<QualificationGroupSettingDto> findByCompany() {
		return qualificationGroupSettingRepository.getQualificationGroupSettingByCompanyID().stream()
				.map(QualificationGroupSettingDto::fromDomainToDto).collect(Collectors.toList());
	}

	public QualificationGroupSettingDto findByQualificationGroupCode(String qualificationGroupCode) {
		return qualificationGroupSettingRepository.getQualificationGroupSettingById(qualificationGroupCode)
				.map(QualificationGroupSettingDto::fromDomainToDto).orElse(null);
	}

}
