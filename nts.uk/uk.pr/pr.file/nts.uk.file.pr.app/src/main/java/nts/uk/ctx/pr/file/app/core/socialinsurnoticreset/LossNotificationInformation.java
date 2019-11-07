package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LossNotificationInformation {
    List<InsLossDataExport> healthInsLoss;
    List<InsLossDataExport> welfPenInsLoss;
    List<PensFundSubmissData> healthInsAssociationData;
    SocialInsurNotiCreateSet socialInsurNotiCreateSet;
    GeneralDate baseDate;
    CompanyInfor company;
    List<SocialInsurancePrefectureInformation> infor;
}
