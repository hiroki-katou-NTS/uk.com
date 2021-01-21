package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;

/**
 * @author ThanhNX
 *
 *         NRLマシン情報
 */
@Getter
public class NRLMachineInfo {

	// コード
	private final EmpInfoTerminalCode empInfoTerCode;

	// 機種名
	private final EmpInfoTerminalName empInfoTerName;

	// ROMバージョン
	private final NRRomVersion romVersion;

	// 機種区分
	private final ModelEmpInfoTer modelEmpInfoTer;

	/**
	 * @param empInfoTerCode:  コード
	 * @param empInfoTerName:  機種名
	 * @param terSerialNo:     ROMバージョン
	 * @param modelEmpInfoTer: 機種区分
	 */
	public NRLMachineInfo(EmpInfoTerminalCode empInfoTerCode, EmpInfoTerminalName empInfoTerName,
			NRRomVersion romVersion, ModelEmpInfoTer modelEmpInfoTer) {
		this.empInfoTerCode = empInfoTerCode;
		this.empInfoTerName = empInfoTerName;
		this.romVersion = romVersion;
		this.modelEmpInfoTer = modelEmpInfoTer;
	}

}
