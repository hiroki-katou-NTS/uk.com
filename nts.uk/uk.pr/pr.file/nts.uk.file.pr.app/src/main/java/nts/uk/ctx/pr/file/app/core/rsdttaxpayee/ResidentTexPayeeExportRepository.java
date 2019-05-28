package nts.uk.ctx.pr.file.app.core.rsdttaxpayee;

import java.util.List;

public interface ResidentTexPayeeExportRepository {
    List<Object[]> getResidentTexPayeeByCompany(String cid);
}
