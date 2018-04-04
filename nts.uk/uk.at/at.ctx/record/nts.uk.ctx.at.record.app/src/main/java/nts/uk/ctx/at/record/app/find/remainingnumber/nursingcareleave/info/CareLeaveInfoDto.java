package nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@AllArgsConstructor
public class CareLeaveInfoDto extends PeregDomainDto{
	
	//社員ID
	@PeregEmployeeId
	private String sId;
	
	//子の看護休暇管理
	@PeregItem("IS00375")
	private boolean childCareUseArt;
	
	@PeregItem("IS00376")
	//子の看護上限設定
	private int childCareUpLimSet;	
	
	@PeregItem("IS00377")
	//本年度の子の看護上限日数
	private Integer childCareThisFiscal;
	
	@PeregItem("IS00378")
	//次年度の子の看護上限日数
	private Integer childCareNextFiscal;
	
	//子の看護休暇管理
	@PeregItem("IS00379")
	private int childCareUsedDays;
	
	//介護休暇管理
	@PeregItem("IS00380")
	private boolean careUseArt;
	
	@PeregItem("IS00381")
	//介護上限設定
	private int careUpLimSet;	
	
	@PeregItem("IS00382")
	//本年度の介護上限日数
	private Integer careThisFiscal;
	
	@PeregItem("IS00383")
	//次年度の介護上限日数
	private Integer careNextFiscal;
	
	//介護使用日数
	@PeregItem("IS00384")
	private int careUsedDays;
	
	
}
