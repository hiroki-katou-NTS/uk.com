package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Builder;

import java.util.List;

@Builder
public class ExportDataCsv {

    /** The lst header. */
    public List<String> lstHeader;

    /** The lst error. */
    public List<GuaByTheInsurExportDto> listContent;


}