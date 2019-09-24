package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInLossInfoDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfoDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo.SocialInsurAcquisiInforDto;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIfRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class LossInfoFinder {

    @Inject
    private WelfPenInsLossIfRepository finderWelPen;

    @Inject
    private HealthInsLossInfoRepository finderHealth;

    @Inject
    private MultiEmpWorkInfoRepository finderMultiWork;

    @Inject
    private EmpBasicPenNumInforRepository finderBacsicPen;

    @Inject
    private SocialInsurAcquisiInforRepository finderSocial;

    public LossInfoDto getLossInfoById(String companyId, String empId){

        Optional<WelfPenInsLossIf> domainWelPen = finderWelPen.getWelfPenLossInfoById(empId);
        Optional<HealthInsLossInfo> domainHealth = finderHealth.getHealthInsLossInfoById(empId);
        Optional<MultiEmpWorkInfo> domainMultiWork = finderMultiWork.getMultiEmpWorkInfoById(empId);
        Optional<EmpBasicPenNumInfor> domainBasicPen = finderBacsicPen.getEmpBasicPenNumInforById(empId);
        Optional<SocialInsurAcquisiInfor> domainSocial = finderSocial.getSocialInsurAcquisiInforByCIdEmpId(companyId, empId );

        return new LossInfoDto(
                domainHealth.isPresent() ? HealthInLossInfoDto.fromDomain(domainHealth.get()) : null,
                domainWelPen.isPresent() ? WelfPenInsLossIfDto.fromDomain(domainWelPen.get()) : null,
                domainBasicPen.isPresent() ? EmpBasicPenNumInforDto.fromDomain( domainBasicPen.get()) : null,
                domainMultiWork.isPresent() ? MultiEmpWorkInfoDto.fromDomain(domainMultiWork.get()) :null,
                domainSocial.isPresent() ? SocialInsurAcquisiInforDto.fromDomain(domainSocial.get()): null,
                1);
    }
}
