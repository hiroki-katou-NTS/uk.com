package nts.uk.ctx.pr.core.ac.company;

import nts.uk.ctx.bs.company.pub.company.CompanyExport;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforEx;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;
@Stateless
public class CompanyInforAdapterImpl implements CompanyInforAdapter {
    @Inject
    ICompanyPub iCompanyPub;

    @Override
    public CompanyInfor getCompanyNotAbolitionByCid(String cid) {
        CompanyExport resulf = iCompanyPub.getCompany(cid);
        return null;
    }
}
