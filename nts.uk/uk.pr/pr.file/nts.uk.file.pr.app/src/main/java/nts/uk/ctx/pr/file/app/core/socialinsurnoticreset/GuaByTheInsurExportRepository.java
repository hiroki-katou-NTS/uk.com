package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface GuaByTheInsurExportRepository {
    List<Object[]> getDataExport(List<String> empIds, String cid,String userId, GeneralDate startDate, GeneralDate endDate);
    Object[] getInforCompanyByCid(String cid);
    CompanyInfor getCompanyInfor(String cid);
}
