package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ParamChangeDate {
//	会社ID
	private String companyId;
//	申請対象日リスト
	private List<String> appDates;
//	申請者リスト
	private List<String> employeeIds;
//	直行直帰申請起動時の表示情報
	private InforGoBackCommonDirectDto inforGoBackCommonDirectDto;
}
	