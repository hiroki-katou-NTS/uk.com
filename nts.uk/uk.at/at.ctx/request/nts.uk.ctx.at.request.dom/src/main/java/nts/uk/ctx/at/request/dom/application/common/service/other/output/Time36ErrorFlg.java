package nts.uk.ctx.at.request.dom.application.common.service.other.output;

public enum Time36ErrorFlg {
	/** 月間エラー*/
	MONTH(0,"月間エラー"),
	/** 年間エラー*/
	YEAR(1,"年間エラー"),
	/** 上限月間時間エラー*/
	MAX_MONTH(2,"上限月間時間エラー"),
	/** 上限複数月平均時間エラー*/
	AVERAGE_MONTH(3,"上限複数月平均時間エラー"),
	/**	上限年間時間エラー*/
	MAX_YEAR(4,"上限年間時間エラー");
	 
	public int value;
	
	public String nameId;
	Time36ErrorFlg(int type,String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
