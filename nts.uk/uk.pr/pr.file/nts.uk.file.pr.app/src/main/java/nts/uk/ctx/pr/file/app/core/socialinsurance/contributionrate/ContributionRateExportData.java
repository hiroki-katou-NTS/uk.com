package nts.uk.ctx.pr.file.app.core.socialinsurance.contributionrate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributionRateExportData {
    private List<Object[]> data;
    private String companyName;
}
