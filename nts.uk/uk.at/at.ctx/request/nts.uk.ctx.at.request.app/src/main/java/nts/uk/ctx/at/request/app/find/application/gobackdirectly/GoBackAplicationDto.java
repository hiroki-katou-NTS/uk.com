package nts.uk.ctx.at.request.app.find.application.gobackdirectly;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackAplication;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
//直行直帰申請
public class GoBackAplicationDto extends ApplicationDto_New {
//	直帰区分
	private int straightDistinction;
//	直行区分
	private int straightLine;
//	勤務を変更する
	private int isChangedWork;
//	勤務情報
	private DataWorkDto dataWork;
	
	public static GoBackAplicationDto convertDto(Application_New value) {
		ApplicationDto_New x = ApplicationDto_New.fromDomain(value);
		GoBackAplicationDto y = (GoBackAplicationDto)x;
		y.setStraightDistinction(((GoBackAplication)value).getStraightDistinction().getValue());
		y.setStraightLine(((GoBackAplication)value).getStraightLine().getValue());
		y.setIsChangedWork( ((GoBackAplication)value).getIsChangedWork().isPresent()? ((GoBackAplication)value).getIsChangedWork().get().getValue() : -1);
		y.setDataWork(
				((GoBackAplication)value).getDataWork().isPresent() ? 
						DataWorkDto.convertDto(((GoBackAplication)value).getDataWork().get()) : null
				);
		return null;
	}
}
