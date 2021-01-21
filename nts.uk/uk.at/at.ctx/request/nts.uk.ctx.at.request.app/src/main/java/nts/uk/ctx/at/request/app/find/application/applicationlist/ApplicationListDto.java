package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.applist.service.AppMasterInfo;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationStatus;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ContentApp;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationListDto {
	private Integer isDisPreP;
	private String startDate;
	private String endDate;
	private List<AppMasterInfo> lstMasterInfo;
//	申請一覧
	private List<ApplicationDataOutput> lstApp;
	//TH: approval (count)
	private	ApplicationStatus appStatusCount;
	//phuc vu cho viec loc theo aptype, dem lai trang thai don
	private List<ApproveAgent> lstAgent;
	private List<AppInfor> lstAppInfor;
	//contentApp
	private List<ContentApp> lstContentApp;
	private List<AppAbsRecSyncData> lstSyncData;
	private List<String> lstSCD;
	private Integer appAllNumber;
	private Integer appPerNumber;
}
