package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;


// TODO: Auto-generated Javadoc
/**
 * Gets the reasons.
 *
 * @return the reasons
 */
@Getter

/**
 * Sets the reasons.
 *
 * @param reasons the new reasons
 */
@Setter

/**
 * Instantiates a new divergence reason input method.
 *
 * @param divergenceTimeNo the divergence time no
 * @param companyId the company id
 * @param divergenceReasonInputed the divergence reason inputed
 * @param divergenceReasonSelected the divergence reason selected
 * @param reasons the reasons
 */
@AllArgsConstructor
public class DivergenceReasonInputMethod extends AggregateRoot{
	
	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;
	
	/** The companyId. */
	// 会社ID
	private String companyId;
	
	/**  The divergence reason inputed. */
	//乖離理由を入力する
	private boolean divergenceReasonInputed;
	
	/**  The divergence reason selected. */
	//乖離理由を選択肢から選ぶ
	private boolean divergenceReasonSelected;
	
	/** The reason list. */
	//乖離理由の選択肢
	private List<DivergenceReasonSelect> reasons;
	
	
	/**
	 * Instantiates a new divergence reason input method.
	 *
	 * @param memento the memento
	 */
	public DivergenceReasonInputMethod (DivergenceReasonInputMethodGetMemento memento){
		this.divergenceTimeNo=memento.getDivergenceTimeNo();
		this.companyId = memento.getCompanyId();
		this.divergenceReasonInputed = memento.getDivergenceReasonInputed();
		this.divergenceReasonSelected = memento.getDivergenceReasonSelected();
		this.reasons = memento.getReasons();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DivergenceReasonInputMethodSetMemento memento)
	{
		memento.setDivergenceTimeNo(this.divergenceTimeNo);
		memento.setCompanyId(this.companyId);
		memento.setDivergenceReasonInputed(this.divergenceReasonInputed);
		memento.setDivergenceReasonSelected(this.divergenceReasonSelected);
		memento.setReasons(this.reasons);
	}

}
