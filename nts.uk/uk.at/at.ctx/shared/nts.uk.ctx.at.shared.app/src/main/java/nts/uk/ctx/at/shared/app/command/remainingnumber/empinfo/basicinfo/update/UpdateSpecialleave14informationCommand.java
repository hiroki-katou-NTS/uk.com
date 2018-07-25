package nts.uk.ctx.at.shared.app.command.remainingnumber.empinfo.basicinfo.update;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class UpdateSpecialleave14informationCommand {
	@PeregEmployeeId
	private String sID;
	//	特別休暇付与基準日
	@PeregItem("IS00580")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00581")
	private BigDecimal useAtr;
	
	//	付与設定
	@PeregItem("IS00582")
	private BigDecimal appSet;
	
	//	付与日数
	@PeregItem("IS00583")
	private BigDecimal grantDays;
	
	//	付与テーブル
	@PeregItem("IS00584")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00585")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00586")
	private String spHDRemain;

}
