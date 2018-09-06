package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHis;
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
