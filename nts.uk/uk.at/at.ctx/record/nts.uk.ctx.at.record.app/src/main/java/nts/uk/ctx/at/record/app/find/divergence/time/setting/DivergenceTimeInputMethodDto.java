package nts.uk.ctx.at.record.app.find.divergence.time.setting;

public class DivergenceTimeInputMethodDto {
	
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
	
	/** The divergence reason inputed*/
	//乖離理由を入力する
	private boolean divergenceReasonInputed;
	
	/** The divergence reason selected*/
	//乖離理由を選択肢から選ぶ
	private boolean divergenceReasonSelected;

	public DivergenceTimeInputMethodDto(int divergenceTimeNo, String companyId, int divTimeUseSet, String divTimeName,
			int divType, boolean reasonInput, boolean reasonSelect, boolean divergenceReasonInputed,
			boolean divergenceReasonSelected) {
		super();
		this.divergenceTimeNo = divergenceTimeNo;
		this.companyId = companyId;
		this.divTimeUseSet = divTimeUseSet;
		this.divTimeName = divTimeName;
		this.divType = divType;
		this.reasonInput = reasonInput;
		this.reasonSelect = reasonSelect;
		this.divergenceReasonInputed = divergenceReasonInputed;
		this.divergenceReasonSelected = divergenceReasonSelected;
	}
	
	
	
}
