package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.CurrProcessDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.CurrProcessDateFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportFinder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Objects;

@Stateless
public class EmployeeInformationAcquisitionProcessingFinder {

    @Inject
    PerProcesClsSetFinder perProcesClsSetFinder;

    @Inject
    CurrProcessDateFinder currProcessDateFinder;

    @Inject
    SetDaySupportFinder setDaySupportFinder;

    private static final int DEFAULT_PROCESS_CATE_NO = 1;


    public SetDaySupportDto getEmpExtRefDate() {
        PerProcesClsSetDto perProcesClsSetDto = perProcesClsSetFinder.getPerProcesClsSetbyUIDAndCID();
        CurrProcessDateDto currProcessDateDto = currProcessDateFinder.getCurrProcessDateByID(Objects.isNull(perProcesClsSetDto) ? DEFAULT_PROCESS_CATE_NO : perProcesClsSetDto.getProcessCateNo());
        SetDaySupportDto setDaySupportDto = setDaySupportFinder.getEmployeeExtractionReferenceDateByIdAndProcessDate(currProcessDateDto.getProcessCateNo(), currProcessDateDto.getGiveCurrTreatYear());
        return setDaySupportDto;
    }
}
