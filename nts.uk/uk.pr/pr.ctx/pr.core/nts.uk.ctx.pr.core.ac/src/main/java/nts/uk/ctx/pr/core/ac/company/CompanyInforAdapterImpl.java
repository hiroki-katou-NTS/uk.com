package nts.uk.ctx.pr.core.ac.company;

import nts.uk.ctx.bs.company.pub.company.CompanyExport622;
import nts.uk.ctx.bs.company.pub.company.ICompanyPub;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInforAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


@Stateless
public class CompanyInforAdapterImpl implements CompanyInforAdapter {
    @Inject
    private ICompanyPub iCompanyPub;

    @Override
    public CompanyInfor getCompanyNotAbolitionByCid(String cid) {
        Optional<CompanyExport622> resulf = iCompanyPub.getCompanyNotAbolitionByCid(cid);
        return resulf.map(e -> {
            return new CompanyInfor(
                    e.getCompanyId(),
                    e.getCompanyCode(),
                    e.getContractCd(),
                    e.getCompanyName(),
                    e.getRepname(),
                    null,
                    e.getComNameKana(),
                    e.getShortComName(),
                    e.getTaxNo(),
                    e.getAddInfo().getFaxNum(),
                    e.getAddInfo().getAdd_1() == null ? "" :e.getAddInfo().getAdd_1(),
                    e.getAddInfo().getAdd_2() == null ? "" :e.getAddInfo().getAdd_2(),
                    e.getAddInfo().getAddKana_1(),
                    e.getAddInfo().getAddKana_2(),
                    e.getAddInfo().getPostCd() == null ? "" : e.getAddInfo().getPostCd(),
                    e.getAddInfo().getPhoneNum() == null ? "" : e.getAddInfo().getPhoneNum()
            );
        }).get();
    }
}
