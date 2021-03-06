package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;

@Stateless
public class QualificationInformationFinder {

	@Inject
	private QualificationInformationRepository qualificationInformationRepository;
	
	@Inject
	private QualificationGroupSettingRepository groupSetRepo;

	public List<QualificationInformationDto> findByCompany() {
		return qualificationInformationRepository.getQualificationGroupSettingByCompanyID().stream()
				.map(QualificationInformationDto::fromDomainToDto).collect(Collectors.toList());
	}

	public QualificationInformationDto findByQualificationCode(String qualificationCode) {
		return qualificationInformationRepository.getQualificationGroupSettingById(qualificationCode)
				.map(QualificationInformationDto::fromDomainToDto).orElse(null);
	}
	
	public List<String> findByCompanyExcludeUsed(String targetGroupCode) {
		return groupSetRepo.getUsedQualificationCodeByCompanyID(targetGroupCode);
	}
	
}
