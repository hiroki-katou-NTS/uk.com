package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;

/**
 * 明細書印字年月設定
 */
public interface SpecPrintYmSetRepository {

    List<SpecPrintYmSet> getAllSpecPrintYmSet();

    List<SpecPrintYmSet> getSpecPrintYmSetById(String cid, int processCateNo);

    List<SpecPrintYmSet> getSpecPrintYmSetByIdAndYear(String cid, int processCateNo, int year);

    void add(SpecPrintYmSet domain);

    void addAll(List<SpecPrintYmSet> domains);

    void update(SpecPrintYmSet domain);


}
