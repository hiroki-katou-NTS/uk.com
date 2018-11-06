package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.arc.error.BusinessException;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class StateCorrelationHistoryService {

    /* 会社*/
    private static final int COMPANY = 0;
    /*部門*/
    private static final int EMPLOYEE = 1;
    /*部門*/
    private static final int DEPARMENT = 2;
    /*分類*/
    private static final int CLASSIFICATION = 3;
    /*職位*/
    private static final int POSITION  = 4;
    /*給与分類*/
    private static final int SALARY = 5 ;
    /*個人*/
    private static final int INDIVIDUAL = 6 ;

    private static final int MODE_UPDATE = 0 ;
    private static final int MODE_DELETE = 1 ;

    @Inject
    private StateLinkSettingCompanyRepository mStateLinkSettingCompanyRepository;
    @Inject
    private StateLinkSettingMasterRepository masterRepository;
    @Inject
    private StateLinkSettingDateRepository mStateLinkSettingDateRepository;
    @Inject
    private StateLinkSettingIndividualRepository mStateLinkSettingIndividualRepository;
    @Inject
    private StateCorrelationHisCompanyRepository mStateCorrelationHisCompanyRepository;
    @Inject
    private StateCorrelationHisEmployeeRepository mStateCorrelationHisEmployeeRepository;
    @Inject
    private StateCorrelationHisDeparmentRepository mStateCorrelationHisDeparmentRepository;
    @Inject
    private StateCorrelationHisClassificationRepository mStateCorrelationHisClassificationRepository;
    @Inject
    private StateCorrelationHisPositionRepository mStateCorrelationHisPositionRepository;
    @Inject
    private StateCorrelationHisSalaryRepository mStateCorrelationHisSalaryRepository;
    @Inject
    private StateCorrelationHisIndividualRepository mStateCorrelationHisIndividualRepository;


    public void editHistoryProcess(int type, int modeEdit, String hisId, String masterCode, YearMonthHistoryItem history, boolean isUpdate) {
        String cid = AppContexts.user().companyId();
        switch (type){
            case COMPANY : {
                if(modeEdit == MODE_DELETE){
                    mStateLinkSettingCompanyRepository.remove(hisId);
                    if (mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid, hisId).get().items().size() == 1) {
                        mStateCorrelationHisCompanyRepository.remove(cid, hisId);
                        return;
                    }
                    mStateCorrelationHisCompanyRepository.update(cid, history);
                    break;
                } else {
                    if (isUpdate) {
                        mStateCorrelationHisCompanyRepository.update(cid, history);
                        return;
                    }
                    throw new BusinessException("Msg_107");

                }

            }
            case EMPLOYEE : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    if(modeEdit == MODE_DELETE){
                        mStateCorrelationHisEmployeeRepository.remove(cid,hisId);
                        return;
                    }
                    mStateCorrelationHisEmployeeRepository.update(cid,history);
                    break;
                }
                else{
                    if (isUpdate) {
                        mStateCorrelationHisEmployeeRepository.update(cid,history);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }

            }
            case DEPARMENT : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    mStateLinkSettingDateRepository.remove(hisId);
                    if(modeEdit == MODE_DELETE){
                        mStateCorrelationHisDeparmentRepository.remove(cid,hisId);
                        return;
                    }
                    mStateCorrelationHisDeparmentRepository.update(cid,history);
                    break;
                }
                else{
                    if (isUpdate) {
                        mStateCorrelationHisDeparmentRepository.update(cid,history);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }

            }
            case CLASSIFICATION : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    if(modeEdit == MODE_DELETE){
                        mStateCorrelationHisClassificationRepository.remove(cid,hisId);
                        return;
                    }
                    mStateCorrelationHisClassificationRepository.update(cid,history);
                    break;
                }
                else{
                    if (isUpdate) {
                        mStateCorrelationHisClassificationRepository.update(cid,history);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }

            }
            case POSITION : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    mStateLinkSettingDateRepository.remove(hisId);
                    if(modeEdit == MODE_DELETE){
                        mStateCorrelationHisPositionRepository.remove(cid,hisId);
                        return;
                    }
                    mStateCorrelationHisPositionRepository.update(cid,history);
                    break;
                }
                else{
                    if (isUpdate) {
                        mStateCorrelationHisPositionRepository.update(cid,history);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }

            }
            case SALARY : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    if(modeEdit == MODE_DELETE){
                        mStateCorrelationHisSalaryRepository.remove(cid,hisId);
                        return;
                    }
                    mStateCorrelationHisSalaryRepository.update(cid,history);
                    break;
                }
                else{
                    if (isUpdate) {
                        mStateCorrelationHisSalaryRepository.update(cid,history);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }

            }
            case INDIVIDUAL : {
                if(modeEdit == MODE_DELETE){
                    mStateLinkSettingIndividualRepository.remove(hisId);
                    if(modeEdit == MODE_DELETE){
                        mStateCorrelationHisIndividualRepository.remove(cid,hisId);
                        return;
                    }
                    mStateCorrelationHisIndividualRepository.update(cid,history);
                    break;
                }
                else{
                    if (isUpdate) {
                        mStateCorrelationHisIndividualRepository.update(cid,history);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }

            }

        }
    }


}
