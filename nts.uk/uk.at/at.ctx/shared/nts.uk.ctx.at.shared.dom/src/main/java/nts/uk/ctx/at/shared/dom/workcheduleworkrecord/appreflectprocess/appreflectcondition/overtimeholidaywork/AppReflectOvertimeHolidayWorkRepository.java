package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.Optional;

public interface AppReflectOvertimeHolidayWorkRepository {
    Optional<AppReflectOvertimeHolidayWork> findByCompanyId(String companyId);
    void save(AppReflectOvertimeHolidayWork domain);
}
