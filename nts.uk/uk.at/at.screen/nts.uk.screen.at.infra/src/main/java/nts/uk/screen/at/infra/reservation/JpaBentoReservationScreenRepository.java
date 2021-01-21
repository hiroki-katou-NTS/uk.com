package nts.uk.screen.at.infra.reservation;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.screen.at.app.reservation.BentoReservationSettingDto;
import nts.uk.screen.at.app.reservation.BentoReservationScreenRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaBentoReservationScreenRepository extends JpaRepository implements BentoReservationScreenRepository {

    private static final String SELECT_BENTO_RERSERVATION_SETTING;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + BentoReservationSettingDto.class.getName());
        stringBuilder.append(
                "(c.operationDistinction, c.referenceTime,c.contentChangeDeadline,c.contentChangeDeadlineDay, c.orderDeadline, c.monthlyResults, c.dailyResults,c.orderData ) ");
        stringBuilder.append("FROM KrcmtBentoReservationSetting c ");
        stringBuilder.append("WHERE c.companyID = :companyId ");
        SELECT_BENTO_RERSERVATION_SETTING = stringBuilder.toString();
    }

    @Override
    public BentoReservationSettingDto findDataBentoRervation(String companyId) {
        return this.queryProxy().query(SELECT_BENTO_RERSERVATION_SETTING, BentoReservationSettingDto.class).setParameter("companyId", companyId).getSingleOrNull();
    }
}
