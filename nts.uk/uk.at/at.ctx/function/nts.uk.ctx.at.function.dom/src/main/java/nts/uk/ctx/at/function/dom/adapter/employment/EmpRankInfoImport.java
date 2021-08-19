package nts.uk.ctx.at.function.dom.adapter.employment;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 社員ランク情報Imported
 * @author rafiqul.islam
 *
 */
@Getter
@AllArgsConstructor
public class EmpRankInfoImport {
	
	 /**社員ID **/
	private  String empId;
	/** ランクコード **/
	private  String rankCode;
	
	/**ランク記号 **/
	private  String rankSymbol;

}
