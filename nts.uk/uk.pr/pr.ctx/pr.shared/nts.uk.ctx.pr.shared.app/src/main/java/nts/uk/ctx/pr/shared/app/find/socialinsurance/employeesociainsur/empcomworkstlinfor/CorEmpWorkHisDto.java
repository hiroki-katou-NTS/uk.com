package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Value
public class CorEmpWorkHisDto {

    /**
     * 社員ID
     */
    private String empId;

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 開始年月
     */
    private int startYearMonth;

    /**
     * 終了年月
     */
    private int endYearMonth;


    public static List<CorEmpWorkHisDto> fromDomain(CorEmpWorkHis domain) {

        List<CorEmpWorkHisDto> listCorEmpWorkHis = new ArrayList<CorEmpWorkHisDto>();
        if(domain.getHistory().size() > 0) {
            listCorEmpWorkHis = domain.getHistory().stream().map(item -> new CorEmpWorkHisDto(domain.getEmpId(), item.identifier(), item.start().v(), item.end().v())).collect(Collectors.toList());
        }
        return listCorEmpWorkHis;
    }
}
