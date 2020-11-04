package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpInfoTerminalExportDataSource {
	
	private int empInfoTerNo;
	private String empInfoTerName;
	private String modelEmpInfoTer;
	private String macAddress;
	private String ipAddress;
	private String terSerialNo;
	private String workLocationCd;
	private String workLocationName;
	private int intervalTimeCondition;
	private int outSupportAtr;
	private String outAtr;
	private String outReplaceAtr;
	private String outReasonAtr;
	private int entranceExitAtr;
	private String empInfoTerMemo;
	
	public static EmpInfoTerminalExportDataSource convertToDatasource(EmpInfoTerminal empInfoTerminal, WorkLocation workLocation) {
		// not check Optional yet
		String outAtr = "置き換えなし";
		switch(empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getReplace()) {
		case USE:
			outAtr = empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().nameId;
			break;
		case NOT_USE:
			default:
				break;
		}
		return new EmpInfoTerminalExportDataSource(empInfoTerminal.getEmpInfoTerCode().v(), empInfoTerminal.getEmpInfoTerName().v(),
				empInfoTerminal.getModelEmpInfoTer().name(), empInfoTerminal.getMacAddress().v(), empInfoTerminal.getIpAddress().isPresent()?empInfoTerminal.getIpAddress().get().v():"",
				empInfoTerminal.getTerSerialNo().isPresent()?empInfoTerminal.getTerSerialNo().get().v():"", empInfoTerminal.getCreateStampInfo().getWorkLocationCd().get().v(), workLocation.workLocationName,
				empInfoTerminal.getIntervalTime().v(), empInfoTerminal.getCreateStampInfo().getConvertEmbCate().getOutSupport().value, outAtr, 
				empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getReplace().nameId, empInfoTerminal.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().nameId, 
				empInfoTerminal.getCreateStampInfo().getConvertEmbCate().getEntranceExit().value, empInfoTerminal.getEmpInfoTerMemo().isPresent()?empInfoTerminal.getEmpInfoTerMemo().get().v():"");
	}
	
	class WorkLocation{
		String workLocationName;
	}
}
