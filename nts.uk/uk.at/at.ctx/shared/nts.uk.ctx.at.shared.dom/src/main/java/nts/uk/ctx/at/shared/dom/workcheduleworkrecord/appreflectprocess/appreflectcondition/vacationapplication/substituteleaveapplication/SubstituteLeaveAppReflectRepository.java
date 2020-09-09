package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.substituteleaveapplication;

import java.util.Optional;

public interface SubstituteLeaveAppReflectRepository {
    Optional<SubstituteLeaveAppReflect> findSubLeaveAppReflectByCompany(String companyId);
}
