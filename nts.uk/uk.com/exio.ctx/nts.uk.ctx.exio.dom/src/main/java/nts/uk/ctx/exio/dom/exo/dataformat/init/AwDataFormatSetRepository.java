package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;
import java.util.List;

/**
* 在職区分型データ形式設定
*/
public interface AwDataFormatSetRepository
{

    List<AwDataFormatSet> getAllAwDataFormatSet();

    Optional<AwDataFormatSet> getAwDataFormatSetById(String cid);

    void add(AwDataFormatSet domain);

    void update(AwDataFormatSet domain);

    void remove();

}
