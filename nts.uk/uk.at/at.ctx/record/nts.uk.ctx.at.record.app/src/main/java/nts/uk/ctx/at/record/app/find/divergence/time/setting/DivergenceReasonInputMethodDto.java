package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodSetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectSetMemento;

public class DivergenceReasonInputMethodDto implements DivergenceReasonInputMethodSetMemento,DivergenceReasonInputMethodGetMemento {
	
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
		this.divergenceTimeNo= DivergenceTimeNo;
		
	}

	@Override
	public void setCompanyId(String companyId) {
		this.companyId=companyId;
		
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
	public void setReasons(List<DivergenceReasonSelect> reasons) {
		this.reasons=reasons;
		
	}

	@Override
	public boolean getDivergenceReasonInputed() {
		return divergenceReasonInputed;
	}

	@Override
	public boolean getDivergenceReasonSelected() {
		return divergenceReasonSelected;
	}

	@Override
	public int getDivergenceTimeNo() {
		return divergenceTimeNo;
	}

	@Override
	public String getCompanyId() {
		return companyId;
	}

	@Override
	public List<DivergenceReasonSelect> getReasons() {
		return reasons;
	}
	

}
