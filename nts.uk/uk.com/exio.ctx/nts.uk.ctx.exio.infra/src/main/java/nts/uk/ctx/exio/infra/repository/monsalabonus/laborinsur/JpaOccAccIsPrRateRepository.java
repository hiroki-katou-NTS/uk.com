package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRate;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsPrRateRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsPrRate;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsPrRatePk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaOccAccIsPrRateRepository extends JpaRepository implements OccAccIsPrRateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIsPrRate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsPrRatePk.ocAcIsPrRtId =:ocAcIsPrRtId AND  f.occAccIsPrRatePk.hisId =:hisId ";



    @Override
    public void remove(String ocAcIsPrRtId, String hisId){
        this.commandProxy().remove(QpbmtOccAccIsPrRate.class, new QpbmtOccAccIsPrRatePk(ocAcIsPrRtId, hisId));
    }
}
