package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.OccAccIsHis;

import java.util.Optional;
import java.util.List;

/**
* 労災保険履歴
*/
public interface OccAccIsHisRepository
{

    List<OccAccIsHis> getAllOccAccIsHis();

    Optional<OccAccIsHis> getOccAccIsHisById(String cid, String hisId);

    void add(OccAccIsHis domain);

    void update(OccAccIsHis domain);

    void remove(String cid, String hisId);

}
