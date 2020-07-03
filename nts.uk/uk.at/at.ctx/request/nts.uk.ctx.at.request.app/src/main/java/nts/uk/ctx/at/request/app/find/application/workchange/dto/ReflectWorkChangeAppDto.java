package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReflectWorkChangeAppDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 出退勤を反映するか
	 */
	private int whetherReflectAttendance;
	
	
	public static ReflectWorkChangeAppDto fromDomain(ReflectWorkChangeApp param) {
		return new ReflectWorkChangeAppDto(
				param.getCompanyID(),
				param.getWhetherReflectAttendance().value);
	}
}
