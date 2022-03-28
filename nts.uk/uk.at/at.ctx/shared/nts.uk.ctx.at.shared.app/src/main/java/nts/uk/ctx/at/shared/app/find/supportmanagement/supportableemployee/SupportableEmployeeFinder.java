package nts.uk.ctx.at.shared.app.find.supportmanagement.supportableemployee;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployeeRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class SupportableEmployeeFinder {
    @Inject
    private SupportableEmployeeRepository supportableEmployeeRepo;

    /**
     * 応援者を取得する
     * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援可能な社員.App.応援者を取得する.応援者を取得する
     * @param employeeIds 社員IDリスト
     * @param supportOrg 応援先組織
     * @param period 期間
     * @return List<応援可能な社員>
     */
    public List<SupportableEmployee> get(List<String> employeeIds, TargetOrgIdenInfor supportOrg, DatePeriod period) {
        val empIds = employeeIds.stream().map(EmployeeId::new).collect(Collectors.toList());

        // 1. 社員と期間を指定して取得する: List<応援可能な社員>
        val result1 = supportableEmployeeRepo.findByEmployeeIdWithPeriod(empIds, period);

        // 2. 応援先と期間を指定して取得する : List<応援可能な社員>
        val result2 = supportableEmployeeRepo.findByRecipientWithPeriod(supportOrg, period);

        return Stream.of(result1, result2).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
