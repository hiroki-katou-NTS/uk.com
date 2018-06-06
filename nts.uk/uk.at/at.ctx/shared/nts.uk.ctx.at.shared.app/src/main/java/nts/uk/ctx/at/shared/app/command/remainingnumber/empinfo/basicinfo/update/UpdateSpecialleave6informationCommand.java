package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class UpdateSpecialleave6informationCommand {
	@PeregEmployeeId
	private String sID;
	//	特別休暇付与基準日
	@PeregItem("IS00330")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00331")
	private BigDecimal useAtr;
	
	//	付与設定
	@PeregItem("IS00332")
	private BigDecimal appSet;
	
	//	付与日数
	@PeregItem("IS00333")
	private BigDecimal grantDays;
	
	//	付与テーブル
	@PeregItem("IS00334")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00335")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00336")
	private String spHDRemain;

}
