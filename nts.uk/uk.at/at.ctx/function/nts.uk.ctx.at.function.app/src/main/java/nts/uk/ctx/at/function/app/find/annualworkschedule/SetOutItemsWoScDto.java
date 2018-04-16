package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
@Value
public class SetOutItemsWoScDto {
	/**
	* 会社ID
	*/
	private String cid;
	/**
	* コード
	*/
	private int cd;
	/**
	* 36協定の表示設定
	*/
	private int dispSettAgr36;
	/**
	* 名称
	*/
	private String name;
	/**
	* 
	*/
	private int outNumExceedTime36Agr;
	/**
	* 
	*/
	private int displayFormat;
	
	
	public static SetOutItemsWoScDto fromDomain(SetOutItemsWoSc domain) {
		return new SetOutItemsWoScDto(domain.getCid(), domain.getCd(), domain.getDispSettAgr36(), domain.getName(), domain.getOutNumExceedTime36Agr(), domain.getDisplayFormat());
	}
}
