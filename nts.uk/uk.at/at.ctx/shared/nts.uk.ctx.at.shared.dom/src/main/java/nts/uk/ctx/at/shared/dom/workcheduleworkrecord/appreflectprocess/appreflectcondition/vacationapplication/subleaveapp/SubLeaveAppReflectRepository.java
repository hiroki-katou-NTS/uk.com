package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import java.util.Optional;

public interface SubLeaveAppReflectRepository {
    Optional<SubstituteLeaveAppReflect> findSubLeaveAppReflectByCompany(String companyId);
}
