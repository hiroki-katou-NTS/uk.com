package nts.uk.ctx.pr.core.dom.laborinsurance;


import java.util.Optional;
import java.util.List;

/**
* 労災保険履歴
*/
public interface OccAccIsHisRepository
{

    Optional<OccAccIsHis> getAllOccAccIsHisByCid(String cid);


    void add(OccAccIsHis domain);

    void update(OccAccIsHis domain);

    void remove(String cid, String hisId);

}
