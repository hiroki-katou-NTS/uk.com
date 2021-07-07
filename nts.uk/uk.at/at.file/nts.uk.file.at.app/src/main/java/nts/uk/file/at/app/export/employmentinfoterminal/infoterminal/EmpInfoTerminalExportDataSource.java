package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * @author huylq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpInfoTerminalExportDataSource {
	
	private String empInfoTerNo;
	private String empInfoTerName;
	private String modelEmpInfoTer;
	private String macAddress;
	private String ipAddress;
	private String terSerialNo;
	private String workLocationCd;
	private String workLocationName;
	private int intervalTimeCondition;
	private String outSupportAtr;
	private String outAtr;
	private String outReplaceAtr;
	private String outReasonAtr;
	private String entranceExitAtr;
	private String empInfoTerMemo;
	
	public static EmpInfoTerminalExportDataSource convertToDatasource(EmpInfoTerminal empInfoTerminal, WorkLocation workLocation) {
		//TODO: set value to dto (temporary fixed) #20210520
		String outAtr = "置き換えなし";
		switch(NotUseAtr.NOT_USE) {
		case USE:
			outAtr = "";
			break;
		case NOT_USE:
			default:
				break;
		}
		//TODO: set value to dto (temporary fixed) #20210520
		return new EmpInfoTerminalExportDataSource(empInfoTerminal.getEmpInfoTerCode().v().toString(), empInfoTerminal.getEmpInfoTerName().v(),
				empInfoTerminal.getModelEmpInfoTer().name(), empInfoTerminal.getMacAddress().v(), 
				empInfoTerminal.getIpAddress().isPresent() ? empInfoTerminal.getIpAddress().get().toString() : "",
				empInfoTerminal.getTerSerialNo().isPresent() ? empInfoTerminal.getTerSerialNo().get().v() : "", 
				workLocation != null ? empInfoTerminal.getCreateStampInfo().getWorkLocationCd().get().v() : "", 
				workLocation != null ? workLocation.getWorkLocationName().v() : "",
				empInfoTerminal.getIntervalTime().v(),  "ー", 
				outAtr, "", 
				"", 
			   "ー", 
				empInfoTerminal.getEmpInfoTerMemo().isPresent() ? empInfoTerminal.getEmpInfoTerMemo().get().v() : "");
	}
}
