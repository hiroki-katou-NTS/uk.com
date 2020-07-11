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
public class GoBackAplicationDto extends ApplicationDto {
	// 直帰区分
	public int straightDistinction;
	// 直行区分
	public int straightLine;
	// 勤務を変更する
	public Integer isChangedWork;
	// 勤務情報
	public DataWorkDto dataWork;

	public static GoBackAplicationDto convertDto(GoBackDirectly goBackAplication) {
		GoBackAplicationDto result = new GoBackAplicationDto();
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
		if (Optional.of(isChangedWork).isPresent()) {
			isChange = Optional.of(EnumAdaptor.valueOf(isChangedWork, EnumConstant.class));
		}
		GoBackDirectly result = new GoBackDirectly(null);
		result.setStraightDistinction(EnumAdaptor.valueOf(straightDistinction, EnumConstant.class));
		result.setStraightLine(EnumAdaptor.valueOf(straightLine, EnumConstant.class));
		result.setIsChangedWork(isChange);
		Optional<DataWork> dataWorkSet = Optional.ofNullable(null);
		if (Optional.of(dataWork).isPresent()) {
			dataWorkSet = Optional.of(dataWork.toDomain());
		}
		result.setDataWork(dataWorkSet);
		return result;
	}
}
