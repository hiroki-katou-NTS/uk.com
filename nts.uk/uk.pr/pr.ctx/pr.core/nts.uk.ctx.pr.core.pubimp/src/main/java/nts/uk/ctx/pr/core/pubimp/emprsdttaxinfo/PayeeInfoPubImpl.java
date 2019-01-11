package nts.uk.ctx.pr.core.pubimp.emprsdttaxinfo;

import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfoRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.ResidentTaxPayeeCode;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.PayeeInfoExport;
import nts.uk.ctx.pr.core.pub.emprsdttaxinfo.PayeeInfoPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
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

	@Override
	public void updateResidentTaxPayeeCode(String historyId, String rsdtTaxPayeeCode) {
		Optional<PayeeInfo> optPayeeInfo = payeeInfoRepo.getPayeeInfoById(historyId);
		if (optPayeeInfo.isPresent()) {
			PayeeInfo payeeInfo = optPayeeInfo.get();
			payeeInfo.setResidentTaxPayeeCd(new ResidentTaxPayeeCode(rsdtTaxPayeeCode));
			payeeInfoRepo.update(payeeInfo);
		}
	}
	
}
