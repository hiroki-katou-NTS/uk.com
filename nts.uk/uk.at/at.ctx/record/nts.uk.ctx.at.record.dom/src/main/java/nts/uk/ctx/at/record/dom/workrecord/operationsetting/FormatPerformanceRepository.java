package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import java.util.Optional;
import java.util.List;

/**
* 実績修正画面で利用するフォーマット
*/
public interface FormatPerformanceRepository
{

    List<FormatPerformance> getAllFormatPerformance();

    Optional<FormatPerformance> getFormatPerformanceById(String cid);

    void add(FormatPerformance domain);

    void update(FormatPerformance domain);

    void remove(String cid);

}
