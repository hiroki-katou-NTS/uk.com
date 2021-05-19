package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.subleaveapp;

import java.util.Optional;

public interface SubLeaveAppReflectRepository {
    Optional<SubstituteLeaveAppReflect> findSubLeaveAppReflectByCompany(String companyId);
}
