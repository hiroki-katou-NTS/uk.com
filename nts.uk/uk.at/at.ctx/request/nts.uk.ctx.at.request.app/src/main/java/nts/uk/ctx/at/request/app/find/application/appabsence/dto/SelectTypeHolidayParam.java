package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectTypeHolidayParam {
	// ・会社ID
	private String companyId;
	// ・申請日リスト
	private List<String> dates;
	// ・休暇申請起動時の表示情報
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	// ・休暇種類
	private ApplyForLeaveDto applyForLeaveDto;
	
	private Integer holidayAppType;
}
