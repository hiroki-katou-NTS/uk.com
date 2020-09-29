package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * * TanLV 
 * 遅刻早退取消申請設定
 *
 */
@Getter
@Setter
public class LateEarlyRequest_Old extends AggregateRoot {
	/** * 会社ID */
	private String companyId;
	
	/** * 実績を表示する */
	private DisplayAtr showResult;

	/**
	 * Contructor
	 * 
	 * @param companyId
	 * @param showResult
	 */
	public LateEarlyRequest_Old(String companyId, int showResult) {
		
		this.companyId = companyId;
		this.showResult = EnumAdaptor.valueOf(showResult, DisplayAtr.class);;
	}
	
	/**
	 * Create From Java Type
	 * 
	 * @param companyId
	 * @param showResult
	 * @return
	 */
	public static LateEarlyRequest_Old createFromJavaType(String companyId, int showResult) {

		return new LateEarlyRequest_Old(companyId, showResult);
	}
}
