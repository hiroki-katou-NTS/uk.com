package nts.uk.ctx.at.record.app.find.divergence.time.history;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeValue;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class CompanyDivergenceReferenceTimeDto implements CompanyDivergenceReferenceTimeSetMemento{
	
	private int divergenceTimeNo;
	
	private int notUseAtr;
	
	private DivergenceReferenceTimeValueDto divergenceReferenceTimeValue;

	@Override
	public void setDivergenceTimeNo(DivergenceType divergenceTimeNo) {
		this.divergenceTimeNo = divergenceTimeNo.value;
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
//		if (divergenceReferenceTimeValue.isPresent()){
//			this.divergenceReferenceTimeValue = divergenceReferenceTimeValue;
//		}
	}

}
