package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import lombok.Getter;
@Getter
public class ParamEmpRsvLeave {
	/** 基準日：年月日 */
	private String inputDate;
	/** 選択済項目 */
	private List<String> listSID;
}
