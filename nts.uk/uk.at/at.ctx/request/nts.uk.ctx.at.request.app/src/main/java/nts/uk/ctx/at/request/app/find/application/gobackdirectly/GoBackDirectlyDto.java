package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.shared.dom.WorkInformation;

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
	public WorkInformationDto dataWork;

	public static GoBackDirectlyDto convertDto(GoBackDirectly goBackAplication) {
		GoBackDirectlyDto result = new GoBackDirectlyDto();
		result.straightDistinction = goBackAplication.getStraightDistinction().getValue();
		result.straightLine = goBackAplication.getStraightLine().getValue();
		if (goBackAplication.getIsChangedWork().isPresent()) {
			result.isChangedWork = goBackAplication.getIsChangedWork().get().getValue();
		}
		if (goBackAplication.getDataWork().isPresent()) {
			result.dataWork = WorkInformationDto.fromDomain(goBackAplication.getDataWork().get());
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
		Optional<WorkInformation> dataWorkSet = Optional.ofNullable(null);
		if (dataWork != null) {
			dataWorkSet = Optional.of(dataWork.toDomain());
		}
		if (this.getAppID() != null) {
			result.setAppID(this.getAppID());
			
		}
		result.setDataWork(dataWorkSet);
		return result;
	}
}
