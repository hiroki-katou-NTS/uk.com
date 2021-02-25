package nts.uk.ctx.at.request.app.find.application.workchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	public ReflectWorkChangeApp toDomain() {
		ReflectWorkChangeApp app = new ReflectWorkChangeApp();
		app.setCompanyID(companyID);
		app.setWhetherReflectAttendance(EnumAdaptor.valueOf(whetherReflectAttendance, NotUseAtr.class));
		return app;
	}
}
