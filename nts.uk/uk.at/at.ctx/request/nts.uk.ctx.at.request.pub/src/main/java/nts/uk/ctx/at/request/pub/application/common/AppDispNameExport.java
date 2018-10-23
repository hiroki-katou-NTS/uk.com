package nts.uk.ctx.at.request.pub.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppDispNameExport {
	private String companyID;   
	private Integer appType;
	private String dispName;
}
