package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

import java.util.List;

public interface RomajiNameNotiCreSetFileGenerator {
    void generate(FileGeneratorContext fileContext, RomajiNameNotification data);
}
