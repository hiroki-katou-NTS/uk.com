package nts.uk.ctx.at.shared.app.find.remainingnumber.empinfo.basicinfo;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
public class Specialleave13informationDto  extends PeregDomainDto {

	@PeregEmployeeId
	private String sID;
	
	//	特別休暇付与基準日
	@PeregItem("IS00573")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00574")
	private Integer useAtr;
	
	//	付与設定
	@PeregItem("IS00575")
	private Integer appSet;
	
	//	付与日数
	@PeregItem("IS00576")
	private Integer grantDays;
	
	//	付与テーブル
	@PeregItem("IS00577")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00578")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00579")
	private String spHDRemain;
	public static Specialleave13informationDto createFromDomain(SpecialLeaveBasicInfo domain){
		Specialleave13informationDto dto = new Specialleave13informationDto();
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
