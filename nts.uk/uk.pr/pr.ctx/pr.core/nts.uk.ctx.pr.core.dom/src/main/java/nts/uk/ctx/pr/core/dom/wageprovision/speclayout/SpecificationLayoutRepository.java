package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト
*/
public interface SpecificationLayoutRepository
{
    List<SpecificationLayout> getSpecCode(String cid, String salaryCd, int startYearMonth);

    List<SpecificationLayout> getAllSpecificationLayout();

    Optional<SpecificationLayout> getSpecificationLayoutById(String cid, String specCd);

    void add(SpecificationLayout domain);

    void update(SpecificationLayout domain);

    void remove(String cid, String specCd);

}
