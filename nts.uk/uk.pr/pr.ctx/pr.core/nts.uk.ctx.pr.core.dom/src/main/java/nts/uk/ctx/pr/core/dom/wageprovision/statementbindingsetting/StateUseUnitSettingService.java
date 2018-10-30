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
    @Inject
    private StateLinkSettingIndividualRepository mStateLinkSettingIndividualRepository;


    public void indiTiedStatAcquiProcess( String empID, String hisId){
        String cid = AppContexts.user().companyId();
        Optional<StateUseUnitSetting> mStateUseUnitSetting =  mStateUseUnitSettingRepository.getStateUseUnitSettingById(cid);
        if(!mStateUseUnitSetting.isPresent()){
                return;
        }
        if(mStateUseUnitSetting.get().getIndividualUse().value == SettingUseClassification.USE.value ){
            Optional<StateCorrelationHisIndividual> mStateCorrelationHisIndividual =  mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(empID,hisId);
            if(mStateCorrelationHisIndividual.isPresent()){
                Optional<StateLinkSettingIndividual> mStateLinkSettingIndividual = mStateLinkSettingIndividualRepository.getStateLinkSettingIndividualById(mStateCorrelationHisIndividual.get().getHistory().get(0).identifier());
                if(mStateLinkSettingIndividual.isPresent() && mStateLinkSettingIndividual.get().getBonusCode().isPresent() && mStateLinkSettingIndividual.get().getSalaryCode().isPresent()){

                }
                else{

                }
            }
        }
        else{

        }








    }




}
