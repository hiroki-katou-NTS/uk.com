package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.GoOutType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.GoOutTypeDispControl;
@AllArgsConstructor
@NoArgsConstructor
public class GoOutTypeDispControlDto {
	/**
	 * 表示する
	 */
	public Integer display;
	
	/**
	 * 外出種類
	 */
	public Integer goOutType;
	
	public static GoOutTypeDispControlDto fromDomain(GoOutTypeDispControl goOutTypeDispControl) {
		return new GoOutTypeDispControlDto(
				goOutTypeDispControl.getDisplay().value,
				goOutTypeDispControl.getGoOutType().value);
	}
	
	public GoOutTypeDispControl toDomain() {
		return new GoOutTypeDispControl(
				EnumAdaptor.valueOf(display, DisplayAtr.class),
				EnumAdaptor.valueOf(goOutType, GoOutType.class));
	}
}
