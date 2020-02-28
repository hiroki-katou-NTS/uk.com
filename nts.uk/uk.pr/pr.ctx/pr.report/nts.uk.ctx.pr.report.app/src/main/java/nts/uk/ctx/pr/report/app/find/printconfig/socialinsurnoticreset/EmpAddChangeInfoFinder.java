package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfoRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


@Stateless
public class EmpAddChangeInfoFinder {
    @Inject
    EmpAddChangeInfoRepository mEmpAddChangeInfoRepository;

    @Inject
    EmpBasicPenNumInforRepository mEmpBasicPenNumInforRepository;


    public EmpAddChangeInfoDto getEmpAddChangeInfoRepository(String employeeId){
        Optional<EmpAddChangeInfo> resulf =  mEmpAddChangeInfoRepository.getEmpAddChangeInfoById(employeeId, AppContexts.user().companyId());
        Optional<EmpBasicPenNumInfor> empBasicPenNumInfor =  mEmpBasicPenNumInforRepository.getEmpBasicPenNumInforById(employeeId);
        String basicPenNumber = empBasicPenNumInfor.isPresent() ? (empBasicPenNumInfor.get().getBasicPenNumber().isPresent() ? empBasicPenNumInfor.get().getBasicPenNumber().get().v() : null) : null;
        if(resulf.isPresent()){
            return resulf.map(e -> new EmpAddChangeInfoDto(
                    e.getSid(),
                    e.getPersonalSet().getShortResident(),e.getPersonalSet().getLivingAbroadAtr(),e.getPersonalSet().getResidenceOtherResidentAtr(),e.getPersonalSet().getOtherAtr(),e.getPersonalSet().getOtherReason().isPresent() ? e.getPersonalSet().getOtherReason().get().toString() : null,
                    e.getSpouse().getShortResident(),e.getSpouse().getLivingAbroadAtr(),e.getSpouse().getResidenceOtherResidentAtr(),e.getSpouse().getOtherAtr(),e.getSpouse().getOtherReason().isPresent() ?  e.getSpouse().getOtherReason().get().toString(): null,
                    basicPenNumber
            )).get();
        }
        return EmpAddChangeInfoDto.builder()
                .basicPenNumber(basicPenNumber)
                .shortResidentAtr(0)
                .livingAbroadAtr(0)
                .residenceOtherResidentAtr(0)
                .otherAtr(0)
                .otherReason(null)
                .spouseShortResidentAtr(0)
                .spouseLivingAbroadAtr(0)
                .spouseResidenceOtherResidentAtr(0)
                .spouseOtherAtr(0)
                .spouseOtherReason(null)
                .build();

    }

}