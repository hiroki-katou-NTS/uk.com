package nts.uk.ctx.pr.core.app.find.employaverwage;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.command.employaverwage.EmployeeComand;
import nts.uk.ctx.pr.core.dom.adapter.employee.EmployeeInfoAverAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWage;
import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWageRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmployeeAverWageFinder {

    @Inject
    private PerProcessClsSetRepository perProcessClsSetRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;

    @Inject
    private SetDaySupportRepository setDaySupportRepository;

    @Inject
    private EmployAverWageRepository employAverWageRepository;

    @Inject
    private EmployeeInfoAverAdapter employeeInfoAverAdapter;


    public List<String> init() {
        int processCateNo = 1;
        List<String> response = new ArrayList<>();
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
            response.add(currProcessDate.get().getGiveCurrTreatYear().v().toString());
            response.add(setDaySupport.isPresent() ? setDaySupport.get().getEmpExtraRefeDate().toString() : null);

        }
        return response;
    }

    public void findByEmployeeId(EmployeeComand employeeComand) {

    }

    public List<EmployeeInfoDto> getEmpInfoDept(EmployeeComand param) {
        // ドメインモデル「所属部門」をすべて取得する
        return employeeInfoAverAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(param.getEmployeeIds(), GeneralDate.fromString(param.getBaseDate(),"yyyy/MM/dd"),
                        false,
                        true,
                        false,
                        true,
                        false,
                        false)).stream().map(x -> {
                    EmployeeInfoDto dto = new EmployeeInfoDto();
                    dto.setEmployeeId(x.getEmployeeId());
                    dto.setEmployeeCode(x.getEmployeeCode());
                    dto.setBusinessName(x.getBusinessName());
                    if(x.getEmployment() != null) {
                        dto.setEmploymentName(x.getEmployment().getEmploymentName());
                    } else {
                        dto.setEmploymentName("");
                    }
                    if(x.getDepartment() != null){
                        dto.setDepartmentName(x.getDepartment().getDepartmentName());
                    }else{
                        dto.setDepartmentName("");
                    }
                    if(param.getGiveCurrTreatYear() != null && !param.getGiveCurrTreatYear().equals("Invalid date")) {
                        Optional<EmployAverWage> employAverWage = employAverWageRepository.getEmployAverWageById(x.getEmployeeId(),Integer.valueOf(param.getGiveCurrTreatYear().replaceAll("/","")));
                        if(employAverWage.isPresent())
                            dto.setAverageWage(employAverWage.get().getAverageWage().v());
                    }
                    return dto;
                }).collect(Collectors.toList());
    }


    public void show(EmployAverWageDto employAverWageDto){

        // ドメインモデル「社員平均賃金」をすべて取得する

    }


}
