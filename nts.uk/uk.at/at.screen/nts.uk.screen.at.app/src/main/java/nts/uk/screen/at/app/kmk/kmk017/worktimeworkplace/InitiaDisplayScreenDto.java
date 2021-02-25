package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.screen.at.app.kmk.kmk008.company.AgreementTimeOfCompanyDto;
import nts.uk.screen.at.app.kmk.kmk008.company.RequestCompany;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitiaDisplayScreenDto {

    private Integer useATR;

    private List<WorkTimeSettingDto> workTimeSettingDto;

    private List<WorkTimeWorkplaceDto> workTimeWorkplaceDto;

}
