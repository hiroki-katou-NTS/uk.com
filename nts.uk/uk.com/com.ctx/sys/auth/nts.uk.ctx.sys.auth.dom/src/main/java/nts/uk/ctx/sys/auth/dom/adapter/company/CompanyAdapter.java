package nts.uk.ctx.sys.auth.dom.adapter.company;

import java.util.List;

public interface CompanyAdapter {
     List<CompanyImport> findAllCompany();
     List<CompanyImport> findAllCompanyImport();
     CompanyImport findCompanyByCid(String cid);
}
