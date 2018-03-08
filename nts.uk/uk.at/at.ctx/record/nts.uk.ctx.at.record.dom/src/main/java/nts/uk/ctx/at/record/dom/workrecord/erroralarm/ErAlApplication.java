package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * エラー発生時に呼び出す申請一覧
 * @author tutk
 *
 */

@Getter
public class ErAlApplication extends AggregateRoot {

	/**会社ID*/
	private String companyID;
	/**エラーアラームコード*/
	private String errorAlarmCode;
	/**呼び出す申請一覧*/
	private List<Integer> appType;
	public ErAlApplication(String companyID, String errorAlarmCode, List<Integer> appType) {
		super();
		this.companyID = companyID;
		this.errorAlarmCode = errorAlarmCode;
		this.appType = appType;
	}
	 
	
	
	
}
