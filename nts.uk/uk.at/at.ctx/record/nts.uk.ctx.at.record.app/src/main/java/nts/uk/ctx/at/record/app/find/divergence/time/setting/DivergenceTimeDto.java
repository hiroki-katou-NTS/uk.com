package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeUseSet;

public class DivergenceTimeDto implements DivergenceTimeSetMemento{
	
	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;
	
	/** The c id. */
	// 会社ID
	private String companyId;
	
	/** The Use classification. */
	// 使用区分
	private int divTimeUseSet;
	/** The divergence time name */
	//乖離時間名称
	private String divTimeName;
	
	/** The divergence type*/
	//乖離の種類
	private int divType;
	
	/** The divergence time error cancel method*/
	//乖離時間のエラーの解除方法
	private boolean reasonInput;
	private boolean reasonSelect;
	
	

	@Override
	public void setDivergenceTimeNo(int divergenceTimeNo) {
		this.divergenceTimeNo =  divergenceTimeNo;
		
	}

	@Override
	public void setCompanyId(String companyId) {
		this.companyId=companyId;
		
	}

	@Override
	public void setDivTimeUseSet(DivergenceTimeUseSet divTimeUset) {
		this.divTimeUseSet = divTimeUset.value;
		
	}

	@Override
	public void setDivTimeName(DivergenceTimeName divTimeName) {
		this.divTimeName = divTimeName.toString();
		
	}

	@Override
	public void setDivType(DivergenceType divType) {
		this.divType = divType.value;
		
	}

	@Override
	public void setErrorCancelMedthod(DivergenceTimeErrorCancelMethod errorCancelMedthod) {
		this.reasonInput = errorCancelMedthod.isReasonInputed();
		this.reasonSelect = errorCancelMedthod.isReasonSelected();
		
	}

	@Override
	public void setTarsetItems(List<Double> targetItems) {
		//no code
		
	}
	
	
	
	
}
