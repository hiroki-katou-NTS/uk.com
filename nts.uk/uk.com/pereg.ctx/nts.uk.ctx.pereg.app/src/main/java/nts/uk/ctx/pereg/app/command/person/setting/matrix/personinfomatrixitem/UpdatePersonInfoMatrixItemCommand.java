/**
 * 
 */
package nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;

/**
 * @author hieult
 *
 */
@Value
public class UpdatePersonInfoMatrixItemCommand {
	/** 個人情報カテゴリID */
	private String pInfoCategoryID;
	
	/** 個人情報項目ID */
	private String pInfoItemDefiID;
	
	/** 列幅 **/
	private  int columnWidth ;
	
	/** 規定区分 **/
	private int regulationATR;
	
		public PersonInfoMatrixItem toDomain(){
			return PersonInfoMatrixItem.createFromJavatype(
					pInfoCategoryID,
					pInfoItemDefiID,
					columnWidth,
					regulationATR);
		}
}
