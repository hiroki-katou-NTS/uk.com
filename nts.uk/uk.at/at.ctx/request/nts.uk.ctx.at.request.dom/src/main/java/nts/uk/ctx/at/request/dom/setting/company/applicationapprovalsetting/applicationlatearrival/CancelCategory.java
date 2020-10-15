package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//取り消す区分
public enum CancelCategory {
	
	NOT_USE(0,"取り消す使用しない"),
	
	USE_NOT_CHECK(1,"取り消す使用するチェックなし"),
	
	USE(2,"取り消す使用するチェックあり");
	
	public final int value;
	
	public final String name;
}
