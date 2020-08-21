package nts.uk.ctx.at.shared.infra.repository.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOvertimeHolidayWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOvertimeHolidayWorkRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAppReflectOvertimeHolidayWorkRepository extends JpaRepository implements AppReflectOvertimeHolidayWorkRepository {
    @Override
    public Optional<AppReflectOvertimeHolidayWork> findByCompanyId(String companyId) {
        return Optional.empty();
    }

    @Override
    public void save(AppReflectOvertimeHolidayWork domain) {

    }
}
