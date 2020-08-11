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
                "( a.KrcmtBentoMenuPK.companyID, a.KrcmtBentoMenuPK.histID, a.contractCD, a.reservationFrameName1, a.reservationStartTime1, a.reservationEndTime1, ");
        builderString.append(" a.reservationFrameName2, a.reservationStartTime2, a.reservationEndTime2, b.startDate, b.endDate ) ");
        builderString.append(" FROM KrcmtBentoMenu a JOIN KrcmtBentoMenuHist b ON a.histID = b.histID AND a.companyID = b.companyID ");
        builderString.append(" LEFT JOIN KrcmtBento c ON a.histID = c.histID AND a.companyID = c.companyID ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE b.companyId = :companyId AND b.endDate = :date ");
        FIND_BENTO_MENU_DATE = builderString.toString();

    }

    @Override
    public BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date) {
        return this.queryProxy().query(FIND_BENTO_MENU_DATE, BentoMenuDto.class).setParameter("companyId", companyId).setParameter("date", date).getSingleOrNull();
    }
}
