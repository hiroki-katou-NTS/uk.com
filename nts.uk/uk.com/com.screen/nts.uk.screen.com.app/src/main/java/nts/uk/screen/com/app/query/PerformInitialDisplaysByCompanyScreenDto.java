package nts.uk.screen.com.app.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class PerformInitialDisplaysByCompanyScreenDto {
    /**
     * 会社ID
     */
    private String companyId;

    private String companyName;

    private List<PerformInitialDetail> scheduleHistory;


}
