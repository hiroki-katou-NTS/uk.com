package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LossNotificationInformation {
    List<InsLossDataExport> healthInsLoss;
    List<InsLossDataExport> welfPenInsLoss;
    List<SocialInsuranceOffice> socialInsuranceOffice;
    NotificationOfLossInsExport socialInsurNotiCreateSet;
    GeneralDate baseDate;
    CompanyInfor company;
}
