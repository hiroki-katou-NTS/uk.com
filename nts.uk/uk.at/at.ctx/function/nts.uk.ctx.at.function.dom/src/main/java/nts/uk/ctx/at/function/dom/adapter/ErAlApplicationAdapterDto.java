package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ErAlApplicationAdapterDto {
	/**会社ID*/
	private String companyID;
	/**エラーアラームコード*/
	private String errorAlarmCode;
	/**呼び出す申請一覧*/
	private List<Integer> appType;
	public ErAlApplicationAdapterDto(String companyID, String errorAlarmCode, List<Integer> appType) {
		super();
		this.companyID = companyID;
		this.errorAlarmCode = errorAlarmCode;
		this.appType = appType;
	}
	
}
