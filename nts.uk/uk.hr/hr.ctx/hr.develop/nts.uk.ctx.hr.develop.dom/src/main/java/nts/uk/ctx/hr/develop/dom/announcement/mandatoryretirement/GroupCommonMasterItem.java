package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_20;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_Half_Width_2;

/**
 * @author thanhpv
 * グループ会社共通マスタ項目
 */
@AllArgsConstructor
@Getter
public class GroupCommonMasterItem extends DomainObject{

	/** 共通項目ID  */
	private String commonMasterItemId;
	
	/** 共通項目コード */
	private String_Any_Half_Width_2 commonMasterItemCode;
	
	/** 共通項目名 */
	private String_Any_20 commonMasterItemName;
	
	/** 表示順 */
	private int displayNumber;
	
	/** 使用開始日 */
	private GeneralDate usageStartDate;
	
	/** 使用終了日 */
	private GeneralDate usageEndDate;
	
	/** 会社別使用状態 */
	private List<NotUseCompanyList> notUseCompanyList;
	
	public static GroupCommonMasterItem createFromJavaType(String commonMasterItemId, String commonMasterItemCode, String commonMasterItemName, int displayNumber, GeneralDate usageStartDate, GeneralDate usageEndDate, List<NotUseCompanyList> notUseCompanyList) {
		return new GroupCommonMasterItem(
				commonMasterItemId,
				new String_Any_Half_Width_2(commonMasterItemCode),
				new String_Any_20(commonMasterItemName),
				displayNumber,
				usageStartDate,
				usageEndDate,
				notUseCompanyList
				);
	}
	
}
