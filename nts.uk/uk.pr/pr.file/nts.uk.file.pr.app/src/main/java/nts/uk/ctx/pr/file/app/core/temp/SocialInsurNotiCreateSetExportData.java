package nts.uk.ctx.pr.file.app.core.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialInsurNotiCreateSetExportData {
    private List<InsLossDataExport> welfPenInsLoss;
    private List<InsLossDataExport> healthInsLoss;
    private SocialInsurNotiCreateSetExport socialInsurNotiCreateSet;
}
