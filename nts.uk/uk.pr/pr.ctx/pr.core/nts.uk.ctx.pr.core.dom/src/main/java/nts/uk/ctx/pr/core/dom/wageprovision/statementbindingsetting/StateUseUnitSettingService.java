package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class StateUseUnitSettingService {

    @Inject
    private StateUseUnitSettingRepository mStateUseUnitSettingRepository;
    @Inject
    private StateCorrelationHisIndividualRepository mStateCorrelationHisIndividualRepository;

    public void indiTiedStatAcquiProcess(){
        String cid = AppContexts.user().companyId();
        Optional<StateUseUnitSetting> mStateUseUnitSetting =  mStateUseUnitSettingRepository.getStateUseUnitSettingById(cid);
        if(!mStateUseUnitSetting.isPresent()){
                return;
        }
        if(mStateUseUnitSetting.get().getIndividualUse().value == SettingUseClassification.USE.value ){
            Optional<StateCorrelationHisIndividual> mStateCorrelationHisIndividual =  mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById();
        }
        else{

        }








    }




}
