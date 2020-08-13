package nts.uk.screen.at.infra.reservation;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.reservation.BentoMenuDto;
import nts.uk.screen.at.app.reservation.BentoMenuScreenRepository;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaBentoMenuScreenRepository extends JpaRepository implements BentoMenuScreenRepository {

    private static final String SELECT;

    private static final String FIND_BENTO_MENU_DATE;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT NEW " + BentoMenuDto.class.getName());
        builderString.append(
                "( a.reservationFrameName1, a.reservationStartTime1, a.reservationEndTime1, ");
        builderString.append(" a.reservationFrameName2, a.reservationStartTime2, a.reservationEndTime2 ) ");
        builderString.append(" FROM KrcmtBentoMenu a JOIN KrcmtBentoMenuHist b ON a.pk.histID = b.pk.histID AND a.pk.companyID = b.pk.companyID ");
//        builderString.append(" LEFT JOIN KrcmtBento c ON a.pk.histID = c.pk.histID AND a.KrcmtBentoMenuPK.companyID = c.KrcmtBentoPK.companyID ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE b.pk.companyID = :companyId AND b.endDate = :date ");
        FIND_BENTO_MENU_DATE = builderString.toString();

    }

    @Override
    public BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date) {
        return this.queryProxy().query(FIND_BENTO_MENU_DATE, BentoMenuDto.class)
                .setParameter("companyId", companyId).setParameter("date", date).getSingleOrNull();
    }
}
