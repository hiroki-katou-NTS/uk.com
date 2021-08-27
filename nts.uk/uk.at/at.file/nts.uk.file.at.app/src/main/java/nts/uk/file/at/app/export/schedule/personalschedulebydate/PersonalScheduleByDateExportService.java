package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PersonalScheduleByDateExportService extends ExportService<PersonalScheduleByDateQuery> {

    @Inject
    private CreatePersonalScheduleByDateExportQuery exportQuery;

    @Inject
    private PersonalScheduleByDateExportGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<PersonalScheduleByDateQuery> exportServiceContext) {
        val query = exportServiceContext.getQuery();

        PersonalScheduleByDateDataSource dataSource = this.exportQuery.get(
                query.getOrgUnit(),
                query.getOrgId(),
                query.getBaseDate(),
                query.getSortedEmployeeIds(),
                query.isDisplayActual(),
                query.isGraphVacationDisplay(),
                query.isDoubleWorkDisplay()
        );

        this.exportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource);
    }
}
