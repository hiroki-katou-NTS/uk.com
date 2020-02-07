package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;

import java.util.List;

@Builder
@Getter
public class ExportDataCsv {

    private List<String> lstHeader;

    private List<SocialInsurancePrefectureInformation> infor;

    private SocialInsurNotiCreateSet ins;

    private List<GuaByTheInsurExportDto> listContent;

    private List<PensionOfficeDataExport> pensionOfficeData;

    private List<EmpPenFundSubData> empPenFundSub;

    private GeneralDate baseDate;

    private CompanyInfor company;

    private GeneralDate  startDate;
    private GeneralDate endDate;


}