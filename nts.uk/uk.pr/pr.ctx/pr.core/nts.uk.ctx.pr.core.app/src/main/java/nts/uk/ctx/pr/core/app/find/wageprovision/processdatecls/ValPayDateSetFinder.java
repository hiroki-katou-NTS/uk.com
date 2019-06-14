package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
/**
 * 支払日の設定の規定値
 */
public class ValPayDateSetFinder {

    @Inject
    private ValPayDateSetRepository finder;

    public ValPayDateSetDto getValPayDateSet(int processCateNo) {
        String cid = AppContexts.user().companyId();
        Optional<ValPayDateSet> valPayDateSet = finder.getValPayDateSetById(cid, processCateNo);
//        if (valPayDateSet.isPresent()) {
//            val item = valPayDateSet.get();
//            return new ValPayDateSetDto(item.getCid(), item.getProcessCateNo());
//        }
        return valPayDateSet.map(x -> new ValPayDateSetDto(x.getCid(), x.getProcessCateNo(),BasicSettingDto.fromDomain(x.getBasicSetting()),AdvancedSettingDto.fromDomain(x.getAdvancedSetting()))).orElse(null);
    }

}
