package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialleave19informationCommand {

	@PeregEmployeeId
	private String sID;
	
	//	特別休暇付与基準日
	@PeregItem("IS00615")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00616")
	private BigDecimal useAtr;
	
	//	付与設定
	@PeregItem("IS00617")
	private BigDecimal appSet;
	
	//	付与日数
	@PeregItem("IS00618")
	private BigDecimal grantDays;
	
	//	付与テーブル
	@PeregItem("IS00619")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00620")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00621")
	private String spHDRemain;

}
