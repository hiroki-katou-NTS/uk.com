package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MSConversion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MSConversionInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRConvertInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StampInfoConversion;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;

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
	private String outAtr; // nr
	private String outReplaceAtr; // nr
	private String outReasonAtr;
	private String entranceExitAtr; // nr
	private String empInfoTerMemo;

	private String workplaceCode;
	private String workplaceName;
	private String stampAttendance;
	private String stampLeaveWork;
	private String stampGoingout;
	private String stampReturn;

	public static EmpInfoTerminalExportDataSource convertToDatasource(EmpInfoTerminal empInfoTerminal,
			WorkLocation workLocation, TargetWorkplace targetWorkplace) {
		// TODO: set value to dto (temporary fixed) #20210520
//		String outAtr = "置き換えなし";
//		switch(NotUseAtr.NOT_USE) {
//		case USE:
//			outAtr = "";
//			break;
//		case NOT_USE:
//			default:
//				break;
//		}

		String stampAttendance = "ー";
		String stampLeaveWork = "ー";
		String stampGoingout = "ー";
		String stampReturn = "ー";

		String outAtr = "ー";
		String entranceExitAtr = "ー";

		StampInfoConversion stampInfoConver = empInfoTerminal.getCreateStampInfo().getStampInfoConver();
		if (stampInfoConver instanceof MSConversionInfo) {
			List<MSConversion> lstMSConversion = ((MSConversionInfo) stampInfoConver).getLstMSConversion();
			for (int i = 0; i < lstMSConversion.size(); i++) {
				switch (lstMSConversion.get(i).getStampClassifi().value) {
				case 0:
					stampAttendance = lstMSConversion.get(i).getStampDestination().nameType;
					break;
				case 1:
					stampLeaveWork = lstMSConversion.get(i).getStampDestination().nameType;
					break;
				case 2:
					stampGoingout = lstMSConversion.get(i).getStampDestination().nameType;
					break;
				case 3:
					stampReturn = lstMSConversion.get(i).getStampDestination().nameType;
					break;
				}
			}
		} else { // NRConvertInfo
			entranceExitAtr = ((NRConvertInfo) stampInfoConver).getEntranceExit().value == 0 ? "ー" : "〇";
			if (((NRConvertInfo) stampInfoConver).getOutPlaceConvert().getReplace().value == 0) {
				outAtr = "置き換えなし";
			} else {
				outAtr = ((NRConvertInfo) stampInfoConver).getOutPlaceConvert().getGoOutReason().get().nameId;
			}
		}

		// TODO: set value to dto (temporary fixed) #20210520
		return new EmpInfoTerminalExportDataSource(empInfoTerminal.getEmpInfoTerCode().v().toString(),
				empInfoTerminal.getEmpInfoTerName().v(), empInfoTerminal.getModelEmpInfoTer().name(),
				empInfoTerminal.getMacAddress().v(),
				empInfoTerminal.getIpAddress().isPresent() ? empInfoTerminal.getIpAddress().get().toString() : "",
				empInfoTerminal.getTerSerialNo().isPresent() ? empInfoTerminal.getTerSerialNo().get().v() : "",
				workLocation != null ? empInfoTerminal.getCreateStampInfo().getWorkLocationCd().get().v() : "",
				workLocation != null ? workLocation.getWorkLocationName().v() : "",
				empInfoTerminal.getIntervalTime().v(), "ー", outAtr, "", "", entranceExitAtr,
				empInfoTerminal.getEmpInfoTerMemo().isPresent() ? empInfoTerminal.getEmpInfoTerMemo().get().v() : "",
				targetWorkplace.getWorkplaceCode(), targetWorkplace.getWorkplaceName(), stampAttendance, stampLeaveWork,
				stampGoingout, stampReturn);
	}
}
