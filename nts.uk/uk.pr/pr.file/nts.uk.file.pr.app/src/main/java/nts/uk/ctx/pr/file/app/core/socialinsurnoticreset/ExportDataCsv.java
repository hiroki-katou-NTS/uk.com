package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;

import java.util.List;

@Builder
@Getter
public class ExportDataCsv {

    /** The lst header. */
    private List<String> lstHeader;

    private CompanyInfor company;
    /** The lst error. */
    private List<GuaByTheInsurExportDto> listContent;


}