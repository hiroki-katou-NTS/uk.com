package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ListApplicantOutput {
	/**自分の申請*/
	private boolean mySelf;
	/**社員ID（リスト）*/
	private List<String> lstSID;
}
