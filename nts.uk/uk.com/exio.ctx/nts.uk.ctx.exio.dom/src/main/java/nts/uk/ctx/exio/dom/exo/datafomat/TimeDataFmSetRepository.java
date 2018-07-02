package nts.uk.ctx.exio.dom.exo.datafomat;

import java.util.Optional;
import java.util.List;

/**
* 時間型データ形式設定
*/
public interface TimeDataFmSetRepository
{

    List<TimeDataFmSet> getAllTimeDataFmSet();

    Optional<TimeDataFmSet> getTimeDataFmSetById();

    void add(TimeDataFmSet domain);

    void update(TimeDataFmSet domain);

    void remove();

}
