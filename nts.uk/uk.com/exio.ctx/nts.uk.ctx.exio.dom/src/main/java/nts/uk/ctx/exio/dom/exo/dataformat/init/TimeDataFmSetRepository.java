package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.List;
import java.util.Optional;

/**
* 時間型データ形式設定
*/
public interface TimeDataFmSetRepository
{

    List<TimeDataFmSet> getAllTimeDataFmSet();

    Optional<TimeDataFmSet> getTimeDataFmSetById();
    
    Optional<TimeDataFmSet> getTimeDataFmSetByCid(String cid);

    void add(TimeDataFmSet domain);

    void update(TimeDataFmSet domain);

    void remove();

}
