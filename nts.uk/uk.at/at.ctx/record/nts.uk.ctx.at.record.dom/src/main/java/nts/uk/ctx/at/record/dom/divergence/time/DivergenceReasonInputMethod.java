package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Class DivergenceReasonInputMethod.
 */
//乖離理由の入力方法
@Getter
@Setter
@AllArgsConstructor
public class DivergenceReasonInputMethod extends AggregateRoot {

	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;

	/** The companyId. */
	// 会社ID
	private String companyId;

	/** The divergence reason inputed. */
	// 乖離理由を入力する
	private boolean divergenceReasonInputed;

	/** The divergence reason selected. */
	// 乖離理由を選択肢から選ぶ
	private boolean divergenceReasonSelected;

	/** The reason list. */
	// 乖離理由の選択肢
	private List<DivergenceReasonSelect> reasons;

	/**
	 * Instantiates a new divergence reason input method.
	 *
	 * @param memento
	 *            the memento
	 */
	public DivergenceReasonInputMethod(DivergenceReasonInputMethodGetMemento memento) {
		this.divergenceTimeNo = memento.getDivergenceTimeNo();
		this.companyId = memento.getCompanyId();
		this.divergenceReasonInputed = memento.getDivergenceReasonInputed();
		this.divergenceReasonSelected = memento.getDivergenceReasonSelected();
		this.reasons = memento.getReasons();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DivergenceReasonInputMethodSetMemento memento) {
		memento.setDivergenceTimeNo(this.divergenceTimeNo);
		memento.setCompanyId(this.companyId);
		memento.setDivergenceReasonInputed(this.divergenceReasonInputed);
		memento.setDivergenceReasonSelected(this.divergenceReasonSelected);
		memento.setReasons(this.reasons);
	}
	
	/**
	 * 	[1] 乖離理由に対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdByNo() {
		List<Integer> listAttdId = new ArrayList<>();
		listAttdId.addAll(this.getAttdIdReasonInput(this.divergenceTimeNo));
		listAttdId.addAll(this.getAttdIdReasonSelect(this.divergenceTimeNo));
		return listAttdId;
	}
	
	/**
	 * 	[2] 利用できない日次の勤怠項目を取得する
	 * 
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdNotAvailable(UseClassification useAtr) {
		List<Integer> listAttdId = new ArrayList<>();
		if(!this.useDivergenceReasonInput(useAtr)) {
			listAttdId.addAll(this.getAttdIdReasonInput(this.divergenceTimeNo));
		}
		if(!this.useDivergenceReasonSelected(useAtr)) {
			listAttdId.addAll(this.getAttdIdReasonSelect(this.divergenceTimeNo));
		}
		return listAttdId;
	}
	
	/**
	 * 	[3] 乖離理由入力を利用するか
	 * @return
	 */
	public boolean useDivergenceReasonInput(UseClassification useAtr) {
		if(useAtr == UseClassification.UseClass_NotUse) {
			return false;
		}
		return this.divergenceReasonInputed;
	}
	
	/**
	 *  [4] 乖離理由を選択肢から選ぶを利用するか
	 * @return
	 */
	public boolean useDivergenceReasonSelected(UseClassification useAtr) {
		if(useAtr == UseClassification.UseClass_NotUse) {
			return false;
		}
		return this.divergenceReasonSelected;
	}

	private List<Integer> getAttdIdReasonInput( int no) {
		switch(no) {
		case 1:
			return Arrays.asList(439);
		case 2: 
			return Arrays.asList(444);
		case 3: 
			return Arrays.asList(449);
		case 4: 
			return Arrays.asList(454);
		case 5: 
			return Arrays.asList(459);
		case 6: 
			return Arrays.asList(802);
		case 7: 
			return Arrays.asList(807);
		case 8: 
			return Arrays.asList(812);
		case 9: 
			return Arrays.asList(817);
		default : //10
			return Arrays.asList(822);
		}
	}
	
	private List<Integer> getAttdIdReasonSelect(int no) {
		switch(no) {
		case 1:
			return Arrays.asList(438);
		case 2: 
			return Arrays.asList(443);
		case 3: 
			return Arrays.asList(448);
		case 4: 
			return Arrays.asList(453);
		case 5: 
			return Arrays.asList(458);
		case 6: 
			return Arrays.asList(801);
		case 7: 
			return Arrays.asList(806);
		case 8: 
			return Arrays.asList(811);
		case 9: 
			return Arrays.asList(816);
		default : //10
			return Arrays.asList(821);
		}
	}
}
