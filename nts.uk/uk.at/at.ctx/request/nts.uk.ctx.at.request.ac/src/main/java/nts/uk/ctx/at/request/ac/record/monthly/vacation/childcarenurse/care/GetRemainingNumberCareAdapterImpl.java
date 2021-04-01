package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.care;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.ImportedClassConverter;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.care.GetRemainingNumberCareAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetRemainingNumberCareAdapterImpl implements GetRemainingNumberCareAdapter {

    @Inject
    private GetRemainingNumberCarePub publisher;

    @Override
    public ChildCareNursePeriodImport getCareRemNumWithinPeriod(
    		String companyId,
    		String employeeId,
    		DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			List<TempChildCareNurseManagementImport> tempCareDataforOverWriteList,
			Optional<ChildCareNursePeriodImport> prevCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite) {

        List< TempChildCareNurseManagementExport > tmpChildCareNurseMngWorkExportList =
        		tempCareDataforOverWriteList.stream().map(ImportedClassConverter::tempChildCareNurseManagementImportToExport).collect(Collectors.toList());

        Optional<ChildCareNursePeriodExport> childCareNursePeriodExport = Optional.empty();
        if ( prevCareLeave.isPresent() ) {
        	childCareNursePeriodExport = Optional.of(ImportedClassConverter.importToExport(prevCareLeave.get()));
        }

        ChildCareNursePeriodExport result = publisher.getCareRemNumWithinPeriod(
        		companyId,
                employeeId,
                period,
                performReferenceAtr,
                criteriaDate,
                isOverWrite,
                tmpChildCareNurseMngWorkExportList,
                childCareNursePeriodExport,
                createAtr,
                periodOverWrite);

        return ImportedClassConverter.exportToImport(result);
    }

}