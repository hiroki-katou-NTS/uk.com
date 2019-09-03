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
        Optional<SocialInsurAcquisiInfor> socialInsurAcquisiInfor =  socialInsurAcquisiInforRepository.getSocialInsurAcquisiInforById(cid,socialInsurAcquisiInforCommand.getEmployeeId());


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
        if(socialInsurAcquisiInfor.isPresent()){
            socialInsurAcquisiInforRepository.update(socialInsurAcquisiInforDomain);
        }else {

            socialInsurAcquisiInforRepository.add(socialInsurAcquisiInforDomain);
        }

        if(!empBasicPenNumInforCommand.getBasicPenNumber().isEmpty()){
            Optional<EmpBasicPenNumInfor> empBasicPenNumInfor = empBasicPenNumInforRepository.getEmpBasicPenNumInforById(empBasicPenNumInforCommand.getEmployeeId());
            EmpBasicPenNumInfor domain = new EmpBasicPenNumInfor(empBasicPenNumInforCommand.getEmployeeId(),empBasicPenNumInforCommand.getBasicPenNumber());
            if(empBasicPenNumInfor.isPresent()){
                empBasicPenNumInforRepository.update(domain);
            }else{
                empBasicPenNumInforRepository.add(domain);
            }
        }
        Optional<MultiEmpWorkInfo>  multiEmpWorkInfo = multiEmpWorkInfoRepository.getMultiEmpWorkInfoById(multiEmpWorkInfoCommand.getEmployeeId());
        MultiEmpWorkInfo multiEmpWorkInfoDomain = new MultiEmpWorkInfo(multiEmpWorkInfoCommand.getEmployeeId(),multiEmpWorkInfoCommand.getIsMoreEmp());
        if(multiEmpWorkInfo.isPresent()){
            multiEmpWorkInfoRepository.update(multiEmpWorkInfoDomain);
        }else{
            multiEmpWorkInfoRepository.add(multiEmpWorkInfoDomain);
        }
    }
}
