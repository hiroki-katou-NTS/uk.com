package nts.uk.screen.at.infra.reservation;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.reservation.*;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaBentoMenuScreenRepository extends JpaRepository implements BentoMenuScreenRepository {

    private static final String SELECT;
    private static final String SELECTBENTO;

    private static final String FIND_BENTO_MENU_DATE;

    private static final String FIND_BENTO_BY_HIS;

    private static final String FIND_BENTO_BY_MAXDATE;

    private static final String SELECT_WORKLOCATION;

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
        builderString.append(" SELECT NEW " + BentomenuJoinBentoDto.class.getName());
        builderString.append(
                "( a.reservationFrameName1, a.reservationStartTime1, a.reservationEndTime1, a.reservationFrameName2," +
                        " a.reservationStartTime2, a.reservationEndTime2, b.startDate, b.endDate, c.pk.frameNo, c.bentoName," +
                        " c.unitName, c.price1, c.price2, c.reservationAtr1, c.reservationAtr2, c.workLocationCode, d.workLocationName)");
        builderString.append(" FROM KrcmtBentoMenu a JOIN KrcmtBentoMenuHist b ON a.pk.histID = b.pk.histID AND a.pk.companyID = b.pk.companyID ");
        builderString.append(" LEFT JOIN KrcmtBento c ON a.pk.histID = c.pk.histID AND a.pk.companyID = c.pk.companyID ");
        builderString.append(" LEFT JOIN KrcmtWorkLocation d ON c.pk.companyID = d.krcmtWorkLocationPK.companyID AND c.workLocationCode = d.krcmtWorkLocationPK.workLocationCD ");
        SELECTBENTO = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECTBENTO);
        builderString.append(" WHERE b.pk.companyID = :companyID AND b.endDate = :date ");
        FIND_BENTO_BY_HIS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECTBENTO);
        builderString.append(" WHERE b.pk.companyID = :companyID AND b.pk.histID = :histId ");
        FIND_BENTO_BY_MAXDATE = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT NEW " + WorkLocationDto.class.getName());
        builderString.append(
                "( a.krcmtWorkLocationPK.workLocationCD, a.workLocationName )");
        builderString.append(" FROM KrcmtWorkLocation a ");
        builderString.append(" WHERE a.krcmtWorkLocationPK.companyID = :companyID ");
        SELECT_WORKLOCATION = builderString.toString();
    }

    @Override
    public BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date) {
        return this.queryProxy().query(FIND_BENTO_MENU_DATE, BentoMenuDto.class)
                .setParameter("companyID", companyId).setParameter("date", date).getSingleOrNull();
    }

    @Override
    public List<BentomenuJoinBentoDto> findDataBento(String companyId, GeneralDate date, BentoRequest request) {
        if (request.getHistId() !=null){
            return this.queryProxy().query(FIND_BENTO_BY_MAXDATE, BentomenuJoinBentoDto.class)
                    .setParameter("companyID", companyId).setParameter("histId", request.getHistId()).getList();
        }
        return this.queryProxy().query(FIND_BENTO_BY_HIS, BentomenuJoinBentoDto.class)
                .setParameter("companyID", companyId).setParameter("date", date).getList();
    }

    @Override
    public List<WorkLocationDto> findDataWorkLocation(String companyId) {
        return this.queryProxy().query(SELECT_WORKLOCATION, WorkLocationDto.class)
                .setParameter("companyID", companyId).getList();
    }
}
