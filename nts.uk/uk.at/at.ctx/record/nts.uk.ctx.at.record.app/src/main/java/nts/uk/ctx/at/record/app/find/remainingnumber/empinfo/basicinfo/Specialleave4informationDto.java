package nts.uk.ctx.at.record.app.find.remainingnumber.empinfo.basicinfo;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
public class Specialleave4informationDto   extends PeregDomainDto {

	@PeregEmployeeId
	private String sID;
	
	//	特別休暇付与基準日
	@PeregItem("IS00316")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00317")
	private Integer useAtr;
	
	//	付与設定
	@PeregItem("IS00318")
	private Integer appSet;
	
	//	付与日数
	@PeregItem("IS00319")
	private Integer grantDays;
	
	//	付与テーブル
	@PeregItem("IS00320")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00321")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00322")
	private String spHDRemain;
	public static Specialleave4informationDto createFromDomain(SpecialLeaveBasicInfo domain){
		Specialleave4informationDto dto = new Specialleave4informationDto();
		dto.grantDate = domain.getGrantSetting().getGrantDate();
		dto.useAtr = domain.getUsed().value;
		dto.appSet = domain.getApplicationSet().value;
		if (domain.getGrantSetting().getGrantDays().isPresent()){
			dto.grantDays = domain.getGrantSetting().getGrantDays().get().v();
		}
		if (domain.getGrantSetting().getGrantTable().isPresent()){
			dto.grantTable = domain.getGrantSetting().getGrantTable().get().v();
		}
		dto.setRecordId(domain.getSID());
		return dto;
	}
}
