package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;
import java.util.List;

/**
* 数値型データ形式設定
*/
public interface NumberDataFmSetRepository
{

    List<NumberDataFmSet> getAllNumberDataFmSet();

    Optional<NumberDataFmSet> getNumberDataFmSetById(String cId);

    void add(NumberDataFmSet domain);

    void update(NumberDataFmSet domain);

    void remove();

}
