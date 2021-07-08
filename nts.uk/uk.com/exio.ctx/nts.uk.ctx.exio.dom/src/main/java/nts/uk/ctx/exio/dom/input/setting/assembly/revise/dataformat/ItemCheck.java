package nts.uk.ctx.exio.dom.input.setting.assembly.revise.dataformat;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
@Getter
public class ItemCheck{
	boolean isLineError = true; //エラー行を数る
	boolean isCond = true; //True 受入条件がOK、False　受入条件がNOT　OK　　//受入条件をチェック
	//項目の編集値
	Map<Integer, Object> mapLineContent = new HashMap<>();
	
	public ItemCheck(boolean isLineError, boolean isCond, Map<Integer, Object> mapLineContent ) {
		this.isLineError = isLineError;
		this.isCond = isCond;
		this.mapLineContent = mapLineContent;
	}
}