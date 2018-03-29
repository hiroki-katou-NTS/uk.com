package nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CareLeaveInfoDto {
	
	//社員ID
	private String sId;
	
	//使用区分
	private boolean useClassification;
	
	//上限設定
	private int upperlimitSetting;	
	
	//本年度上限日数
	private Integer maxDayForThisFiscalYear;
	
	//次年度上限日数
	private Integer maxDayForNextFiscalYear;
}
