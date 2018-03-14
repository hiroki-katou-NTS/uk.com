package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento;

public class DivergenceReasonInputMethodDto implements DivergenceReasonInputMethodSetMemento {
	
	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;
	
	/** The companyId. */
	// 会社ID
	private String companyId;
	
	/** The divergence reason inputed*/
	//乖離理由を入力する
	private boolean divergenceReasonInputed;
	
	/** The divergence reason selected*/
	//乖離理由を選択肢から選ぶ
	private boolean divergenceReasonSelected;
	
	/**The reason list*/
	//乖離理由の選択肢
	private List<DivergenceReasonSelect> reasons;
	
	

	public DivergenceReasonInputMethodDto() {
		super();
		this.divergenceReasonInputed = divergenceReasonInputed;
		this.divergenceReasonSelected = divergenceReasonSelected;
	}

	@Override
	public void setDivergenceTimeNo(int DivergenceTimeNo) {
		// no code
		
	}

	@Override
	public void setCompanyId(String companyId) {
		// no code
		
	}

	@Override
	public void setDivergenceReasonInputed(boolean divergenceReasonInputed) {
		this.divergenceReasonInputed=divergenceReasonInputed;
		
	}

	@Override
	public void setDivergenceReasonSelected(boolean divergenceReasonSelected) {
		this.divergenceReasonSelected=divergenceReasonSelected;
		
	}

	@Override
	public void setReasons(List<DivergenceReasonSelect> reason) {
		// no code
		
	}
	

}
