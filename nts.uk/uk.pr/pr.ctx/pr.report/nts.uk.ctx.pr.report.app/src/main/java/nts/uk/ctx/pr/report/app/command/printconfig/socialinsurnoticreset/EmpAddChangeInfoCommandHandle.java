package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

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
public class EmpAddChangeInfoCommandHandle {
    @Inject
    EmpAddChangeInfoRepository mEmpAddChangeInfoRepository;

    @Inject
    EmpBasicPenNumInforRepository mEmpBasicPenNumInforRepository;

    public void registerEmpAddChangeInfo(EmpAddChangeInfoCommand empAddChangeInfoDto){
        EmpAddChangeInfo empAddChangeInfo =  new EmpAddChangeInfo(empAddChangeInfoDto.getSid(),
                new AddChangeSetting(
                        empAddChangeInfoDto.shortResidentAtr,
                        empAddChangeInfoDto.livingAbroadAtr,
                        empAddChangeInfoDto.residenceOtherResidentAtr,
                        empAddChangeInfoDto.otherAtr,
                        empAddChangeInfoDto.otherReason
                ),
                new AddChangeSetting(
                        empAddChangeInfoDto.spouseShortResidentAtr,
                        empAddChangeInfoDto.spouseLivingAbroadAtr,
                        empAddChangeInfoDto.spouseResidenceOtherResidentAtr,
                        empAddChangeInfoDto.spouseOtherAtr,
                        empAddChangeInfoDto.spouseOtherReason
                )
        );
        EmpBasicPenNumInfor empBasicPenNumInfor = new EmpBasicPenNumInfor(
                empAddChangeInfoDto.getSid(),
                empAddChangeInfoDto.basicPenNumber
        );

        if(empAddChangeInfoDto.isUpdateEmpBasicPenNumInfor){
            mEmpBasicPenNumInforRepository.update(empBasicPenNumInfor);
        }
        else {
            mEmpBasicPenNumInforRepository.add(empBasicPenNumInfor);
        }
        if(empAddChangeInfoDto.isUpdateEmpAddChangeInfo){
            mEmpAddChangeInfoRepository.update(empAddChangeInfo);
            return;
        }
        mEmpAddChangeInfoRepository.add(empAddChangeInfo);
        return;

    }
}
