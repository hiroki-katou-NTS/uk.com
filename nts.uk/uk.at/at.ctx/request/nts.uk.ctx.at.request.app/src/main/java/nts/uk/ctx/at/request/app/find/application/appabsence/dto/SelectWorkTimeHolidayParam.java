package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectWorkTimeHolidayParam {
	// ・会社ID
	private String companyId;
	// ・休暇申請起動時の表示情報
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
	// ・勤務種類コード
	private String workTypeCode;
	// ・就業時間帯コード<Optional>
	private String workTimeCodeOp;
	// 社員ID
	private String employeeId;
	// 年月日
	private List<String> datesOp;
}
