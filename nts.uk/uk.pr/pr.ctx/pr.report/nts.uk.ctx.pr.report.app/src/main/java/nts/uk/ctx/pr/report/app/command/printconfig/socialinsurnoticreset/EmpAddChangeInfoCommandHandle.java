package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.AddChangeSetting;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class EmpAddChangeInfoCommandHandle extends CommandHandler<EmpAddChangeInfoCommand> {
    @Inject
    EmpAddChangeInfoRepository mEmpAddChangeInfoRepository;

    @Inject
    EmpBasicPenNumInforRepository mEmpBasicPenNumInforRepository;

    @Override
    protected void handle(CommandHandlerContext<EmpAddChangeInfoCommand> commandHandlerContext) {
        EmpAddChangeInfoCommand command = commandHandlerContext.getCommand();
        registerEmpAddChangeInfo(command);
    }

    public void registerEmpAddChangeInfo(EmpAddChangeInfoCommand empAddChangeInfoDto){
        EmpAddChangeInfo empAddChangeInfo =  new EmpAddChangeInfo(empAddChangeInfoDto.getSid(),
                new AddChangeSetting(
                        empAddChangeInfoDto.getShortResidentAtr(),
                        empAddChangeInfoDto.getLivingAbroadAtr(),
                        empAddChangeInfoDto.getResidenceOtherResidentAtr(),
                        empAddChangeInfoDto.getOtherAtr(),
                        empAddChangeInfoDto.getOtherReason()
                ),
                new AddChangeSetting(
                        empAddChangeInfoDto.getSpouseShortResidentAtr(),
                        empAddChangeInfoDto.getSpouseLivingAbroadAtr(),
                        empAddChangeInfoDto.getSpouseResidenceOtherResidentAtr(),
                        empAddChangeInfoDto.getSpouseOtherAtr(),
                        empAddChangeInfoDto.getSpouseOtherReason()
                )
        );
        EmpBasicPenNumInfor empBasicPenNumInfor = new EmpBasicPenNumInfor(
                empAddChangeInfoDto.getSid(),
                empAddChangeInfoDto.getBasicPenNumber()
        );

        if(empAddChangeInfoDto.isUpdateEmpBasicPenNumInfor()){
            mEmpBasicPenNumInforRepository.update(empBasicPenNumInfor);
        }
        else {
            mEmpBasicPenNumInforRepository.add(empBasicPenNumInfor);
        }
        if(empAddChangeInfoDto.isUpdateEmpAddChangeInfo()){
            mEmpAddChangeInfoRepository.update(empAddChangeInfo);
            return;
        }
        mEmpAddChangeInfoRepository.add(empAddChangeInfo);

    }
}
