package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.substituteworkapplication;

import java.util.Optional;

public interface SubstituteWorkAppReflectRepository {
    Optional<SubstituteWorkAppReflect> findSubWorkAppReflectByCompany(String companyId);
}
