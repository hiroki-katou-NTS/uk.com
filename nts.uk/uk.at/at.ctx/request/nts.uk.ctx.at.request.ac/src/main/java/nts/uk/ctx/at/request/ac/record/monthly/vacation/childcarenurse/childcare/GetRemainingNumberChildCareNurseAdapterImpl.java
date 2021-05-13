package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.childcare;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.ImportedClassConverter;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCareNurseAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.shr.com.context.AppContexts;
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
            Optional<List<TempChildCareNurseManagementImport>> tempChildCareDataforOverWriteList,
            Optional<ChildCareNursePeriodImport> prevChildCareLeave,
            Optional<CreateAtr> createAtr,
            Optional<DatePeriod> periodOverWrite) {

    		List<TempChildCareNurseManagementExport> tempChildCareNurseManagementExportList
    			= tempChildCareDataforOverWriteList.isPresent()
	                ? tempChildCareDataforOverWriteList.get().stream().map(ImportedClassConverter::tempChildCareNurseManagementImportToExport).collect(Collectors.toList())
	                : new ArrayList<TempChildCareNurseManagementExport>();

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
                tempChildCareNurseManagementExportList,
                childCareNursePeriodExport,
                createAtr,
                periodOverWrite);

        return ImportedClassConverter.exportToImport(result);
    }


}
