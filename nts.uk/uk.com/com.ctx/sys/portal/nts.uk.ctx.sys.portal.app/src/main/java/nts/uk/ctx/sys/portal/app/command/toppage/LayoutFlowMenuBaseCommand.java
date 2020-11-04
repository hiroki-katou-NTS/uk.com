package nts.uk.ctx.sys.portal.app.command.toppage;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.WidgetSetting;

@Getter
@Setter
public class LayoutFlowMenuBaseCommand {
	
	/** ウィジェット設定 */
	private List<WidgetSetting> widgetSettings;
	/** トップページコード */
	private String topPageCode;
	/** レイアウトNO */
	private BigDecimal layoutNo;
	/** レイアウト種類 */
	private Integer layoutType;
	/** 会社ID */
	private String cid;
	/** フローメニューコード */
	private String flowMenuCd;
	/** フローメニューコード（アップロード） */
	private String flowMenuUpCd;
	/** 外部URL */
	private String url;
	
	public LayoutNew toDomain(){
		return LayoutNew.createFromJavaType(widgetSettings, topPageCode, layoutNo, layoutType, cid, flowMenuCd, flowMenuUpCd, url);
	}

}
