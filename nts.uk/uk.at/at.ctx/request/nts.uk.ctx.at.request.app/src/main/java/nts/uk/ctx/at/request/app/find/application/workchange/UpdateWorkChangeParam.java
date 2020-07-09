package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateWorkChangeParam {
	// 会社ID
	private String companyId;

	// 申請対象日リスト
	private List<String> listDates;
	
	// 勤務変更申請の表示情報
	private AppWorkChangeDispInfoDto appWorkChangeDispInfo;

}
