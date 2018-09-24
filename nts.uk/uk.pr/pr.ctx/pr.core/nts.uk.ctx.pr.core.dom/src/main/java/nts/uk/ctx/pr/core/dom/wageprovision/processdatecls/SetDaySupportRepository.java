package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;

/**
 * 給与支払日設定
 */
public interface SetDaySupportRepository {

    List<SetDaySupport> getAllSetDaySupport();

    List<SetDaySupport> getSetDaySupportById(String cid, int processCateNo);

    List<SetDaySupport> getSetDaySupportByIdAndYear(String cid, int processCateNo, int year);

    void add(SetDaySupport domain);

    void update(SetDaySupport domain);

    void addAll(List<SetDaySupport> domains);


}
