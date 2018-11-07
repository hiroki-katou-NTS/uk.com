package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

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
    @Inject
    private AffDepartHistoryAdapter mAffDepartHistoryAdapter;
    @Inject
    private StateCorrelationHisDeparmentRepository mStateCorrelationHisDeparmentRepository;
    @Inject
    private StateLinkSettingMasterRepository mStateLinkSettingMasterRepository;
    @Inject
    private DepartmentAdapter mDepartmentAdapter;


    /*雇用*/
    private static final int EMPLOYEE = 0;
    /*部門*/
    private static final int DEPARMENT = 1;
    /*分類*/
    private static final int CLASSIFICATION = 2;
    /*職位*/
    private static final int POSITION = 3;
    /*給与分類*/
    private static final int SALARY = 4;


    public void indiTiedStatAcquiProcess(String empID, String hisId) {
//        String cid = AppContexts.user().companyId();
//        Optional<StateUseUnitSetting> mStateUseUnitSetting =  mStateUseUnitSettingRepository.getStateUseUnitSettingById(cid);
//        if(!mStateUseUnitSetting.isPresent()){
//            return;
//        }
//        if(mStateUseUnitSetting.get().getIndividualUse().value == SettingUseClassification.USE.value ){
//            Optional<StateCorrelationHisIndividual> mStateCorrelationHisIndividual =  mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(empID,hisId);
//            if(mStateCorrelationHisIndividual.isPresent()){
//                Optional<StateLinkSettingIndividual> mStateLinkSettingIndividual = mStateLinkSettingIndividualRepository.getStateLinkSettingIndividualById(mStateCorrelationHisIndividual.get().getHistory().get(0).identifier());
//                if(mStateLinkSettingIndividual.isPresent() && mStateLinkSettingIndividual.get().getBonusCode().isPresent() && mStateLinkSettingIndividual.get().getSalaryCode().isPresent()){
//
//                } else{
//                    if () {
//                        getAcquireMasterLinkedStatement();
//                    } else {
//
//                    }
//                }
//            } else {
//                if () {
//                    getAcquireMasterLinkedStatement();
//                } else {
//
//                }
//            }
//        } else{
//            if () {
//                getAcquireMasterLinkedStatement();
//            } else {
//
//            }
//        }


    }

    private void getAcquireMasterLinkedStatement(int type, String employeeId, GeneralDate baseDate, YearMonthHistoryItem baseHistory, String hisId) {
        String cid = AppContexts.user().companyId();
        switch (type) {
            case DEPARMENT: {
                AffDepartHistory mAffDepartHistory = mAffDepartHistoryAdapter.getDepartmentByBaseDate(employeeId, baseDate).get();
                mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentByDate(cid, baseHistory);
                Optional<StateLinkSettingMaster> mStateLinkSettingMaster = mStateLinkSettingMasterRepository.getStateLinkSettingMasterById(hisId, mAffDepartHistory.getDepartmentCode());
                if (!mStateLinkSettingMaster.isPresent()) {
                    return;
                }
                Optional<Department> mDepartment = mDepartmentAdapter.getDepartmentByBaseDate(employeeId, baseDate);
                if (!mDepartment.isPresent()) {
                    return;
                }


            }
        }
    }




}
