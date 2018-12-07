package nts.uk.ctx.pr.core.pubimp.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessDateClassificationExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.ProcessDateClassificationPub;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ProcessDateClassificationPubImpl implements ProcessDateClassificationPub {
    @Inject
    private PerProcessClsSetRepository perProcessClsSetRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;

    @Inject
    private SetDaySupportRepository setDaySupportRepository;

    @Override
    public Optional<ProcessDateClassificationExport> getCurrentProcessYearMonthAndExtraRefDate() {
        String companyId = AppContexts.user().companyId();

        // ドメインモデル「個人処理区分設定」を取得する
        Optional<PerProcessClsSet> perProcessClsSet = perProcessClsSetRepository.getPerProcessClsSetByUIDAndCID(AppContexts.user().userId(), companyId);
        int processCateNo = perProcessClsSet.map(PerProcessClsSet::getProcessCateNo).orElse(1);

        // ドメインモデル「現在処理年月」を取得する
        Optional<CurrProcessDate> currProcessDate = currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(companyId, processCateNo);

        // ドメインモデル「給与支払日設定」を取得する
        if (currProcessDate.isPresent()) {
            Optional<SetDaySupport> setDaySupport = setDaySupportRepository.getSetDaySupportByIdAndProcessDate(companyId, processCateNo, currProcessDate.get().getGiveCurrTreatYear().v());
            return Optional.of(new ProcessDateClassificationExport(currProcessDate.map(CurrProcessDate::getGiveCurrTreatYear).orElse(null), setDaySupport.map(SetDaySupport::getEmpExtraRefeDate).orElse(null)));
        }
        return Optional.empty();
    }
}