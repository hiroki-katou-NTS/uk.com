package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DivergenceTimeInputMethodSaveCommand {
	
	/** The divergence time no. */
	// 乖離時間NO
	private int divergenceTimeNo;
	
	/** The c id. */
	// 会社ID
	private String companyId;
	
	/** The Use classification. */
	// 使用区分
	private int divergenceTimeUseSet;
	/** The divergence time name */
	//乖離時間名称
	private String divergenceTimeName;
	
	/** The divergence type*/
	//乖離の種類
	private int divergenceType;
	
	/** The divergence time error cancel method*/
	//乖離時間のエラーの解除方法
	private boolean reasonInput;
	private boolean reasonSelect;
	
	/** The divergence reason inputed*/
	//乖離理由を入力する
	private boolean divergenceReasonInputed;
	
	/** The divergence reason selected*/
	//乖離理由を選択肢から選ぶ
	private boolean divergenceReasonSelected;
	
	

}
