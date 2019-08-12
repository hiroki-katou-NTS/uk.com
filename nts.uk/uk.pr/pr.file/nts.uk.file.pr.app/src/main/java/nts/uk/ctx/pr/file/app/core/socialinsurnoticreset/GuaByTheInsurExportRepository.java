package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;

import java.util.List;

public interface GuaByTheInsurExportRepository {
    List<CompanyStatutoryWrite> getByCid(String cid);
}
