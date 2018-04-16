package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class SpecialleaveinformationCommand {

	@PeregEmployeeId
	private String sID;
	
	//	特別休暇付与基準日
	@PeregItem("IS00295")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00296")
	private int useAtr;
	
	//	付与設定
	@PeregItem("IS00297")
	private int appSet;
	
	//	付与日数
	@PeregItem("IS00298")
	private Integer grantDays;
	
	//	付与テーブル
	@PeregItem("IS00299")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00300")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00301")
	private String spHDRemain;

}
