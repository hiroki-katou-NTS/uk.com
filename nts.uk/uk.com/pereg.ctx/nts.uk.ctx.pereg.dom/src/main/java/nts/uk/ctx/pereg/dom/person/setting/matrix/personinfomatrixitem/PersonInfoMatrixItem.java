/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;

/**
 * @author hieult
 *
 */
/** 個人情報マトリクス項目 */
@Getter
@Setter
@AllArgsConstructor
public class PersonInfoMatrixItem {

	/** 個人情報カテゴリID */
	private String pInfoCategoryID;

	/** 個人情報項目ID */
	private String pInfoItemDefiID;

	/** 列幅 **/
	private int columnWidth = 100;

	/** 規定区分 **/
	private RegulationATR regulationATR;

	public static PersonInfoMatrixItem createFromJavatype(String pInfoCategoryID, String pInfoItemDefiID,
			int columnWidth, int regulationATR) {
		if (columnWidth >= 20 && columnWidth <= 9999) {
			return new PersonInfoMatrixItem(pInfoCategoryID, pInfoItemDefiID, columnWidth,
					EnumAdaptor.valueOf(regulationATR, RegulationATR.class));
		} else
			throw new BusinessException("Msg_1467");
	}
}
