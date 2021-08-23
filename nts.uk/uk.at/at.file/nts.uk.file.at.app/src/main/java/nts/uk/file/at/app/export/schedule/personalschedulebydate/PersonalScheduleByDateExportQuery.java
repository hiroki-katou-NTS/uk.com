package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.shr.com.company.CompanyAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class PersonalScheduleByDateExportQuery {
    @Inject
    private CompanyAdapter company;

    @Inject
    private EmployeeInformationAdapter employeeInfoAdapter;

    public PersonalScheduleByDateDataSource get(int orgUnit, String orgId, GeneralDate baseDate, List<String> sortedEmployeeIds) {


        return null;
    }
}
