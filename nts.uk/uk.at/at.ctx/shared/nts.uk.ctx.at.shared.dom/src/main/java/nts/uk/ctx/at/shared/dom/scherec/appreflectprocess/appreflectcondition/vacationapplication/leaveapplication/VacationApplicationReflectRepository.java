package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication;

import java.util.Optional;

public interface VacationApplicationReflectRepository {
    Optional<VacationApplicationReflect> findReflectByCompanyId(String companyId);
}
