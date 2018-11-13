package nts.uk.ctx.pr.core.app.find.employaverwage;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmployeeAverWageFinder {

    @Inject
    private PerProcessClsSetRepository perProcessClsSetRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;

    @Inject
    private SetDaySupportRepository setDaySupportRepository;


    public String init() {
        int processCateNo = 1;
        String response = null;
        String currentCid = AppContexts.user().companyId();
        // ドメインモデル「個人処理区分設定」を取得する
        Optional<PerProcessClsSet> perProcessClsSet = perProcessClsSetRepository.getPerProcessClsSetByUIDAndCID(AppContexts.user().userId(),currentCid);
        if(perProcessClsSet.isPresent()) {
            processCateNo = perProcessClsSet.get().getProcessCateNo();
        }

        // ドメインモデル「現在処理年月」を取得する
        Optional<CurrProcessDate> currProcessDate = currProcessDateRepository.getCurrProcessDateByIdAndProcessCateNo(currentCid,processCateNo);

        // ドメインモデル「給与支払日設定」を取得する
        if(currProcessDate.isPresent()) {
            Optional<SetDaySupport> setDaySupport = setDaySupportRepository.getSetDaySupportByIdAndProcessDate(currentCid,processCateNo,currProcessDate.get().getGiveCurrTreatYear().v());
            response = setDaySupport.isPresent() ? setDaySupport.get().getEmpExtraRefeDate().toString() : null;
        }
        return response;
    }

    public void show(EmployAverWageDto employAverWageDto){

        // ドメインモデル「社員平均賃金」をすべて取得する

    }


}
