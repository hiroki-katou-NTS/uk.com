package nts.uk.ctx.pr.core.dom.laborinsurance;


import java.util.Optional;
import java.util.List;

/**
* 労災保険履歴
*/
public interface OccAccIsHisRepository
{

    List<OccAccIsHis> getAllOccAccIsHis();
    List<OccAccIsHis> getAllOccAccIsHisByCid(String cid);

    Optional<OccAccIsHis> getOccAccIsHisById(String cid, String hisId);

    void add(OccAccIsHis domain);

    void update(OccAccIsHis domain);

    void remove(String cid, String hisId);

}
