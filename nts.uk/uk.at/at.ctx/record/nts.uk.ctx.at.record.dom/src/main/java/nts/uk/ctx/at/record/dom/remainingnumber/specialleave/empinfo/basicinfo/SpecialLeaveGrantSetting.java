package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class SpecialLeaveGrantSetting {
	
	// 付与基準日
	private GeneralDate grantStandardDate;
	
	// 付与日数
	private Optional<GrantNumber> grantNumber;
	
	// 付与テーブル
	private Optional<GrantTable> grantTable;
	
}
