package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
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
@Getter
public class StandardWidget extends TopPagePart {
	
	// 勤務状況の詳細設定
	private List<DetailedWorkStatusSetting> detailedWorkStatusSettingList;

	// 承認すべき申請状況の詳細設定
	private List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList;

	// 標準ウィジェット種別
	private StandardWidgetType standardWidgetType;

	// 申請状況の詳細設定
	private List<ApplicationStatusDetailedSetting> appStatusDetailedSettingList;
	
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

	public StandardWidget(String companyID, String toppagePartID, TopPagePartCode code, TopPagePartName name,
			TopPagePartType type, Size size, List<DetailedWorkStatusSetting> detailedWorkStatusSettingList,
			List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList,
			StandardWidgetType standardWidgetType,
			List<ApplicationStatusDetailedSetting> appStatusDetailedSettingList) {
		
		super(companyID, toppagePartID, code, name, type, size);
		
		this.detailedWorkStatusSettingList = new ArrayList<>();
		this.approvedAppStatusDetailedSettingList = new ArrayList<>();
		this.appStatusDetailedSettingList = new ArrayList<>();
		
		this.standardWidgetType = standardWidgetType;
		if(standardWidgetType == StandardWidgetType.APPROVE_STATUS) {
			this.detailedWorkStatusSettingList = detailedWorkStatusSettingList;
		}else if(standardWidgetType == StandardWidgetType.APPLICATION_STATUS) {
			this.approvedAppStatusDetailedSettingList = approvedAppStatusDetailedSettingList;
		}else if(standardWidgetType == StandardWidgetType.WORK_STATUS) {
			this.appStatusDetailedSettingList = appStatusDetailedSettingList;
		}
		
	}

}
