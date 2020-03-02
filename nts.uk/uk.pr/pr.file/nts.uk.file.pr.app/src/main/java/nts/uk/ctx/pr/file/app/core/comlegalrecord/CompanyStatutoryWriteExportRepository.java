package nts.uk.ctx.pr.file.app.core.comlegalrecord;

import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;

import java.util.List;

public interface CompanyStatutoryWriteExportRepository {
    List<CompanyStatutoryWrite> getByCid(String cid);
}
