package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LossNotificationInformation {
    List<InsLossDataExport> healthInsLoss;
    List<InsLossDataExport> welfPenInsLoss;
    NotificationOfLossInsExport socialInsurNotiCreateSet;
}
