package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
@Data
@AllArgsConstructor
@NoArgsConstructor
//直行直帰申請起動時の表示情報
public class InforGoBackCommonDirectDto {
//	勤務種類初期選択
	private String workType;
//	就業時間帯初期選択
	private String workTime;
//	申請表示情報
	private AppDispInfoStartupDto appDispInfoStartup;
//	直行直帰申請の反映
	private GoBackReflectDto goBackReflect;
//	勤務種類リスト
	private List<WorkTypeDto> lstWorkType;
//	直行直帰申請
	private GoBackDirectlyDto goBackApplication;
	
	private List<TimeZoneUseDto> timezones;
	
	public static InforGoBackCommonDirectDto fromDomain(InforGoBackCommonDirectOutput value) {
		List<TimeZoneUseDto> timezones = value.getTimezones().stream().map(x -> TimeZoneUseDto.fromDomain(x)).collect(Collectors.toList());
		return new InforGoBackCommonDirectDto(
				value.getWorkType(),
				value.getWorkTime(),
				AppDispInfoStartupDto.fromDomain(value.getAppDispInfoStartup()),
				GoBackReflectDto.fromDomain(value.getGoBackReflect()),
				value.getLstWorkType().stream().map(item -> WorkTypeDto.fromDomain(item)).collect(Collectors.toList()),
				value.getGoBackDirectly().isPresent() ? GoBackDirectlyDto.convertDto(value.getGoBackDirectly().get()) : null,
				timezones);
	}
	
	public InforGoBackCommonDirectOutput toDomain() {
		InforGoBackCommonDirectOutput info = new InforGoBackCommonDirectOutput();
		info.setWorkType(workType);
		info.setWorkTime(workTime);
		info.setAppDispInfoStartup(appDispInfoStartup.toDomain());
		info.setGoBackReflect(goBackReflect.toDomain());
		info.setLstWorkType(lstWorkType.stream().map(item -> item.toDomain()).collect(Collectors.toList()));
		if(goBackApplication != null) {
			info.setGoBackDirectly(Optional.of(goBackApplication.toDomain()));
		}else {
			info.setGoBackDirectly(Optional.ofNullable(null));
		}
		List<TimezoneUse> timezones = Collections.emptyList();
		if (!CollectionUtil.isEmpty(this.timezones)) {
			timezones = this.timezones.stream().map(x -> x.toDomain()).collect(Collectors.toList());			
		}
		info.setTimezones(timezones);
		return info;
	}
	
}
