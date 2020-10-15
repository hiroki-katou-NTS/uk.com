package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
		result.straightDistinction = goBackAplication.getStraightDistinction().value;
		result.straightLine = goBackAplication.getStraightLine().value;
		if (goBackAplication.getIsChangedWork().isPresent()) {
			result.isChangedWork = goBackAplication.getIsChangedWork().get().value;
		}
		if (goBackAplication.getDataWork().isPresent()) {
			result.dataWork = WorkInformationDto.fromDomain(goBackAplication.getDataWork().get());
		}
		return result;
	}

	public GoBackDirectly toDomain() {
		Optional<NotUseAtr> isChange = Optional.ofNullable(null);
		
		if (isChangedWork != null) {
			isChange = Optional.ofNullable(EnumAdaptor.valueOf(isChangedWork, NotUseAtr.class));
		}
		GoBackDirectly result = new GoBackDirectly();
		result.setStraightDistinction(EnumAdaptor.valueOf(straightDistinction, NotUseAtr.class));
		result.setStraightLine(EnumAdaptor.valueOf(straightLine, NotUseAtr.class));
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
