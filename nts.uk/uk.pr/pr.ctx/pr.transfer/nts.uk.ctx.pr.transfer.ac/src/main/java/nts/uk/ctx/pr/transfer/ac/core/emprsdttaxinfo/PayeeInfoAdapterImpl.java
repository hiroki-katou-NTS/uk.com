package nts.uk.ctx.pr.transfer.ac.core.emprsdttaxinfo;

import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.PayeeInfoPub;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.PayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.PayeeInfoImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PayeeInfoAdapterImpl implements PayeeInfoAdapter {
    @Inject
    private PayeeInfoPub payeeInfoPub;

    @Override
    public List<PayeeInfoImport> getListPayeeInfo(List<String> listHistId) {
        return payeeInfoPub.getListPayeeInfo(listHistId).stream().map(x -> {
            PayeeInfoImport imp = new PayeeInfoImport();
            imp.setHistId(x.getHistId());
            imp.setResidentTaxPayeeCd(x.getResidentTaxPayeeCd());
            return imp;
        }).collect(Collectors.toList());
    }
}
