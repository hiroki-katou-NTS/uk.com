package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.標準ウィジェット.標準ウィジェット
 *
 */
public class StandardWidget extends TopPagePart {
	
	// 勤務状況の詳細設定
	List<DetailedWorkStatusSetting> detailedWorkStatusSettingList;

	@Setter
	@Getter
	// 承認すべき申請状況の詳細設定
	List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList;

	@Setter
	// 標準ウィジェット種別
	StandardWidgetType standardWidgetType;

	@Getter
	@Setter
	// 申請状況の詳細設定
	List<ApplicationStatusDetailedSetting> appStatusDetailedSettingList;
	
	public StandardWidget(String companyID, String toppagePartID, TopPagePartCode code, TopPagePartName name, TopPagePartType type, Size size) {
		super(companyID, toppagePartID, code, name, type, size);
		// TODO Auto-generated constructor stub
	}

	public static StandardWidget createFromJavaType(String companyID, String toppagePartID, String code, String name, int type, int width, int height) {
       return new StandardWidget (
    		   companyID,
    		   toppagePartID,
    		   new TopPagePartCode(code),
    		   new TopPagePartName(name),
    		   EnumAdaptor.valueOf(type, TopPagePartType.class), 
    		   Size.createFromJavaType(width, height));
	}

}
