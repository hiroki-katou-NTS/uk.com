package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AppMasterInfo {

	// 申請ID
	private String appID;
	private int appType;
	private String dispName;
	private String empName;
	private String inpEmpName;
	private String workplaceName;
	@Setter
	private boolean statusFrameAtr;
	@Setter
	private String phaseStatus;
	//事前、事後の後ろに#CMM045_101(※)を追加
	private boolean checkAddNote;
	@Setter
	private int checkTimecolor;
	//ver14 + EA1360
	//申請詳細設定.時刻計算利用区分
	private Integer detailSet;
	//EA2236
	private String empSD;
}
