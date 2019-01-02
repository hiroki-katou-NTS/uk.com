/**
 * 
 */
package nts.uk.ctx.pereg.app.command.person.setting.matrix.matrixdisplayset;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem.CreatePersonInfoMatrixItemCommand;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatrixDisplaySetCommand {
	/** カーソルの移動方向 **/
	private int cursorDirection;

	/** 分類 **/
	private int clsATR;

	/** 職位 **/
	private int positionATR;

	/** 職場 **/
	private int workPlaceATR;

	/** 部門 **/
	private int departmentATR;

	/** 雇用 **/
	private int employmentATR;

	public MatrixDisplaySetting toDomain() {
		String userID = AppContexts.user().userId();
		String companyID = AppContexts.user().companyId();
				
		return MatrixDisplaySetting.createFromJavaType(companyID, userID, cursorDirection, clsATR, positionATR,
				workPlaceATR, departmentATR, employmentATR);
	}
}
