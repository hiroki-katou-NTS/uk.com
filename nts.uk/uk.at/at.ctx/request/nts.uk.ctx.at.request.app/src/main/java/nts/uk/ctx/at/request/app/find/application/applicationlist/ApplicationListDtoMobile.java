package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationListDtoMobile {
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
	private AppListExtractConditionDto appListExtractConditionDto;
	/**
	 * 申請一覧情報
	 */
	private AppListInfoDto appListInfoDto;
}
