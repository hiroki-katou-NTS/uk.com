/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.mypage.setting;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.enums.PermissionDivision;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;

/**
 * The Class MyPageSetting.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class MyPageSetting extends AggregateRoot {

	/** The company id. */
	private String companyId;

	/** The use my page. */
	private UseDivision useMyPage;

	/** The use standard widget. */
	private UseDivision useStandarWidget;
	
	/** The use optional widget. */
	private UseDivision useOptionalWidget;

	/** The use dashboard. */
	private UseDivision useDashboard;

	/** The use flow menu. */
	private UseDivision useFlowMenu;

	/** The external url permission. */
	private PermissionDivision externalUrlPermission;

	/** The top page part use setting. */
	private List<TopPagePartUseSetting> topPagePartUseSetting;

	/**
	 * Instantiates a new my page setting.
	 *
	 * @param companyId the company id
	 * @param useMyPage the use my page
	 * @param useStandardWidget
	 * @param useOptionalWidget
	 * @param useDashboard the use dashboard
	 * @param useFlowMenu the use flow menu
	 * @param externalUrlPermission the external url permission
	 * @param topPagePartUseSetting the top page part use setting
	 */
	public MyPageSetting(String companyId, UseDivision useMyPage, UseDivision useStandarWidget,
			UseDivision useOptionalWidget, UseDivision useDashboard, UseDivision useFlowMenu,
			PermissionDivision externalUrlPermission, List<TopPagePartUseSetting> topPagePartUseSetting) {
		super();
		this.companyId = companyId;
		this.useMyPage = useMyPage;
		this.useStandarWidget = useStandarWidget;
		this.useOptionalWidget = useOptionalWidget;
		this.useDashboard = useDashboard;
		this.useFlowMenu = useFlowMenu;
		this.externalUrlPermission = externalUrlPermission;
		this.topPagePartUseSetting = topPagePartUseSetting;
	}

	/**
	 * Creates the from java type.
	 *
	 * @param CompanyId the company id
	 * @param useMyPage the use my page
	 * @param useDashboard the use dashboard
	 * @param useFlowMenu the use flow menu
	 * @param externalUrlPermission the external url permission
	 * @param topPagePartUseSetting the top page part use setting
	 * @return the my page setting
	 */
	public static MyPageSetting createFromJavaType(String CompanyId, Integer useMyPage, Integer useStandardWidget, Integer useOptionalWidget,
			Integer useDashboard, Integer useFlowMenu, Integer externalUrlPermission,List<TopPagePartUseSetting> topPagePartUseSetting) {
		return new MyPageSetting(CompanyId, UseDivision.valueOf(useMyPage),
				UseDivision.valueOf(useStandardWidget), UseDivision.valueOf(useOptionalWidget), UseDivision.valueOf(useDashboard), UseDivision.valueOf(useFlowMenu),
				PermissionDivision.valueOf(externalUrlPermission), topPagePartUseSetting);
	}

	
	
	
}
