package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.ArrivedLateLeaveEralySetting;
@Data
@AllArgsConstructor
@NoArgsConstructor
//遅刻早退取消申請設定
public class ArrivedLateLeaveEralySettingDto {
//	【削除】日別実績のアラームを消す
	private Boolean isResult;
//	会社ID
	private String id;
//	取り消す設定
	private int cancelCategory;
	
	public static ArrivedLateLeaveEralySettingDto convertDto(ArrivedLateLeaveEralySetting value) {
		return new ArrivedLateLeaveEralySettingDto(
				value.getIsResult(),
				value.getId(),
				value.getCancelCategory().value);
	}
}
