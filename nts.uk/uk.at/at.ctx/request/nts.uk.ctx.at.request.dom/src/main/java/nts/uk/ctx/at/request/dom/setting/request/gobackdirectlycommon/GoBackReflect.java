package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.AggregateRoot;

@EqualsAndHashCode(callSuper=false)
@Data
//直行直帰申請の反映
public class GoBackReflect extends AggregateRoot{
//	会社ID
	private String companyId;
//	勤務情報を反映する
	private ApplicationStatus reflectApplication;
	
	public String getContent() {
		return null;
	}
}
