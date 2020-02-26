package nts.uk.ctx.pr.file.app.core.wageprovision.wagetable;

import java.util.List;

public interface WageTableExportRepository {
    List<WageTablelData> getWageTableExport(String cid,int startYearMonth);
    List<ItemDataNameExport> getItemName(String cid);
    List<ItemDataNameExport> getItemNameMaster(String cid);
}
