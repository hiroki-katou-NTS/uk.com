package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
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
		String outAtr = "置き換えなし";
		switch(empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getReplace()) {
		case USE:
			outAtr = empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().nameId;
			break;
		case NOT_USE:
			default:
				break;
		}
		
		String empInfoTerNo = "0000" + empInfoTerminal.getEmpInfoTerCode().v();
		empInfoTerNo = empInfoTerNo.substring(empInfoTerNo.length()-4);
		
		return new EmpInfoTerminalExportDataSource(empInfoTerNo, empInfoTerminal.getEmpInfoTerName().v(),
				empInfoTerminal.getModelEmpInfoTer().name(), empInfoTerminal.getMacAddress().v(), 
				empInfoTerminal.getIpAddress().isPresent() ? empInfoTerminal.getIpAddress().get().getFullIpAddress() : "",
				empInfoTerminal.getTerSerialNo().isPresent() ? empInfoTerminal.getTerSerialNo().get().v() : "", 
				workLocation != null ? empInfoTerminal.getCreateStampInfo().getWorkLocationCd().get().v() : "", 
				workLocation != null ? workLocation.getWorkLocationName().v() : "",
				empInfoTerminal.getIntervalTime().v(), empInfoTerminal.getCreateStampInfo().getConvertEmbCate().getOutSupport().value == 1 ? "〇" : "ー", 
				outAtr, empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getReplace().nameId, 
				empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().isPresent() ? empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().nameId : "", 
				empInfoTerminal.getCreateStampInfo().getConvertEmbCate().getEntranceExit().value == 1 ? "〇" : "ー", 
				empInfoTerminal.getEmpInfoTerMemo().isPresent() ? empInfoTerminal.getEmpInfoTerMemo().get().v() : "");
	}
}
