package nts.uk.ctx.at.request.dom.setting.company.divergencereason;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * @author loivt
 * 乖離定型理由
 */
@Getter
@Setter
@AllArgsConstructor
public class DivergenceReason extends AggregateRoot{
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
	/**
	 * 定型理由項目
	 */
	private ReasonTypeItem reasonTypeItem;
	
	public static DivergenceReason createSimpleFromJavaType(String companyID,int appType,String reasonID,int dispOrder,String reasonTemp, int defaultFlg){
//		ReasonTypeItem reasonTypeItem = new ReasonTypeItem(reasonID, dispOrder, new ReasonTempPrimitive(reasonTemp), EnumAdaptor.valueOf(defaultFlg,DefaultFlg.class));
//		return new DivergenceReason(companyID, EnumAdaptor.valueOf(appType,ApplicationType.class), reasonTypeItem);
		return null;
		
	}

}
