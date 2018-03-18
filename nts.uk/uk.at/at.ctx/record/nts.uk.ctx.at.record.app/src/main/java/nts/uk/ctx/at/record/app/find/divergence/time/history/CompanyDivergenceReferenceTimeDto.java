package nts.uk.ctx.at.record.app.find.divergence.time.history;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeValue;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
public class CompanyDivergenceReferenceTimeDto implements CompanyDivergenceReferenceTimeSetMemento{
	
	private int divergenceTimeNo;
	
	private int notUseAtr;
	
	private DivergenceReferenceTimeValueDto divergenceReferenceTimeValue;
	
	public CompanyDivergenceReferenceTimeDto() {
		super();
	}

	@Override
	public void setDivergenceTimeNo(Integer divergenceTimeNo) {
		this.divergenceTimeNo = divergenceTimeNo.intValue();
	}

	@Override
	public void setCompanyId(String companyId) {
		//no coding
	}

	@Override
	public void setNotUseAtr(NotUseAtr notUseAtr) {
		this.notUseAtr = notUseAtr.value;
	}

	@Override
	public void setHistoryId(String historyId) {
		// no coding
	}

	@Override
	public void setDivergenceReferenceTimeValue(Optional<DivergenceReferenceTimeValue> divergenceReferenceTimeValue) {
		if (divergenceReferenceTimeValue.isPresent()){
			this.divergenceReferenceTimeValue = new DivergenceReferenceTimeValueDto(divergenceReferenceTimeValue.get().getAlarmTime().get().valueAsMinutes(),
														divergenceReferenceTimeValue.get().getErrorTime().get().valueAsMinutes());
		}
	}

}
