package nts.uk.ctx.pr.core.pubimp.emprsdttaxinfo;

import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfoRepository;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.PayeeInfoExport;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.PayeeInfoPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PayeeInfoPubImpl implements PayeeInfoPub {
    @Inject
    private PayeeInfoRepository payeeInfoRepo;

    @Override
    public List<PayeeInfoExport> getListPayeeInfo(List<String> listHistId) {
        return payeeInfoRepo.getListPayeeInfo(listHistId).stream().map(x -> {
            PayeeInfoExport export = new PayeeInfoExport();
            export.setHistId(x.getHistId());
            export.setResidentTaxPayeeCd(x.getResidentTaxPayeeCd().v());
            return export;
        }).collect(Collectors.toList());
    }
}
