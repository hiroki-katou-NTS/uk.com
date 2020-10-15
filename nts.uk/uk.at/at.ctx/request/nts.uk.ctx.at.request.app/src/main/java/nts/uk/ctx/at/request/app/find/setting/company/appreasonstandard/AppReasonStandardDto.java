package nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppReasonStandardDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請種類
	 */
	private int applicationType;
	
	/**
	 * 定型理由項目
	 */
	private List<ReasonTypeItemDto> reasonTypeItemLst;
	
	/**
	 * 休暇申請の種類
	 */
	private Integer opHolidayAppType;
	
	public static AppReasonStandardDto fromDomain(AppReasonStandard appReasonStandard) {
		return new AppReasonStandardDto(
				appReasonStandard.getCompanyID(), 
				appReasonStandard.getApplicationType().value, 
				appReasonStandard.getReasonTypeItemLst().stream().map(x -> ReasonTypeItemDto.fromDomain(x)).collect(Collectors.toList()), 
				appReasonStandard.getOpHolidayAppType().map(x -> x.value).orElse(null));
	}
	
	public AppReasonStandard toDomain() {
		return new AppReasonStandard(
				companyID, 
				EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
				reasonTypeItemLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				opHolidayAppType == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opHolidayAppType, HolidayAppType.class)));
	}
}
