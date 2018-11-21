package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト設定
*/
public interface SpecificationLayoutSetRepository
{

    List<SpecificationLayoutSet> getAllSpecificationLayoutSet();

    Optional<SpecificationLayoutSet> getSpecificationLayoutSetById(String histId);

    void add(SpecificationLayoutSet domain);

    void update(SpecificationLayoutSet domain);

    void remove(String histId);

}
