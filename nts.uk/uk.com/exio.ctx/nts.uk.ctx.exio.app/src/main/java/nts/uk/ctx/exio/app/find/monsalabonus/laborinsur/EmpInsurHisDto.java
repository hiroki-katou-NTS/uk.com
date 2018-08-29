package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHis;

@AllArgsConstructor
@Value
public class EmpInsurHisDto {
	
	private String cid;
    private String hisId;
    private int startDate;
    private int endDate;
	    
	    public static EmpInsurHisDto fromDomain(EmpInsurHis domain) {
			return new EmpInsurHisDto(
					domain.getCid(),
					domain.getHisId(),
					domain.getEndDate(),
					domain.getStartDate());
		}
}
