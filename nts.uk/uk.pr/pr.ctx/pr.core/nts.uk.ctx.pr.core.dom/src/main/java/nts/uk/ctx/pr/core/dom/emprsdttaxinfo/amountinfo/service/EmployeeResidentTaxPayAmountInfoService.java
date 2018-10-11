package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.service;

import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfoRepository;
import nts.uk.shr.com.time.calendar.Year;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class EmployeeResidentTaxPayAmountInfoService {

    @Inject
    private EmployeeResidentTaxPayAmountInfoRepository empRsdtTaxPayAmountInfoRepository;

    /**
     * 登録処理
     */
    public void registerTaxPayAmount(List<EmployeeResidentTaxPayAmountInfo> listEmpRsdtTaxPayAmountInfo, int year) {
        List<String> listSid = new ArrayList<>();
        for (EmployeeResidentTaxPayAmountInfo empAmount : listEmpRsdtTaxPayAmountInfo) {
            listSid.add(empAmount.getSid());
            empAmount.setYear(new Year(year));
        }
        // ドメインモデル「社員住民税納付額情報」を取得する
        Map<String, EmployeeResidentTaxPayAmountInfo> mapPayAmount = empRsdtTaxPayAmountInfoRepository
                .getListEmpRsdtTaxPayAmountInfo(listSid, year).stream()
                .collect(Collectors.toMap(EmployeeResidentTaxPayAmountInfo::getSid, x -> x));

        // 取得できなかった場合
        List<EmployeeResidentTaxPayAmountInfo> listPayAmountAdd = listEmpRsdtTaxPayAmountInfo.stream()
                .filter(x -> mapPayAmount.containsKey(x.getSid())).collect(Collectors.toList());
        // 取得できた場合
        List<EmployeeResidentTaxPayAmountInfo> listPayAmountUpdate = listEmpRsdtTaxPayAmountInfo.stream()
                .filter(x -> mapPayAmount.containsKey(x.getSid())).collect(Collectors.toList());

        // ドメインモデル「社員住民税納付額情報」を新規登録する
        empRsdtTaxPayAmountInfoRepository.addAll(listPayAmountAdd);
        //ドメインモデル「社員住民税納付額情報」を更新登録する
        empRsdtTaxPayAmountInfoRepository.updateAll(listPayAmountUpdate);
    }
}
