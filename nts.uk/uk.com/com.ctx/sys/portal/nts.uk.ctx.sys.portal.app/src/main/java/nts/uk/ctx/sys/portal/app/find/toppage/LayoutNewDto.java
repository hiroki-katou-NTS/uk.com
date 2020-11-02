package nts.uk.ctx.sys.portal.app.find.toppage;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.portal.dom.layout.WidgetSetting;

@Data
@Builder
public class LayoutNewDto {
	
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

}
