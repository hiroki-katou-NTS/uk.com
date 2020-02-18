package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;

import lombok.Data;
@Data
public class ParamGetOvertime {
	private String url;
	// 4申請日
	private String appDate;
	// 1残業区分
	private int uiType;
	// 5開始時刻
	private Integer timeStart1;
	// 6終了時刻
	private Integer timeEnd1;
	// 7申請理由
	private String reasonContent;
	// 3申請者List
	private List<String> employeeIDs;
	private String employeeID;
}
