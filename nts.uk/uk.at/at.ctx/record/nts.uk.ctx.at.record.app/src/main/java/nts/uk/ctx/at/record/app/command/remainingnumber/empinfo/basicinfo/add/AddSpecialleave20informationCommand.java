package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo.add;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddSpecialleave20informationCommand {

	@PeregEmployeeId
	private String sID;
	
	//	特別休暇付与基準日
	@PeregItem("IS00622")
	private GeneralDate grantDate;
	
	//	特別休暇管理
	@PeregItem("IS00623")
	private BigDecimal useAtr;
	
	//	付与設定
	@PeregItem("IS00624")
	private BigDecimal appSet;
	
	//	付与日数
	@PeregItem("IS00625")
	private BigDecimal grantDays;
	
	//	付与テーブル
	@PeregItem("IS00626")
	private String grantTable;
	
	//	次回付与日
	@PeregItem("IS00627")
	private String nextGrantDate;

	//	特別休暇残数
	@PeregItem("IS00628")
	private String spHDRemain;

}
