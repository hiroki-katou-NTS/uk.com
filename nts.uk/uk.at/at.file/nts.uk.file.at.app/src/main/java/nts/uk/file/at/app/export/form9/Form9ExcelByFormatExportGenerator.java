package nts.uk.file.at.app.export.form9;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface Form9ExcelByFormatExportGenerator {
    void generate(FileGeneratorContext context, Form9ExcelByFormatDataSource dataSource, Form9ExcelByFormatQuery query);
}
