/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix;


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
	
	/** カーソルの移動方向  **/
	private CursorDirection cursorDirection;
	
	/** 分類  **/
	private NotUseAtr classification;
	
	/** 職位  **/
	private NotUseAtr position;
	
	/** 職場  **/
	private NotUseAtr workPlace;
	
	/** 部門  **/
	private NotUseAtr department;
	
	/** 雇用  **/
	private NotUseAtr employment;
	
			public static MatrixDisplaySetting createFromJavaType ( String companyID , String userID , int cursorDirection , int classification , int position , int workPlace , int department , int employment ){
				return new MatrixDisplaySetting(
						companyID,
						userID,
						EnumAdaptor.valueOf(cursorDirection, CursorDirection.class),
						EnumAdaptor.valueOf(classification , NotUseAtr.class),
						EnumAdaptor.valueOf(position , NotUseAtr.class),
						EnumAdaptor.valueOf(workPlace , NotUseAtr.class),
						EnumAdaptor.valueOf(department , NotUseAtr.class),
						EnumAdaptor.valueOf(employment , NotUseAtr.class));
			}
}
