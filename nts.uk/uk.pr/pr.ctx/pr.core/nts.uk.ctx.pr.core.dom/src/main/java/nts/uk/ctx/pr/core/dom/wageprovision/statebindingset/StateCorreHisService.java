package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;


import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@SuppressWarnings("Duplicates")
@Stateless
public class StateCorreHisService {

    /* 会社*/
    private static final int EMPLOYEE = 0;
    /*部門*/
    private static final int DEPARMENT = 1;
    /*部門*/
    private static final int CLASSIFICATION = 2;
    /*分類*/
    private static final int POSITION = 3;
    /*職位*/
    private static final int SALARY  = 4;
    /*給与分類*/
    private static final int COMPANY = 5 ;
    /*個人*/
    private static final int INDIVIDUAL = 6 ;

    private static final int MODE_DELETE = 0 ;

    private static final int MODE_UPDATE = 1 ;

    @Inject
    private StateCorreHisComRepository mStateCorreHisComRepository;
    @Inject
    private StateCorreHisEmRepository mStateCorreHisEmRepository;
    @Inject
    private StateCorreHisDeparRepository mStateCorreHisDeparRepository;
    @Inject
    private StateCorreHisClsRepository mStateCorreHisClsRepository;
    @Inject
    private StateCorreHisPoRepository mStateCorreHisPoRepository;
    @Inject
    private StateCorreHisSalaRepository mStateCorreHisSalaRepository;
    @Inject
    private StateCorreHisIndiviRepository mStateCorreHisIndiviRepository;


    public void editHistoryProcess(int type, int modeEdit, String hisId, String masterCode,String employeeId, YearMonthHistoryItem history, boolean isUpdate) {
        String cid = AppContexts.user().companyId();
        if (modeEdit == MODE_UPDATE) {
            if (isUpdate) {
                historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end(), employeeId);
                return;
            }
            throw new BusinessException("Msg_107");
        }
        switch (type){
            case COMPANY : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisComRepository.remove(cid,hisId);
//                    if (mStateCorreHisComRepository.getStateCorrelationHisCompanyById(cid).get().items().size() == 1) {
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                break;
            }
            case EMPLOYEE : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisEmRepository.removeAll(cid,hisId);
//                    if(mStateCorreHisEmRepository.getStateCorrelationHisEmployeeById(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                break;
            }
            case DEPARMENT : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisDeparRepository.removeAll(cid,hisId);
                   // masterRepository.remove(hisId,masterCode);
                    //mStateLinkSettingDateRepository.remove(hisId);
//                    if(mStateCorreHisDeparRepository.getStateCorrelationHisDeparmentById(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                break;
            }
            case CLASSIFICATION : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisClsRepository.removeAll(cid,hisId);
//                    if(mStateCorreHisClsRepository.getStateCorrelationHisClassificationByCid(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                break;
            }
            case POSITION : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisPoRepository.removeAll(cid,hisId);
                //mStateLinkSettingDateRepository.remove(hisId);
//                    if(mStateCorreHisPoRepository.getStateCorrelationHisPositionByCid(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());


                break;

            }
            case SALARY : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisSalaRepository.removeAll(cid,hisId);
