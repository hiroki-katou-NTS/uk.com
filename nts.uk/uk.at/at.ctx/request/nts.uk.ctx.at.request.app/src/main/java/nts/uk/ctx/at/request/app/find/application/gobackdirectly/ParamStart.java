package nts.uk.ctx.at.request.app.find.application.gobackdirectly;


import java.util.List;

import lombok.Data;


//パラメータ.申請者社員ID
//パラメータ.申請者List
@Data
public class ParamStart {
	//パラメータ.申請者社員ID
	private String ApplicantEmployeeID;
	//パラメータ.申請者List
	private List<String> ApplicantList;
}
