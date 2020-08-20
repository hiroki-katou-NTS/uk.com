package nts.uk.screen.at.infra.reservation;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.reservation.BentoDto;
import nts.uk.screen.at.app.reservation.BentoMenuDto;
import nts.uk.screen.at.app.reservation.BentoMenuScreenRepository;
import nts.uk.screen.at.app.reservation.BentoRequest;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaBentoMenuScreenRepository extends JpaRepository implements BentoMenuScreenRepository {

    private static final String SELECT;
    private static final String SELECTBENTO;

    private static final String FIND_BENTO_MENU_DATE;

    private static final String FIND_BENTO_BY_HIS;

    private static final String FIND_BENTO_BY_MAXDATE;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT NEW " + BentoMenuDto.class.getName());
        builderString.append(
                "( a.reservationFrameName1, a.reservationStartTime1, a.reservationEndTime1, ");
        builderString.append(" a.reservationFrameName2, a.reservationStartTime2, a.reservationEndTime2 ) ");
        builderString.append(" FROM KrcmtBentoMenu a JOIN KrcmtBentoMenuHist b ON a.pk.histID = b.pk.histID AND a.pk.companyID = b.pk.companyID ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE b.pk.companyID = :companyID AND b.endDate = :date ");
        FIND_BENTO_MENU_DATE = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT NEW " + BentoDto.class.getName());
        builderString.append(
                "( a.reservationFrameName1, a.reservationStartTime1, a.reservationEndTime1, a.reservationFrameName2," +
                        " a.reservationStartTime2, a.reservationEndTime2, b.startDate, b.endDate, c.pk.frameNo, c.bentoName," +
                        " c.unitName, c.price1, c.price2, c.reservationAtr1, c.reservationAtr2, c.workLocationCode, d.workLocationName)");
        builderString.append(" FROM KrcmtBentoMenu a JOIN KrcmtBentoMenuHist b ON a.pk.histID = b.pk.histID AND a.pk.companyID = b.pk.companyID ");
        builderString.append(" LEFT JOIN KrcmtBento c ON a.pk.histID = c.pk.histID AND a.pk.companyID = c.pk.companyID ");
        builderString.append(" LEFT JOIN KwlmtWorkLocation d ON c.pk.companyID = d.kwlmtWorkLocationPK.companyID AND c.workLocationCode = d.kwlmtWorkLocationPK.workLocationCD ");
        SELECTBENTO = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECTBENTO);
        builderString.append(" WHERE b.pk.companyID = :companyID AND b.endDate = :date ");
        FIND_BENTO_BY_HIS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECTBENTO);
        builderString.append(" WHERE b.pk.companyID = :companyID AND b.pk.histID = :histId ");
        FIND_BENTO_BY_MAXDATE = builderString.toString();

    }

    @Override
    public BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date) {
        return this.queryProxy().query(FIND_BENTO_MENU_DATE, BentoMenuDto.class)
                .setParameter("companyID", companyId).setParameter("date", date).getSingleOrNull();
    }

    @Override
    public List<BentoDto> findDataBento(String companyId, GeneralDate date,BentoRequest request) {
        if (request.getHistId() !=null){
            return this.queryProxy().query(FIND_BENTO_BY_MAXDATE, BentoDto.class)
                    .setParameter("companyID", companyId).setParameter("histId", request.getHistId()).getList();
        }
        return this.queryProxy().query(FIND_BENTO_BY_HIS, BentoDto.class)
                .setParameter("companyID", companyId).setParameter("date", date).getList();
    }
}
