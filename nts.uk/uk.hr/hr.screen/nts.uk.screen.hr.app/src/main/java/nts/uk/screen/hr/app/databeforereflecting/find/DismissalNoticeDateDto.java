package nts.uk.screen.hr.app.databeforereflecting.find;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class DismissalNoticeDateDto {
	// 社員ID
	private String sID;
	// 解雇予告日
	private GeneralDate dismissalNoticeDate;
}
