package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamStartMobile {
//	・会社ID
	String companyId;
//	・社員ID
	String employeeId;
//	・申請対象日リスト
	List<String> dates;
//	・モード
	Boolean mode;
//	・直行直帰申請起動の表示情報
	InforGoBackCommonDirectDto inforGoBackCommonDirectDto;
	
	AppDispInfoStartupDto appDispInfoStartupDto;
}	
