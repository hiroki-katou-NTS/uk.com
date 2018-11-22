package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class QualificationInformationFinder {

    @Inject
    private QualificationInformationRepository qualificationInformationRepository;

    public List<QualificationInformationDto> findByCompany () {
        return qualificationInformationRepository.getQualificationGroupSettingByCompanyID().stream().map(QualificationInformationDto::fromDomainToDto).collect(Collectors.toList());
    }

    public QualificationInformationDto findByQualificationCode (String qualificationCode) {
        return qualificationInformationRepository.getQualificationGroupSettingById(qualificationCode).map(QualificationInformationDto::fromDomainToDto).orElse(null);
    }
}
