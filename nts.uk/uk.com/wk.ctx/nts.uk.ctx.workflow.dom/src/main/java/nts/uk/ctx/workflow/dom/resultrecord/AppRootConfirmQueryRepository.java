package nts.uk.ctx.workflow.dom.resultrecord;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface AppRootConfirmQueryRepository {

	AppRootIntermForQuery.List queryInterm(String companyId, List<String> employeeIds, DatePeriod period, RecordRootType rootType);

	AppRootRecordConfirmForQuery.List queryConfirm(String companyId, List<String> employeeIds, DatePeriod period, RecordRootType rootType);
}
