package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;


import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@SuppressWarnings("Duplicates")
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

    private static final int MODE_DELETE = 0 ;

    private static final int MODE_UPDATE = 1 ;

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


    public void editHistoryProcess(int type, int modeEdit, String hisId, String masterCode,String employeeId, YearMonthHistoryItem history, boolean isUpdate) {
        String cid = AppContexts.user().companyId();
        switch (type){
            case COMPANY : {
                if(modeEdit == MODE_DELETE){
                    mStateLinkSettingCompanyRepository.remove(hisId);
//                    if (mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid).get().items().size() == 1) {
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);

                } else {
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;
            }
            case EMPLOYEE : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
//                    if(mStateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);

                }
                else{
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;
            }
            case DEPARMENT : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    mStateLinkSettingDateRepository.remove(hisId);
//                    if(mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentById(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);
                }
                else{
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;
            }
            case CLASSIFICATION : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
//                    if(mStateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);
                }
                else{
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;
            }
            case POSITION : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
                    mStateLinkSettingDateRepository.remove(hisId);
//                    if(mStateCorrelationHisPositionRepository.getStateCorrelationHisPositionByCid(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);

                }
                else{
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;

            }
            case SALARY : {
                if(modeEdit == MODE_DELETE){
                    masterRepository.remove(hisId,masterCode);
//                    if(mStateCorrelationHisSalaryRepository.getStateCorrelationHisSalaryByCid(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);
                }
                else{
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;

            }
            case INDIVIDUAL : {
                if(modeEdit == MODE_DELETE){
                    mStateLinkSettingIndividualRepository.remove(hisId);
//                    if(mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                    historyDeletionProcessing(type,hisId,cid,employeeId);
                }
                else{
                    if (isUpdate) {
                        historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(),employeeId);
                        return;
                    }
                    throw new BusinessException("Msg_107");
                }
                break;
            }

        }
    }

    private void historyDeletionProcessing(int type, String hisId, String cId, String employeeId) {
        switch (type) {
            case COMPANY: {
                Optional<StateCorrelationHisCompany> accInsurHis = mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cId);
                if (!accInsurHis.isPresent()) {
                    throw new RuntimeException("invalid employmentHistory");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (accInsurHis.get().getHistory().size() == 1) {
                    mStateCorrelationHisCompanyRepository.remove(cId, hisId);
                    return;
                }
                accInsurHis.get().remove(itemToBeDelete.get());
                mStateCorrelationHisCompanyRepository.remove(cId, hisId);
                if (accInsurHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
                    accInsurHis.get().exCorrectToRemove(lastestItem);
                    mStateCorrelationHisCompanyRepository.update(cId, lastestItem);
                }
                break;
            }
            case EMPLOYEE: {
                Optional<StateCorrelationHisEmployee> accInsurHis = mStateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cId);
                if (!accInsurHis.isPresent()) {
                    throw new RuntimeException("invalid employmentHistory");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (accInsurHis.get().getHistory().size() == 1) {
                    mStateCorrelationHisEmployeeRepository.remove(cId, hisId);
                    return;
                }
                accInsurHis.get().remove(itemToBeDelete.get());
                mStateCorrelationHisEmployeeRepository.remove(cId, hisId);
                if (accInsurHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
                    accInsurHis.get().exCorrectToRemove(lastestItem);
                    mStateCorrelationHisEmployeeRepository.update(cId, lastestItem);
                }
                break;

            }
            case DEPARMENT: {
                Optional<StateCorrelationHisDeparment> accInsurHis = mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentById(cId);
                if (!accInsurHis.isPresent()) {
                    throw new RuntimeException("invalid employmentHistory");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (accInsurHis.get().getHistory().size() == 1) {
                    mStateCorrelationHisDeparmentRepository.remove(cId, hisId);
                    return;
                }
                accInsurHis.get().remove(itemToBeDelete.get());
                mStateCorrelationHisDeparmentRepository.remove(cId, hisId);
                if (accInsurHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
                    accInsurHis.get().exCorrectToRemove(lastestItem);
                    mStateCorrelationHisDeparmentRepository.update(cId, lastestItem);
                }
                break;
            }
            case CLASSIFICATION: {
                Optional<StateCorrelationHisClassification> accInsurHis = mStateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
                if (!accInsurHis.isPresent()) {
                    throw new RuntimeException("invalid employmentHistory");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (accInsurHis.get().getHistory().size() == 1) {
                    mStateCorrelationHisClassificationRepository.remove(cId, hisId);
                    return;
                }
                accInsurHis.get().remove(itemToBeDelete.get());
                mStateCorrelationHisClassificationRepository.remove(cId, hisId);
                if (accInsurHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
                    accInsurHis.get().exCorrectToRemove(lastestItem);
                    mStateCorrelationHisClassificationRepository.update(cId, lastestItem);
                }
                break;

            }
            case POSITION: {
                Optional<StateCorrelationHisPosition> accInsurHis = mStateCorrelationHisPositionRepository.getStateCorrelationHisPositionByCid(cId);
                if (!accInsurHis.isPresent()) {
                    throw new RuntimeException("invalid employmentHistory");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (accInsurHis.get().getHistory().size() == 1) {
                    mStateCorrelationHisPositionRepository.remove(cId, hisId);
                    return;
                }
                accInsurHis.get().remove(itemToBeDelete.get());
                mStateCorrelationHisPositionRepository.remove(cId, hisId);
                if (accInsurHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
                    accInsurHis.get().exCorrectToRemove(lastestItem);
                    mStateCorrelationHisPositionRepository.update(cId, lastestItem);
                }
                break;
            }
            case SALARY: {
                Optional<StateCorrelationHisClassification> accInsurHis = mStateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
                if (!accInsurHis.isPresent()) {
                    throw new RuntimeException("invalid employmentHistory");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (accInsurHis.get().getHistory().size() == 1) {
                    mStateCorrelationHisClassificationRepository.remove(cId, hisId);
                    return;
                }
                accInsurHis.get().remove(itemToBeDelete.get());
                mStateCorrelationHisClassificationRepository.remove(cId, hisId);
                if (accInsurHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = accInsurHis.get().getHistory().get(0);
                    accInsurHis.get().exCorrectToRemove(lastestItem);
                    mStateCorrelationHisClassificationRepository.update(cId, lastestItem);
                }
                break;
            }
            case INDIVIDUAL : {
                String companyId = AppContexts.user().companyId();

                Optional<StateCorrelationHisIndividual> existHist = mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(employeeId,hisId);

                if (!existHist.isPresent()){
                    throw new RuntimeException("invalid StateCorrelationHisIndividual");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = existHist.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();

                if (!itemToBeDelete.isPresent()){
                    throw new RuntimeException("invalid StateCorrelationHisIndividual");
                }
                existHist.get().remove(itemToBeDelete.get());
                mStateCorrelationHisIndividualRepository.remove(employeeId,hisId);
                break;
            }


        }


    }

    private void historyCorrectionProcecessing(int type, String cId, String hisId, YearMonth start, YearMonth end,String employeeId) {
        switch (type) {
            case COMPANY: {
                // // //
                Optional<StateCorrelationHisCompany> accInsurHis = mStateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cId);
                if (!accInsurHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                //update history
                mStateCorrelationHisCompanyRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = accInsurHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorrelationHisCompanyRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case EMPLOYEE: {
                // // //
                Optional<StateCorrelationHisEmployee> accInsurHis = mStateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cId);

                if (!accInsurHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorrelationHisEmployeeRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = accInsurHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorrelationHisEmployeeRepository.update(cId, itemToBeUpdated.get());
                break;

            }
            case DEPARMENT: {
                // // //
                Optional<StateCorrelationHisDeparment> accInsurHis = mStateCorrelationHisDeparmentRepository.getStateCorrelationHisDeparmentById(cId);

                if (!accInsurHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorrelationHisDeparmentRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = accInsurHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorrelationHisDeparmentRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case CLASSIFICATION: {
                // // //
                Optional<StateCorrelationHisClassification> accInsurHis = mStateCorrelationHisClassificationRepository.getStateCorrelationHisClassificationByCid(cId);
                if (!accInsurHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorrelationHisClassificationRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = accInsurHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorrelationHisClassificationRepository.update(cId, itemToBeUpdated.get());
                break;

            }
            case POSITION: {
                // // //
                Optional<StateCorrelationHisPosition> accInsurHis = mStateCorrelationHisPositionRepository.getStateCorrelationHisPositionByCid(cId);
                if (!accInsurHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorrelationHisPositionRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = accInsurHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorrelationHisPositionRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case SALARY: {

                // // //
                Optional<StateCorrelationHisSalary> accInsurHis = mStateCorrelationHisSalaryRepository.getStateCorrelationHisSalaryByCid(cId);
                if (!accInsurHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = accInsurHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                accInsurHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorrelationHisSalaryRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = accInsurHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorrelationHisSalaryRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case INDIVIDUAL :{
                // Update history table
                // In case of date period are exist in the screen
                if (start != null){
                    Optional<StateCorrelationHisIndividual> existHist = mStateCorrelationHisIndividualRepository.getStateCorrelationHisIndividualById(employeeId, hisId);
                    if (!existHist.isPresent()) {
                        throw new RuntimeException("invalid StateCorrelationHisIndividual");
                    }

                    Optional<YearMonthHistoryItem> itemToBeUpdate = existHist.get().getHistory().stream()
                            .filter(h -> h.identifier().equals(hisId)).findFirst();

                    if (!itemToBeUpdate.isPresent()) {
                        throw new RuntimeException("invalid StateCorrelationHisIndividual");
                    }
                    existHist.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start,
                            end != null ? end : new YearMonth(999912)));
                    mStateCorrelationHisIndividualRepository.update(employeeId, itemToBeUpdate.get());
                }


                break;
            }


        }


    }




}
