package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork;

import java.util.Optional;

public interface AppReflectOtHdWorkRepository {
    Optional<AppReflectOtHdWork> findByCompanyId(String companyId);
}
