package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.NameOfEachBusiness;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBus;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusinessName;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccInsurBus;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccInsurBusPk;


@Stateless
public class JpaOccAccInsurBusRepository extends JpaRepository implements OccAccInsurBusRepository {

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
                    entity.useAtr,
                    Optional.ofNullable(new OccAccInsurBusinessName(entity.name))));
        });
        return nameOfEachBusinessList;
    }

    @Override
    public void update(OccAccInsurBus domain) {
        this.commandProxy().updateAll(
                QpbmtOccAccInsurBus.toEntity(domain));
    }

    @Override
    public void remove(String cid, int occAccInsurBusNo){
        this.commandProxy().remove(QpbmtOccAccInsurBus.class, new QpbmtOccAccInsurBusPk(cid, occAccInsurBusNo));
    }
}
