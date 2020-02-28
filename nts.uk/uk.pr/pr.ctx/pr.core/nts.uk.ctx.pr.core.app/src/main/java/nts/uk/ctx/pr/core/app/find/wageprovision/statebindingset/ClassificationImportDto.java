package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationImport;

@Value
public class ClassificationImportDto {
    private String classificationCode;
    private String classificationName;

    public ClassificationImportDto(ClassificationImport domain) {
        this.classificationCode = domain.getClassificationCode();
        this.classificationName = domain.getClassificationName();
    }
}
