package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト履歴
*/
public interface SpecificationLayoutHistRepository {

    List<SpecificationLayoutHist> getAllSpecificationLayoutHist();

    Optional<SpecificationLayoutHist> getSpecificationLayoutHistById(String cid, int specCd, String histId);

    void add(SpecificationLayoutHist domain);

    void update(SpecificationLayoutHist domain);

    void remove(String cid, int specCd, String histId);

}
