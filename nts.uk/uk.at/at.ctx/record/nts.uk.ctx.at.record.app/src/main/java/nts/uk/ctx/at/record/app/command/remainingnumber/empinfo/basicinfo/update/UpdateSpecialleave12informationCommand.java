package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.update;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class UpdateSpecialleave12informationCommand {
	@PeregEmployeeId
	private String sID;
	//	特別休暇付与基準日
	@PeregItem("IS00566")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00567")
	private BigDecimal useAtr;
	
	//	付与設定
	@PeregItem("IS00568")
	private BigDecimal appSet;
	
	//	付与日数
	@PeregItem("IS00569")
	private BigDecimal grantDays;
	
	//	付与テーブル
	@PeregItem("IS00570")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00571")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00572")
	private String spHDRemain;

}
