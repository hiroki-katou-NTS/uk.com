package nts.uk.ctx.exio.dom.exo.datafomat;

import java.util.Optional;
import java.util.List;

/**
* 文字型データ形式設定
*/
public interface ChacDataFmSetRepository
{

    List<ChacDataFmSet> getAllChacDataFmSet();

    Optional<ChacDataFmSet> getChacDataFmSetById();

    void add(ChacDataFmSet domain);

    void update(ChacDataFmSet domain);

    void remove();

}
