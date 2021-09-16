package nts.uk.file.at.app.export.schedule.personalScheduleByIndividual;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface PersonalScheduleByIndividualExportGenerator {
    void generate(FileGeneratorContext context, PersonalScheduleIndividualDataSource dataSource);
}
