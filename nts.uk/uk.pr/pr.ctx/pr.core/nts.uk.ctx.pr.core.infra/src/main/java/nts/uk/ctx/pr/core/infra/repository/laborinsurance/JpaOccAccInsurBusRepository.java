package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.NameOfEachBusiness;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccInsurBus;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccInsurBusRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccInsurBusinessName;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBus;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBusPk;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsHis;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaOccAccInsurBusRepository extends JpaRepository implements OccAccInsurBusRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccInsurBus f";
    private static final String SELECT_BY_KEY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccInsurBusPk.cid =:cid";


    @Override
    public Optional<OccAccInsurBus> getOccAccInsurBus(String cId) {
         List<QpbmtOccAccInsurBus> occAccInsurBus= this.queryProxy().query(SELECT_BY_KEY_CID, QpbmtOccAccInsurBus.class)
                .setParameter("cid", cId)
                .getList();
         return Optional.ofNullable(new OccAccInsurBus(cId, toDomain(occAccInsurBus)));
    }
    private List<NameOfEachBusiness> toDomain(List<QpbmtOccAccInsurBus> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        List<NameOfEachBusiness> nameOfEachBusinessList = new ArrayList<NameOfEachBusiness>();
        entities.forEach(entity -> {
            nameOfEachBusinessList.add( new NameOfEachBusiness(entity.occAccInsurBusPk.occAccInsurBusNo,
                    entity.toUse,
                    Optional.ofNullable(new OccAccInsurBusinessName(entity.name))));
        });
        return nameOfEachBusinessList;
    }

    @Override
    public void remove(String cid, int occAccInsurBusNo){
        this.commandProxy().remove(QpbmtOccAccInsurBus.class, new QpbmtOccAccInsurBusPk(cid, occAccInsurBusNo));
    }
}
