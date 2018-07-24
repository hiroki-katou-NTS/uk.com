package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import lombok.Getter;
@Getter
public class ParamEmpRsvLeave {
	/**
	 * 選択モード：単一選択　or　複数選択    
	 */
	private boolean mode;
	/** 基準日：年月日 */
	private String inputDate;
	/** 選択済項目 */
	private List<String> listSID;
}
