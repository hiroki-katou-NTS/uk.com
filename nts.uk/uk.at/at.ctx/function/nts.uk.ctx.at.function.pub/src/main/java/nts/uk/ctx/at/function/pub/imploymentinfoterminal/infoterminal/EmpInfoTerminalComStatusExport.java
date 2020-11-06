package nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
/**
 * 
 * @author dungbn
 *
 */
@Value
public class EmpInfoTerminalComStatusExport {

	private String contractCode;
	
	private int empInfoTerCode;
	
	private GeneralDateTime signalLastTime;
}
