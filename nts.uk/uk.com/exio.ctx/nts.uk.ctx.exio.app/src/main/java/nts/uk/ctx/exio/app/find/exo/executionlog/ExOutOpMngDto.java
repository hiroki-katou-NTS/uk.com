package nts.uk.ctx.exio.app.find.exo.executionlog;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;

/**
 * 外部出力動作管理
 */
@AllArgsConstructor
@Value
public class ExOutOpMngDto {

	/**
	* 
	*/
	private String exOutProId;

	/**
	* 
	*/
	private int proCnt;

	/**
	* 
	*/
	private int errCnt;

	/**
	* 
	*/
	private int totalProCnt;

	/**
	* 
	*/
	private int doNotInterrupt;

	/**
	* 
	*/
	private String proUnit;

	/**
	* 
	*/
	private int opCond;

	public static ExOutOpMngDto fromDomain(ExOutOpMng domain) {
		return new ExOutOpMngDto(domain.getExOutProId().v(), domain.getProCnt(), domain.getErrCnt(),
				domain.getTotalProCnt(), domain.getDoNotInterrupt().value, domain.getProUnit(), domain.getOpCond().value);
	}

}
