package nts.uk.file.at.app.export.bento;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;

public interface OrderInfoExcelGenerator {
    void generate(FileGeneratorContext generatorContext, OrderInfoDto data);
}
