package nts.uk.ctx.sys.portal.app.command.toppage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.portal.app.find.toppage.WidgetSettingDto;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.WidgetSetting;
import nts.uk.ctx.sys.portal.dom.layout.WidgetType;

@Getter
@Setter
public class SaveLayoutFlowMenuCommand implements LayoutNew.MementoSetter, LayoutNew.MementoGetter {
	/** ウィジェット設定 */
	private List<WidgetSettingDto> widgetSettings;
	/** トップページコード */
	private String topPageCode;
	/** レイアウトNO */
	private int layoutNo;
	/** レイアウト種類 */
	private int layoutType;
	/** 会社ID */
	private String cid;
	/** フローメニューコード */
	private String flowMenuCd;
	/** フローメニューコード（アップロード） */
	private String flowMenuUpCd;
	/** 外部URL */
	private String url;
	
	@Override
	public List<WidgetSetting> getWidgetSettings() {
		if (this.widgetSettings == null) {
			return new ArrayList<WidgetSetting>();
		}
		return this.widgetSettings.stream()
				.map(x -> new WidgetSetting(WidgetType.valueOf(x.getWidgetType()), x.getOrder())).collect(Collectors.toList());
	}

	@Override
	public void setWidgetSettings(List<WidgetSetting> widgetSettings) {
		if (widgetSettings == null) {
			return;
		}
		this.widgetSettings = widgetSettings.stream().map(x -> WidgetSettingDto.builder()
				.widgetType(x.getWidgetType().value)
				.order(x.getOrder())
				.build())
				.collect(Collectors.toList());
	}

	@Override
	public String getTopPageCode() {
		return this.topPageCode;
	}

	@Override
	public void setTopPageCode(String toppageCode) {
		this.topPageCode = toppageCode;
	}

	@Override
	public BigDecimal getLayoutNo() {
		return BigDecimal.valueOf(this.layoutNo);
	}

	@Override
	public void setLayoutNo(BigDecimal layoutNo) {
		this.layoutNo = layoutNo.intValue();
	}

	@Override
	public BigDecimal getLayoutType() {
		return BigDecimal.valueOf(this.layoutType);
	}
	
	@Override
	public void setLayoutType(BigDecimal layoutType) {
		this.layoutType = layoutType.intValue();
	}

	@Override
	public String getCid() {
		return this.cid;
	}

	@Override
	public void setCid(String cid) {
		this.cid = cid;
	}

	@Override
	public String getFlowMenuCd() {
		return this.flowMenuCd;
	}
	
	@Override
	public void setFlowMenuCd(String flowMenuCd) {
		this.flowMenuCd = flowMenuCd;
	}

	@Override
	public String getFlowMenuUpCd() {
		return this.flowMenuUpCd;
	}

	@Override
	public void setFlowMenuUpCd(String flowMenuUpCd) {
		this.flowMenuUpCd = flowMenuUpCd;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}
}
