package nts.uk.ctx.pr.core.pubimp.emprsdttaxinfo;

import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoRepository;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.PayeeInfo;
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
    private EmployeeResidentTaxPayeeInfoRepository empRsdtTaxPayeeInfoRepo;

    @Override
    public List<PayeeInfoExport> getListPayeeInfo(List<String> listHistId) {
        return empRsdtTaxPayeeInfoRepo.getListPayeeInfo(listHistId).stream().map(x -> {
            PayeeInfoExport export = new PayeeInfoExport();
            export.setHistId(x.getHistId());
            export.setResidentTaxPayeeCd(x.getResidentTaxPayeeCd().v());
            return export;
        }).collect(Collectors.toList());
    }

	@Override
	public void updateResidentTaxPayeeCode(String historyId, String rsdtTaxPayeeCode) {
        PayeeInfo payeeInfo = new PayeeInfo(historyId, rsdtTaxPayeeCode);
        empRsdtTaxPayeeInfoRepo.updatePayeeInfo(payeeInfo);
	}
	
}
