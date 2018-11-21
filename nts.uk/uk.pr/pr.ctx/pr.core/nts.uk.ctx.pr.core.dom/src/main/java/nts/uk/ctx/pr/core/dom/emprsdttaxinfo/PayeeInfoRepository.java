package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import java.util.List;
import java.util.Optional;

/**
 * 納付先情報
 */
public interface PayeeInfoRepository {

    Optional<PayeeInfo> getPayeeInfoById(String histId);

    List<PayeeInfo> getListPayeeInfo(List<String> listHistId);

    void add(PayeeInfo domain);

    void update(PayeeInfo domain);

    void remove(String histId);

}
