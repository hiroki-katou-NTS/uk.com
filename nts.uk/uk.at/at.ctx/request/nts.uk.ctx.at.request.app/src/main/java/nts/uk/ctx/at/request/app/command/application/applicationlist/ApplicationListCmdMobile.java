package nts.uk.ctx.at.request.app.command.application.applicationlist;

import lombok.Data;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Data
public class ApplicationListCmdMobile {
//	private Integer isDisPreP;
//	private String startDate;
//	private String endDate;
//	private List<AppMasterInfo> lstMasterInfo;
//	private List<ApplicationDataOutput> lstApp;
//	//TH: approval (count)
//	private	ApplicationStatus appStatusCount;
//	//phuc vu cho viec loc theo aptype, dem lai trang thai don
//	private List<ApproveAgent> lstAgent;
//	private List<AppInfor> lstAppInfor;
//	//contentApp
//	private List<ContentApp> lstContentApp;
//	private List<AppAbsRecSyncData> lstSyncData;
//	private List<String> lstSCD;
	private Integer appAllNumber;
	private Integer appPerNumber;
	/**
	 * 申請一覧抽出条件
	 */
	private AppListExtractConditionCmd appListExtractCondition;
	/**
	 * 申請一覧情報
	 */
	private AppListInfoCmd appListInfo;
}
