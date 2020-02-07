package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorWorkFormInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorWorkFormInfoRepository;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class CorEmpWorkHisFinder {

    @Inject
    private CorEmpWorkHisRepository repository;

    @Inject
    private CorWorkFormInfoRepository corWorkFormInfoRepository;

    public List<CorEmpWorkHisDto> getAllCorEmpWorkHisByEmpId(String empId){
        Optional<CorEmpWorkHis> domain  = repository.getAllCorEmpWorkHisByEmpId(empId);
        if(domain.isPresent()){
            return CorEmpWorkHisDto.fromDomain(domain.get());
        }
        return null;
    }

    public String getHisIdCorEmpWorkHisEmpId(String empId, YearMonth startDate, YearMonth endDate){
        Optional<CorEmpWorkHis> domain  = repository.getAllCorEmpWorkHisByEmpId(empId);

        if(domain.isPresent()){
            Optional<YearMonthHistoryItem> historyItem = domain.get().getHistory().stream().filter(x -> x.start().lessThanOrEqualTo(startDate) && x.end().greaterThanOrEqualTo(startDate)).findFirst();
            if(historyItem.isPresent()){
                return historyItem.get().identifier();
            }
        }
        return null;
    }

    public CorWorkFormInfoDto getCorWorkFormInfoDto(String empId, GeneralDate startDate, GeneralDate endDate){
        String hisId = this.getHisIdCorEmpWorkHisEmpId(empId,startDate.yearMonth(),endDate.yearMonth());
        if(hisId != null){
            Optional<CorWorkFormInfo> corWorkFormInfo = corWorkFormInfoRepository.getCorWorkFormInfoByHisId(empId,hisId);
            if(corWorkFormInfo.isPresent()){
                return CorWorkFormInfoDto.fromDomain(corWorkFormInfo.get());
            }
        }

        return null;
    }
}
