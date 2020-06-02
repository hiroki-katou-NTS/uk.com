package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author laitv 年月日期間の汎用履歴項目 
 * path :  UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.年月日期間の汎用履歴項目
 */
@AllArgsConstructor
@Getter
public class DateHistoryItem extends DomainObject{

	/** 履歴ID */
	private String hisId;

	/** 社員ID */
	private DatePeriod period;

	public static DateHistoryItem createFromJavaType(String hisId, DatePeriod period) {
		return new DateHistoryItem(hisId, period);
	}
}
