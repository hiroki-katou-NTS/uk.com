/**
 * 
 */
package nts.uk.ctx.pereg.app.command.person.setting.matrix.matrixdisplayset;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;

/**
 * @author hieult
 *
 */
@Value
public class UpdateMatrixDisplaySetCommand {
	/** 会社ID **/
	private String companyID;

	/** ユーザID **/
	private String userID;
	
	/** カーソルの移動方向  **/
	private int cursorDirection;
	
	/** 分類  **/
	private int clsATR;
	
	/** 職位  **/
	private int positionATR;
	
	/** 職場  **/
	private int workPlaceATR;
	
	/** 部門  **/
	private int departmentATR;
	
	/** 雇用  **/
	private int employmentATR;
		
		public MatrixDisplaySetting toDomain(){
			return MatrixDisplaySetting.createFromJavaType(companyID,
					userID,
					cursorDirection,
					clsATR,
					positionATR,
					workPlaceATR,
					departmentATR,
					employmentATR);
		}
}
