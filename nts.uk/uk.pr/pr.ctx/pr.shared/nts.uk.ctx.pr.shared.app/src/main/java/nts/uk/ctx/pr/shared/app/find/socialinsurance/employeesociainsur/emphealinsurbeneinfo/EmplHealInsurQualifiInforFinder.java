package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
* 社員健康保険資格情報: Finder
*/
public class EmplHealInsurQualifiInforFinder
{

    @Inject
    private EmplHealInsurQualifiInforRepository finder;

    public List<EmplHealInsurQualifiInforDto> getAllEmplHealInsurQualifiInfor(){
        return finder.getAllEmplHealInsurQualifiInfor().stream().map(item -> EmplHealInsurQualifiInforDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
