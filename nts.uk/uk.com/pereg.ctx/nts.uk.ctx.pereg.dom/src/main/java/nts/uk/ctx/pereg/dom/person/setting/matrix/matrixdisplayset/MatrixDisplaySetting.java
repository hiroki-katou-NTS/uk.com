/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author hieult
 *
 */
/** マトリクス表示設定 **/
@Getter
@Setter
@AllArgsConstructor
public class MatrixDisplaySetting {
	/** 会社ID **/
	private String companyID;

	/** ユーザID **/
	private String userID;

	/** カーソルの移動方向 **/
	private CursorDirection cursorDirection;

	/** 分類 **/
	private NotUseAtr clsATR;

	/** 職位 **/
	private NotUseAtr jobATR;

	/** 職場 **/
	private NotUseAtr workPlaceATR;

	/** 部門 **/
	private NotUseAtr departmentATR;

	/** 雇用 **/
	private NotUseAtr employmentATR;

	public static MatrixDisplaySetting createFromJavaType(String companyID, String userID, int cursorDirection,
			int clsATR, int positionATR, int workPlaceATR, int departmentATR, int employmentATR) {
		return new MatrixDisplaySetting(companyID, userID, EnumAdaptor.valueOf(cursorDirection, CursorDirection.class),
				EnumAdaptor.valueOf(clsATR, NotUseAtr.class), EnumAdaptor.valueOf(positionATR, NotUseAtr.class),
				EnumAdaptor.valueOf(workPlaceATR, NotUseAtr.class), EnumAdaptor.valueOf(departmentATR, NotUseAtr.class),
				EnumAdaptor.valueOf(employmentATR, NotUseAtr.class));
	}
}
