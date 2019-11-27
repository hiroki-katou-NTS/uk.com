/**
 * 
 */
package nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItem;

/**
 * @author hieult
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonInfoMatrixItemCommand {
	/** 個人情報カテゴリID */
	public String pInfoCategoryID;

	/** 個人情報項目ID */
	public String pInfoItemDefiID;

	/** 列幅 **/
	public int columnWidth;

	/** 規定区分 **/
	public int regulationATR;

	public PersonInfoMatrixItem toDomain() {
		return PersonInfoMatrixItem.createFromJavatype(pInfoCategoryID, pInfoItemDefiID, columnWidth, regulationATR);
	}
}
