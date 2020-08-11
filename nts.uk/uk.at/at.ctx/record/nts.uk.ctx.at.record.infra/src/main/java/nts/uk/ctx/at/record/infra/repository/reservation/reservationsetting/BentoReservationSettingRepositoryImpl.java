package nts.uk.ctx.at.record.infra.repository.reservation.reservationsetting;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.infra.entity.reservation.BentoReservationSetting.KrcmtBentoReservationSetting;

import javax.ejb.Stateless;
import java.util.Optional;

import static nts.uk.ctx.at.record.infra.entity.reservation.BentoReservationSetting.KrcmtBentoReservationSetting.toDomain;

@Stateless
public class BentoReservationSettingRepositoryImpl extends JpaRepository implements BentoReservationSettingRepository {

    @Override
    public Optional<BentoReservationSetting> findByCId(String companyId) {
        String QUERY_BY_ID = "SELECT s FROM KrcmtBentoReservationSetting s WHERE s.companyID = :companyId";
        return this.queryProxy()
                .query(QUERY_BY_ID, KrcmtBentoReservationSetting.class)
                .setParameter("companyId", companyId).getSingle(x -> KrcmtBentoReservationSetting.toDomain(x));
    }

}
