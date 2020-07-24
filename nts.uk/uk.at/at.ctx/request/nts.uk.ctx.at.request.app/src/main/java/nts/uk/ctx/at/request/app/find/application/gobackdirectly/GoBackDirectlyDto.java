package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.DataWork;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;

//直行直帰申請
@AllArgsConstructor
@NoArgsConstructor
public class GoBackDirectlyDto extends ApplicationDto {
	// 直帰区分
	public int straightDistinction;
	// 直行区分
	public int straightLine;
	// 勤務を変更する
	public Integer isChangedWork;
	// 勤務情報
	public DataWorkDto dataWork;

	public static GoBackDirectlyDto convertDto(GoBackDirectly goBackAplication) {
		GoBackDirectlyDto result = new GoBackDirectlyDto();
		result.straightDistinction = goBackAplication.getStraightDistinction().getValue();
		result.straightLine = goBackAplication.getStraightLine().getValue();
		if (goBackAplication.getIsChangedWork().isPresent()) {
			result.isChangedWork = goBackAplication.getIsChangedWork().get().getValue();
		}
		if (goBackAplication.getDataWork().isPresent()) {
			result.dataWork = DataWorkDto.fromDomain(goBackAplication.getDataWork().get());
		}
		return result;
	}

	public GoBackDirectly toDomain() {
		Optional<EnumConstant> isChange = Optional.ofNullable(null);
		if (isChangedWork != null) {
			isChange = Optional.ofNullable(new EnumConstant(isChangedWork, "", ""));
		}
		GoBackDirectly result = new GoBackDirectly();
		result.setStraightDistinction(new EnumConstant(straightDistinction, "", ""));
		result.setStraightLine(new EnumConstant(straightLine, "", ""));
		result.setIsChangedWork(isChange);
		Optional<DataWork> dataWorkSet = Optional.ofNullable(null);
		if (Optional.of(dataWork).isPresent()) {
			dataWorkSet = Optional.of(dataWork.toDomain());
		}
		result.setDataWork(dataWorkSet);
		return result;
	}
}
