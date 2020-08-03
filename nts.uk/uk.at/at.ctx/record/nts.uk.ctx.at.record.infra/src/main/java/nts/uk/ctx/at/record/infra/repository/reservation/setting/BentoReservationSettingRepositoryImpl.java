package nts.uk.ctx.at.record.infra.repository.reservation.setting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.infra.entity.reservation.setting.KrcmtBentoReservationSetting;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class BentoReservationSettingRepositoryImpl extends JpaRepository implements BentoReservationSettingRepository {

    private static final String FIND;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT e ");
        builderString.append(" FROM KrcmtBentoReservationSetting e ");
        builderString.append(" WHERE e.KrcmtBentoReservationSetting.companyId = :companyId");

        FIND = builderString.toString();
    }

    @Override
    public Optional<BentoReservationSetting> findByCId(String companyId) {
        return this.queryProxy().query(FIND, KrcmtBentoReservationSetting.class)
                .setParameter("companyId", companyId)
                .getSingle(c -> KrcmtBentoReservationSetting.toDomain(c));
    }

}
