package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OvertimeHours {
		/** エラーメッセージ */
		Optional<String> errorMessage;
		/** 時間外労働情報 */
		Optional<List<AgreementTimeList36>> overtimeLaborInfor;
		public OvertimeHours(Optional<String> errorMessage, Optional<List<AgreementTimeList36>> overtimeLaborInfor) {
			super();
			this.errorMessage = errorMessage;
			this.overtimeLaborInfor = overtimeLaborInfor;
		}
		
}
