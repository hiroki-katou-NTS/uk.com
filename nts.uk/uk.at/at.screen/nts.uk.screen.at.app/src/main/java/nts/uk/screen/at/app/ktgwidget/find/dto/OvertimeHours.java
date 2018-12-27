package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OvertimeHours {
	
		/** エラーメッセージ */
		String errorMessage;
		/** 時間外労働情報 */
		List<AgreementTimeList36> overtimeLaborInfor;
		
		
		public OvertimeHours(String errorMessage, List<AgreementTimeList36> overtimeLaborInfor) {
			super();
			this.errorMessage = errorMessage;
			this.overtimeLaborInfor = overtimeLaborInfor;
		}
		
		
}
