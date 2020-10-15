package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamBeforeRegister {
//	・会社ID
	private String companyId;
//	・代行申請区分
	private boolean agentAtr;
//	・申請
	private ApplicationDto applicationDto;
//	・直行直帰申請
	private GoBackDirectlyDto goBackDirectlyDto;
//	・直行直帰申請起動時の表示情報
	private InforGoBackCommonDirectDto inforGoBackCommonDirectDto;
//	・モード＝　新規
	private boolean mode;
}
