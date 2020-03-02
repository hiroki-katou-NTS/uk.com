package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@Stateless
@Transactional
public class AddEmpBasicPenNumInforCommandHandler extends CommandHandler<CredentialAcquisitionInfoCommand>
{
    
    @Inject
    private EmpBasicPenNumInforRepository empBasicPenNumInforRepository;

    @Inject
    private MultiEmpWorkInfoRepository multiEmpWorkInfoRepository;

    @Inject
    private SocialInsurAcquisiInforRepository socialInsurAcquisiInforRepository;
    
    @Override
    protected void handle(CommandHandlerContext<CredentialAcquisitionInfoCommand> context) {

        CredentialAcquisitionInfoCommand command = context.getCommand();
        SocialInsurAcquisiInforCommand socialInsurAcquisiInforCommand = command.getSocialInsurAcquisiInforCommand();
        MultiEmpWorkInfoCommand multiEmpWorkInfoCommand = command.getMultiEmpWorkInfoCommand();
        EmpBasicPenNumInforCommand empBasicPenNumInforCommand = command.getEmpBasicPenNumInforCommand();
        String cid = AppContexts.user().companyId();
        Optional<SocialInsurAcquisiInfor> socialInsurAcquisiInfor =  socialInsurAcquisiInforRepository.getSocialInsurAcquisiInforByCIdEmpId(cid, socialInsurAcquisiInforCommand.getEmployeeId());
        if(socialInsurAcquisiInfor.isPresent()){
            SocialInsurAcquisiInfor socialInsurAcquisiInforDomain = new SocialInsurAcquisiInfor(
                    cid,
                    socialInsurAcquisiInforCommand.getEmployeeId(),
                    socialInsurAcquisiInforCommand.getPercentOrMore(),
                    socialInsurAcquisiInforCommand.getRemarksOther(),
                    socialInsurAcquisiInforCommand.getRemarksAndOtherContents(),
                    socialInsurAcquisiInforCommand.getRemunMonthlyAmountKind(),
                    socialInsurAcquisiInforCommand.getRemunMonthlyAmount(),
                    socialInsurAcquisiInforCommand.getTotalMonthlyRemun(),
                    socialInsurAcquisiInforCommand.getLivingAbroad(),
                    socialInsurAcquisiInforCommand.getReasonOther(),
                    socialInsurAcquisiInforCommand.getReasonAndOtherContents(),
                    socialInsurAcquisiInforCommand.getShortTimeWorkers(),
                    socialInsurAcquisiInforCommand.getShortStay(),
                    socialInsurAcquisiInforCommand.getDepenAppoint(),
                    socialInsurAcquisiInforCommand.getQualifiDistin(),
                    socialInsurAcquisiInforCommand.getContinReemAfterRetirement()

            );
            socialInsurAcquisiInforRepository.update(socialInsurAcquisiInforDomain);
        }else {

            SocialInsurAcquisiInfor socialInsurAcquisiInforDomain = new SocialInsurAcquisiInfor(
                    cid,
                    socialInsurAcquisiInforCommand.getEmployeeId(),
                    socialInsurAcquisiInforCommand.getPercentOrMore() == 0 ? null : socialInsurAcquisiInforCommand.getPercentOrMore(),
                    socialInsurAcquisiInforCommand.getRemarksOther() == 0 ? null : socialInsurAcquisiInforCommand.getRemarksOther(),
                    socialInsurAcquisiInforCommand.getRemarksAndOtherContents(),
                    socialInsurAcquisiInforCommand.getRemunMonthlyAmountKind(),
                    socialInsurAcquisiInforCommand.getRemunMonthlyAmount(),
                    socialInsurAcquisiInforCommand.getTotalMonthlyRemun(),
                    socialInsurAcquisiInforCommand.getLivingAbroad(),
                    socialInsurAcquisiInforCommand.getReasonOther(),
                    socialInsurAcquisiInforCommand.getReasonAndOtherContents(),
                    socialInsurAcquisiInforCommand.getShortTimeWorkers() == 0 ? null : socialInsurAcquisiInforCommand.getShortTimeWorkers(),
                    socialInsurAcquisiInforCommand.getShortStay(),
                    socialInsurAcquisiInforCommand.getDepenAppoint() == 0 && socialInsurAcquisiInforCommand.getDepenAppoint() == null ? null : socialInsurAcquisiInforCommand.getDepenAppoint(),
                    socialInsurAcquisiInforCommand.getQualifiDistin(),
                    socialInsurAcquisiInforCommand.getContinReemAfterRetirement() == 0? null : socialInsurAcquisiInforCommand.getContinReemAfterRetirement()

            );

            socialInsurAcquisiInforRepository.add(socialInsurAcquisiInforDomain);
        }

        if(!empBasicPenNumInforCommand.getBasicPenNumber().isEmpty()){
            Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empBasicPenNumInforCommand.getEmployeeId());

            if(empBasicPenNumInfor.isPresent()){
                EmpBasicPenNumInfor domain = new EmpBasicPenNumInfor(empBasicPenNumInforCommand.getEmployeeId(),empBasicPenNumInforCommand.getBasicPenNumber());
                empBasicPenNumInforRepository.update(domain);
            }else{
                EmpBasicPenNumInfor domain = new EmpBasicPenNumInfor(empBasicPenNumInforCommand.getEmployeeId(),empBasicPenNumInforCommand.getBasicPenNumber().isEmpty() ? null : empBasicPenNumInforCommand.getBasicPenNumber());
                empBasicPenNumInforRepository.add(domain);
            }
        }else{
            EmpBasicPenNumInfor domain = new EmpBasicPenNumInfor(empBasicPenNumInforCommand.getEmployeeId(),null);
            empBasicPenNumInforRepository.update(domain);
        }
        Optional<MultiEmpWorkInfo>  multiEmpWorkInfo = multiEmpWorkInfoRepository.getMultiEmpWorkInfoById(multiEmpWorkInfoCommand.getEmployeeId());

        if(multiEmpWorkInfo.isPresent()){
            MultiEmpWorkInfo multiEmpWorkInfoDomain = new MultiEmpWorkInfo(multiEmpWorkInfoCommand.getEmployeeId(),multiEmpWorkInfoCommand.getIsMoreEmp());
            multiEmpWorkInfoRepository.update(multiEmpWorkInfoDomain);
        }else{
            MultiEmpWorkInfo multiEmpWorkInfoDomain = new MultiEmpWorkInfo(multiEmpWorkInfoCommand.getEmployeeId(),multiEmpWorkInfoCommand.getIsMoreEmp());
            multiEmpWorkInfoRepository.add(multiEmpWorkInfoDomain);
        }
    }
}
