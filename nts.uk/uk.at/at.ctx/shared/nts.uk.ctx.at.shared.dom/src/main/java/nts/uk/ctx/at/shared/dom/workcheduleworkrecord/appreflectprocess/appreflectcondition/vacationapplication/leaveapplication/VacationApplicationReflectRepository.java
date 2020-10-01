package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import java.util.Optional;

public interface VacationApplicationReflectRepository {
    Optional<VacationApplicationReflect> findReflectByCompanyId(String companyId);
}
