package nts.uk.ctx.at.record.ac.request.application.approvalstatus;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.request.application.approvalstatus.*;
import nts.uk.ctx.at.request.pub.application.approvalstatus.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ReflectionStatusAdapterImpl implements ReflectionStatusAdapter {

    @Inject
    private ReflectionStatusPub reflectionStatusPub;

    @Override
    public Map<String, List<ApplicationDateImport>> getAppByEmployeeDate(List<String> employeeIDS, DatePeriod period) {
        return reflectionStatusPub.getAppByEmployeeDate(employeeIDS, period).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> convertAppDate(x.getValue())));
    }

    private List<ApplicationDateImport> convertAppDate(List<ApplicationDateExport> apps) {
        return apps.stream().map(x -> new ApplicationDateImport(x.getAppDate(), convertApp(x.getApplication())))
                .collect(Collectors.toList());
    }

    private ApplicationImport convertApp(ApplicationExport app) {
        return new ApplicationImport(
                app.getAppID(),
                app.getPrePostAtr(),
                app.getEmployeeID(),
                app.getAppType(),
                app.getAppDate(),
                app.getEnteredPersonID(),
                app.getInputDate(),
                convertReflectionStatus(app.getReflectionStatus()),
                app.getOpStampRequestMode(),
                app.getOpReversionReason(),
                app.getOpAppStartDate(),
                app.getOpAppEndDate(),
                app.getOpAppReason(),
                app.getOpAppStandardReasonCD());
    }

    private ReflectionStatusImport convertReflectionStatus(ReflectionStatusExport reflectionStatus) {
        return new ReflectionStatusImport(
                reflectionStatus
                        .getListReflectionStatusOfDayExport().stream()
                        .map(x -> new ReflectionStatusOfDayImport(
                                x.getActualReflectStatus(),
                                x.getScheReflectStatus(),
                                x.getTargetDate(),
                                x.getOpUpdateStatusAppReflect().isPresent() ? Optional.of(convertDailyAttendance(x.getOpUpdateStatusAppReflect().get())) : Optional.empty(),
                                x.getOpUpdateStatusAppReflect().isPresent() ? Optional.of(convertDailyAttendance(x.getOpUpdateStatusAppCancel().get())) : Optional.empty()
                        ))
                        .collect(Collectors.toList()));
    }

    private DailyAttendanceUpdateStatusImport convertDailyAttendance(DailyAttendanceUpdateStatusExport att) {
        return new DailyAttendanceUpdateStatusImport(
                att.getOpActualReflectDateTime(),
                att.getOpScheReflectDateTime(),
                att.getOpReasonActualCantReflect(),
                att.getOpReasonScheCantReflect()
        );
    }
}
