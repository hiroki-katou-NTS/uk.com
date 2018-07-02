package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;

import lombok.Data;
@Data
public class ParamGetOvertime {
	private String url;
	private String appDate;
	private int uiType;
	private Integer timeStart1;
	private Integer timeEnd1;
	private String reasonContent;
	private List<String> employeeIDs;
	private String employeeID;
}
