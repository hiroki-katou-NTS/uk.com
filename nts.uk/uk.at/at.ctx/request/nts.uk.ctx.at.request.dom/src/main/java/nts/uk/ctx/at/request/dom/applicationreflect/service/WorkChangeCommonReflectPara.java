package nts.uk.ctx.at.request.dom.applicationreflect.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.CommonReflectPara;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkChangeCommonReflectPara {
	private CommonReflectPara commonPara;
	/**
	 * 休日を除外する
	 */
	private Integer excludeHolidayAtr;
}
