package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.childcare;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.ImportedClassConverter;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCareNurseAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
//import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetRemainingNumberChildCareNurseAdapterImpl implements GetRemainingNumberChildCareNurseAdapter {
    @Inject
    private GetRemainingNumberChildCareNursePub publisher;

    @Override
    public ChildCareNursePeriodImport getChildCareNurseRemNumWithinPeriod(
            String employeeId,
            DatePeriod period,
            InterimRemainMngMode performReferenceAtr,
            GeneralDate criteriaDate,
            Optional<Boolean> isOverWrite,
            Optional<List<TmpChildCareNurseMngWorkImport>> tempChildCareDataforOverWriteList,
            Optional<ChildCareNursePeriodImport> prevChildCareLeave,
            Optional<CreateAtr> createAtr,
            Optional<DatePeriod> periodOverWrite) {

    		List< TmpChildCareNurseMngWorkExport > tmp = tempChildCareDataforOverWriteList.isPresent()
                ? tempChildCareDataforOverWriteList.get().stream().map(ImportedClassConverter::tmpChildCareNurseMngWorkToExport).collect(Collectors.toList())
                : null;

	        Optional<ChildCareNursePeriodExport> childCareNursePeriodExport = Optional.empty();
	        if ( prevChildCareLeave.isPresent() ) {
	        	childCareNursePeriodExport = Optional.of(ImportedClassConverter.importToExport(prevChildCareLeave.get()));
	        }

        ChildCareNursePeriodExport result = publisher.getChildCareRemNumWithinPeriod(
        		AppContexts.user().companyId(),
                employeeId,
                period,
                performReferenceAtr,
                criteriaDate,
                isOverWrite,
                tmp,
                childCareNursePeriodExport,
                createAtr,
                periodOverWrite);

        return ImportedClassConverter.exportToImport(result);
    }


}
