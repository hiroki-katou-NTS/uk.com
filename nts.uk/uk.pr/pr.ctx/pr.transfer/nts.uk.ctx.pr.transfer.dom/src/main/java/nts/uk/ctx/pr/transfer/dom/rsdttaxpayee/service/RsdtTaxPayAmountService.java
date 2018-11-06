package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.service;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.EmployeeResidentTaxPayeeInfoImport;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.PayeeInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.PayeeInfoImport;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoImport;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RsdtTaxPayAmountService {

    @Inject
    private EmployeeResidentTaxPayeeInfoAdapter empRsdtTaxPayeeInfoAdapter;

    @Inject
    private PayeeInfoAdapter payeeInfoAdapter;

    @Inject
    private EmployeeResidentTaxPayAmountInfoAdapter empRsdtTaxPayAmountInfoAdapter;

    @Inject
    private ResidentTaxPayeeRepository rsdtTaxPayeeRepo;

    /**
     * 対象データ取得処理
     */
    public List<RsdtTaxPayAmountDto> getRsdtTaxPayAmount(List<String> listSId, int year) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「社員住民税納付先情報」をすべて取得する
        List<String> listHistId = new ArrayList<>();
        YearMonth periodYM = YearMonth.of(year, 6);
        List<EmployeeResidentTaxPayeeInfoImport> listEmpRsdtTaxPayeeInfo = empRsdtTaxPayeeInfoAdapter
                .getEmpRsdtTaxPayeeInfo(listSId, periodYM);
        for (EmployeeResidentTaxPayeeInfoImport taxPayee : listEmpRsdtTaxPayeeInfo) {
            for (YearMonthHistoryItem histItem : taxPayee.getHistoryItems()) {
                listHistId.add(histItem.identifier());
            }
        }
        // ドメインモデル「納付先情報」をすべて取得する
        List<PayeeInfoImport> listPayeeInfo = payeeInfoAdapter.getListPayeeInfo(listHistId);
        List<String> listRsdtTaxPayeeCd = listPayeeInfo.stream().map(x -> x.getResidentTaxPayeeCd())
                .collect(Collectors.toList());

        // ドメインモデル「社員住民税納付額情報」をすべて取得する
        List<EmployeeResidentTaxPayAmountInfoImport> listEmpRsdtTaxPayAmountInfo = empRsdtTaxPayAmountInfoAdapter
                .getListEmpRsdtTaxPayAmountInfo(listSId, year);

        // ドメインモデル「住民税納付先」をすべて取得する
        List<ResidentTaxPayee> listRsdtTaxPayee = rsdtTaxPayeeRepo.getListResidentTaxPayee(cid, listRsdtTaxPayeeCd);

        return this.mapRsdtTaxPayAmount(listSId, listEmpRsdtTaxPayeeInfo, listPayeeInfo, listEmpRsdtTaxPayAmountInfo,
                listRsdtTaxPayee);
    }

    /**
     * mapRsdtTaxPayAmount
     */
    private List<RsdtTaxPayAmountDto> mapRsdtTaxPayAmount(List<String> listSId,
                                                          List<EmployeeResidentTaxPayeeInfoImport> listEmpRsdtTaxPayeeInfo,
                                                          List<PayeeInfoImport> listPayeeInfo,
                                                          List<EmployeeResidentTaxPayAmountInfoImport> listEmpRsdtTaxPayAmountInfo,
                                                          List<ResidentTaxPayee> listRsdtTaxPayee) {
        // Map listEmpRsdtTaxPayeeInfo
        HashMap<String, String> mapRsdtTaxPayeeInfo = new HashMap<>();
        for (EmployeeResidentTaxPayeeInfoImport taxPayee : listEmpRsdtTaxPayeeInfo) {
            if (taxPayee.getHistoryItems().isEmpty()) continue;
            mapRsdtTaxPayeeInfo.put(taxPayee.getSid(), taxPayee.getHistoryItems().get(0).identifier());
        }

        // Map listPayeeInfo
        HashMap<String, String> mapPayeeInfo = new HashMap<>();
        for (PayeeInfoImport payeeInfo : listPayeeInfo) {
            mapPayeeInfo.put(payeeInfo.getHistId(), payeeInfo.getResidentTaxPayeeCd());
        }

        // Map listEmpRsdtTaxPayAmountInfo
        HashMap<String, EmployeeResidentTaxPayAmountInfoImport> mapEmpRsdtTaxPayAmountInfo = new HashMap<>();
        for (EmployeeResidentTaxPayAmountInfoImport amountInfo : listEmpRsdtTaxPayAmountInfo) {
            mapEmpRsdtTaxPayAmountInfo.put(amountInfo.getSid(), amountInfo);
        }

        // Map listRsdtTaxPayee
        HashMap<String, String> mapRsdtTaxPayee = new HashMap<>();
        for (ResidentTaxPayee taxPayee : listRsdtTaxPayee) {
            mapRsdtTaxPayee.put(taxPayee.getCode().v(), taxPayee.getName().v());
        }

        List<RsdtTaxPayAmountDto> result = new ArrayList<>();
        for (String sid : listSId) {
            RsdtTaxPayAmountDto amount = new RsdtTaxPayAmountDto();
            amount.setSid(sid);
            amount.setRsdtTaxPayeeName(this.getRsdtTaxPayeeName(sid, mapRsdtTaxPayeeInfo, mapPayeeInfo,
                    mapRsdtTaxPayee));

            if (mapEmpRsdtTaxPayAmountInfo.containsKey(sid)) {
                EmployeeResidentTaxPayAmountInfoImport amountnInfo = mapEmpRsdtTaxPayAmountInfo.get(sid);
                amount.setYear(amountnInfo.getYear().v());
                amount.setInputAtr(amountnInfo.getInputAtr());
                amount.setMonthlyPaymentAmount(amountnInfo.getMonthlyPaymentAmount());
            }
            result.add(amount);
        }

        return result;
    }

    /**
     * getRsdtTaxPayeeName
     */
    private String getRsdtTaxPayeeName(String sid, HashMap<String, String> mapRsdtTaxPayeeInfo,
                                       HashMap<String, String> mapPayeeInfo, HashMap<String, String> mapRsdtTaxPayee) {
        if (!mapRsdtTaxPayeeInfo.containsKey(sid)) return "";
        String histId = mapRsdtTaxPayeeInfo.get(sid);
        if (!mapPayeeInfo.containsKey(histId)) return "";
        String rsdtTaxPayeeCd = mapPayeeInfo.get(histId);
        if (!mapRsdtTaxPayee.containsKey(rsdtTaxPayeeCd)) return "";
        return mapRsdtTaxPayee.get(rsdtTaxPayeeCd);
    }
}
