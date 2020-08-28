package nts.uk.ctx.at.shared.infra.repository.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWorkRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAppReflectOtHdWorkRepository extends JpaRepository implements AppReflectOtHdWorkRepository {
    @Override
    public Optional<AppReflectOtHdWork> findByCompanyId(String companyId) {
        return Optional.empty();
    }

    @Override
    public void save(AppReflectOtHdWork domain) {

    }
}
