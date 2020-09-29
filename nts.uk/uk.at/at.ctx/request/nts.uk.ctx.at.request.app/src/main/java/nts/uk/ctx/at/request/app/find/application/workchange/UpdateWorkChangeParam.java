package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeDispInfoCmd;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class UpdateWorkChangeParam {
	// 会社ID
	private String companyId;

	// 申請対象日リスト
	private List<String> listDates;
	
	// 勤務変更申請の表示情報
	private AppWorkChangeDispInfoCmd appWorkChangeDispInfo;

}
