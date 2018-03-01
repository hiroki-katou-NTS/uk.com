package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * output 5 - 申請一覧リスト取得実績
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class AppListAtrOutput {

	private List<ApplicationFullOutput> lstAppFull;
	private ApplicationStatus appStatus;
	private List<String> lstAppColor;
	private List<AppPrePostGroup> lstAppGroup;
}
