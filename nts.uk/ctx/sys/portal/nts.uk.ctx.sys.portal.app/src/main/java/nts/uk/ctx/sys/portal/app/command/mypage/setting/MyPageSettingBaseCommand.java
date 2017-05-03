package nts.uk.ctx.sys.portal.app.command.mypage.setting;

import java.util.Set;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.portal.app.find.mypage.setting.TopPagePartUseSettingDto;
import nts.uk.ctx.sys.portal.dom.enums.PermissionDivision;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;

/**
 * The Class MyPageSettingBaseCommand.
 */
public class MyPageSettingBaseCommand {
	/** The My page setting code. */
	public String companyId;

	/** The use my page. */
	public Integer useMyPage;

	/** The use widget. */
	public Integer useWidget;

	/** The use dashboard. */
	public Integer useDashboard;

	/** The use flow menu. */
	public Integer useFlowMenu;

	/** The external url permission. */
	public Integer externalUrlPermission;

	/** The top page part use setting dto. */
	public Set<TopPagePartUseSettingDto> topPagePartUseSettingDto;

	/**
	 * To domain.
	 *
	 * @return the my page setting
	 */
	public MyPageSetting toDomain() {
		return new MyPageSetting(companyId, UseDivision.valueOf(useMyPage),
				UseDivision.valueOf(useWidget), UseDivision.valueOf(useDashboard), UseDivision.valueOf(useFlowMenu),
				PermissionDivision.valueOf(externalUrlPermission), this.topPagePartUseSettingDto.stream().map(item -> {
					return TopPagePartUseSetting.createFromJavaType(item.companyId, item.partItemCode,
							item.partItemName, item.useDivision, item.partType.value);
				}).collect(Collectors.toList()));
	}
}
