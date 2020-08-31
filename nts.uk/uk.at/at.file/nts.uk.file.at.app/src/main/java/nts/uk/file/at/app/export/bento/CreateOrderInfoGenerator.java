package nts.uk.file.at.app.export.bento;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface CreateOrderInfoGenerator {
    void generate(FileGeneratorContext generatorContext, OrderInfoExportData data);
}
