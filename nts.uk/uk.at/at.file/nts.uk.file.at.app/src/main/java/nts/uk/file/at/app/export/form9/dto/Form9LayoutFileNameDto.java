package nts.uk.file.at.app.export.form9.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;

import java.util.Optional;

@Data
@AllArgsConstructor
public class Form9LayoutFileNameDto {
    private Form9Layout form9Layout;

    private String fileName;
}
