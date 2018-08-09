package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DateSpecHdRelationOutput {
	//コード
	private String relationCD;
	//続柄名
	private String relationName;
	//上限日数
	private int maxDate;
}
