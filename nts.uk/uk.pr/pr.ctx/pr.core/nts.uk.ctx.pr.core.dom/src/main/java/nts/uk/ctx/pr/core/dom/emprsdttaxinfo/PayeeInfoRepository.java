package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import java.util.List;
import java.util.Optional;

/**
 * 納付先情報
 */
public interface PayeeInfoRepository {

    List<PayeeInfo> getAllPayeeInfo();

    Optional<PayeeInfo> getPayeeInfoById(String histId);

    void add(PayeeInfo domain);

    void update(PayeeInfo domain);

    void remove(String histId);

}
