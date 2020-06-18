package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon;

import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
@Data
//直行直帰申請共通設定
public class GoBackReflect extends AggregateRoot{
//	会社ID
	private String companyId;
//	勤務情報を反映する
	private ApplicationStatus reflectApplication;
}
