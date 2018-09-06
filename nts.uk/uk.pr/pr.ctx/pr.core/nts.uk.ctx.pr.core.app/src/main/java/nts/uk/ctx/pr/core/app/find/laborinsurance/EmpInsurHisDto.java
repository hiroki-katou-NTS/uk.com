package nts.uk.ctx.pr.core.app.find.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;

@AllArgsConstructor
@Value
public class EmpInsurHisDto {
	
	private String cid;
    private List<YearMonthHistoryItem> history;
	    
	    public static EmpInsurHisDto fromDomain(EmpInsurHis domain) {
			return new EmpInsurHisDto(
					domain.getCid(),
					domain.getHistory());
		}
}