//                    if(mStateCorreHisSalaRepository.getStateCorrelationHisSalaryByCid(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());
                break;
            }
            case INDIVIDUAL : {
                historyDeletionProcessing(type, hisId, cid, employeeId);
                mStateCorreHisIndiviRepository.remove(cid,hisId);
//                    if(mStateCorreHisIndiviRepository.getStateCorrelationHisIndividualById(cid).get().items().size() == 1){
//                        historyDeletionProcessing(type, cid, hisId);
//                        return;
//                    }
//                    historyCorrectionProcecessing(type, cid, history.identifier(), history.start(), history.end());

                break;
            }
        }


    }

    private void historyDeletionProcessing(int type, String hisId, String cId, String employeeId) {
        switch (type) {
            case COMPANY: {
                Optional<StateCorreHisCom> stateCorreHis = mStateCorreHisComRepository.getStateCorrelationHisCompanyById(cId);
                if (!stateCorreHis.isPresent()) {
                    throw new RuntimeException("invalid StateCorreHisCom");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (stateCorreHis.get().getHistory().size() == 1) {
                    mStateCorreHisComRepository.remove(cId, hisId);
                    return;
                }
                stateCorreHis.get().remove(itemToBeDelete.get());
                mStateCorreHisComRepository.remove(cId, hisId);
                if (stateCorreHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = stateCorreHis.get().getHistory().get(0);
                    stateCorreHis.get().exCorrectToRemove(lastestItem);
                    mStateCorreHisComRepository.update(cId, lastestItem);
                }
                break;
            }
            case EMPLOYEE: {
                Optional<StateCorreHisEm> stateCorreHis = mStateCorreHisEmRepository.getStateCorrelationHisEmployeeById(cId);
                if (!stateCorreHis.isPresent()) {
                    throw new RuntimeException("invalid StateCorreHisEm");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (stateCorreHis.get().getHistory().size() == 1) {
                    mStateCorreHisEmRepository.removeAll(cId, hisId);
                    return;
                }
                stateCorreHis.get().remove(itemToBeDelete.get());
                mStateCorreHisEmRepository.removeAll(cId, hisId);
                if (stateCorreHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = stateCorreHis.get().getHistory().get(0);
                    stateCorreHis.get().exCorrectToRemove(lastestItem);
                    mStateCorreHisEmRepository.update(cId, lastestItem);
                }
                break;

            }
            case DEPARMENT: {
                Optional<StateCorreHisDepar> stateCorreHis = mStateCorreHisDeparRepository.getStateCorrelationHisDeparmentById(cId);
                if (!stateCorreHis.isPresent()) {
                    throw new RuntimeException("invalid StateCorreHisDepar");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (stateCorreHis.get().getHistory().size() == 1) {
                    mStateCorreHisDeparRepository.removeAll(cId, hisId);
                    return;
                }
                stateCorreHis.get().remove(itemToBeDelete.get());
                mStateCorreHisDeparRepository.removeAll(cId, hisId);
                if (stateCorreHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = stateCorreHis.get().getHistory().get(0);
                    stateCorreHis.get().exCorrectToRemove(lastestItem);
                    mStateCorreHisDeparRepository.update(cId, lastestItem);
                }
                break;
            }
            case CLASSIFICATION: {
                Optional<StateCorreHisCls> stateCorreHis = mStateCorreHisClsRepository.getStateCorrelationHisClassificationByCid(cId);
                if (!stateCorreHis.isPresent()) {
                    throw new RuntimeException("invalid StateCorreHisCls");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (stateCorreHis.get().getHistory().size() == 1) {
                    mStateCorreHisClsRepository.removeAll(cId, hisId);
                    return;
                }
                stateCorreHis.get().remove(itemToBeDelete.get());
                mStateCorreHisClsRepository.removeAll(cId, hisId);
                if (stateCorreHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = stateCorreHis.get().getHistory().get(0);
                    stateCorreHis.get().exCorrectToRemove(lastestItem);
                    mStateCorreHisClsRepository.update(cId, lastestItem);
                }
                break;

            }
            case POSITION: {
                Optional<StateCorreHisPo> stateCorreHis = mStateCorreHisPoRepository.getStateCorrelationHisPositionByCid(cId);
                if (!stateCorreHis.isPresent()) {
                    throw new RuntimeException("invalid StateCorreHisPo");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (stateCorreHis.get().getHistory().size() == 1) {
                    mStateCorreHisPoRepository.removeAll(cId, hisId);
                    return;
                }
                stateCorreHis.get().remove(itemToBeDelete.get());
                mStateCorreHisPoRepository.removeAll(cId, hisId);
                if (stateCorreHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = stateCorreHis.get().getHistory().get(0);
                    stateCorreHis.get().exCorrectToRemove(lastestItem);
                    mStateCorreHisPoRepository.update(cId, lastestItem);
                }
                break;
            }
            case SALARY: {
                Optional<StateCorreHisSala> stateCorreHis = mStateCorreHisSalaRepository.getStateCorrelationHisSalaryByCid(cId);
                if (!stateCorreHis.isPresent()) {
                    throw new RuntimeException("invalid StateCorreHisSala");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()) {
                    return;
                }
                if (stateCorreHis.get().getHistory().size() == 1) {
                    mStateCorreHisSalaRepository.removeAll(cId, hisId);
                    return;
                }
                stateCorreHis.get().remove(itemToBeDelete.get());
                mStateCorreHisSalaRepository.removeAll(cId, hisId);
                if (stateCorreHis.get().getHistory().size() > 0) {
                    YearMonthHistoryItem lastestItem = stateCorreHis.get().getHistory().get(0);
                    stateCorreHis.get().exCorrectToRemove(lastestItem);
                    mStateCorreHisSalaRepository.update(cId, lastestItem);
                }
                break;
            }
            case INDIVIDUAL : {
                Optional<StateCorreHisIndivi> existHist = mStateCorreHisIndiviRepository.getStateCorrelationHisIndividualById(employeeId,hisId);
                if (!existHist.isPresent()){
                    throw new RuntimeException("invalid StateCorreHisIndivi");
                }
                Optional<YearMonthHistoryItem> itemToBeDelete = existHist.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId))
                        .findFirst();
                if (!itemToBeDelete.isPresent()){
                    throw new RuntimeException("invalid StateCorreHisIndivi");
                }
                existHist.get().remove(itemToBeDelete.get());
                mStateCorreHisIndiviRepository.remove(employeeId,hisId);
                break;
            }
        }
    }
    private void historyCorrectionProcecessing(int type, String cId, String hisId, YearMonth start, YearMonth end,String employeeId) {
        switch (type) {
            case COMPANY: {
                Optional<StateCorreHisCom> stateCorreHis = mStateCorreHisComRepository.getStateCorrelationHisCompanyById(cId);
                if (!stateCorreHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                stateCorreHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                //update history
                mStateCorreHisComRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorreHisComRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case EMPLOYEE: {
                // // //
                Optional<StateCorreHisEm> stateCorreHis = mStateCorreHisEmRepository.getStateCorrelationHisEmployeeById(cId);

                if (!stateCorreHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                stateCorreHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorreHisEmRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorreHisEmRepository.update(cId, itemToBeUpdated.get());
                break;

            }
            case DEPARMENT: {
                // // //
                Optional<StateCorreHisDepar> stateCorreHis = mStateCorreHisDeparRepository.getStateCorrelationHisDeparmentById(cId);

                if (!stateCorreHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                stateCorreHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorreHisDeparRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorreHisDeparRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case CLASSIFICATION: {
                // // //
                Optional<StateCorreHisCls> stateCorreHis = mStateCorreHisClsRepository.getStateCorrelationHisClassificationByCid(cId);
                if (!stateCorreHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                stateCorreHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorreHisClsRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorreHisClsRepository.update(cId, itemToBeUpdated.get());
                break;

            }
            case POSITION: {
                // // //
                Optional<StateCorreHisPo> stateCorreHis = mStateCorreHisPoRepository.getStateCorrelationHisPositionByCid(cId);
                if (!stateCorreHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                stateCorreHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorreHisPoRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorreHisPoRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case SALARY: {
                // // //
                Optional<StateCorreHisSala> stateCorreHis = mStateCorreHisSalaRepository.getStateCorrelationHisSalaryByCid(cId);
                if (!stateCorreHis.isPresent()) {
                    return;
                }
                Optional<YearMonthHistoryItem> itemToBeUpdate = stateCorreHis.get().getHistory().stream()
                        .filter(h -> h.identifier().equals(hisId)).findFirst();
                if (!itemToBeUpdate.isPresent()) {
                    return;
                }
                stateCorreHis.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
                mStateCorreHisSalaRepository.update(cId, itemToBeUpdate.get());
                // update item before
                Optional<YearMonthHistoryItem> itemToBeUpdated = stateCorreHis.get().immediatelyBefore(itemToBeUpdate.get());
                if (!itemToBeUpdated.isPresent()) {
                    return;
                }
                mStateCorreHisSalaRepository.update(cId, itemToBeUpdated.get());
                break;
            }
            case INDIVIDUAL :{
                // Update history table
                // In case of date period are exist in the screen
                if (start != null){
                    Optional<StateCorreHisIndivi> existHist = mStateCorreHisIndiviRepository.getStateCorrelationHisIndividualById(employeeId, hisId);
                    if (!existHist.isPresent()) {
                        throw new RuntimeException("invalid StateCorreHisIndivi");
                    }

                    Optional<YearMonthHistoryItem> itemToBeUpdate = existHist.get().getHistory().stream()
                            .filter(h -> h.identifier().equals(hisId)).findFirst();

                    if (!itemToBeUpdate.isPresent()) {
                        throw new RuntimeException("invalid StateCorreHisIndivi");
                    }
                    existHist.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start,
                            end != null ? end : new YearMonth(999912)));
                    mStateCorreHisIndiviRepository.update(employeeId, itemToBeUpdate.get());
                }
                break;
            }
        }
    }
}
