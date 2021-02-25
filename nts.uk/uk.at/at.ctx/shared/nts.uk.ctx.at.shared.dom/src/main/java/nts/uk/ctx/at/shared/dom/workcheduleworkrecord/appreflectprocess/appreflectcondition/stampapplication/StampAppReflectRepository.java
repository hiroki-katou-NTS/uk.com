package nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication;

import java.util.Optional;

public interface StampAppReflectRepository {
    Optional<StampAppReflect> findReflectByCompanyId(String companyId);
}
