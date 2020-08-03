package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;

/**
 * @author ThanhNX
 *
 *         タイムレコード設定更新リスト
 */
@Getter
public class TimeRecordSetUpdateList extends NRLMachineInfo implements DomainAggregate {

	// タイムレコード設定更新
	private final List<TimeRecordSetUpdate> lstTRecordSetUpdate;

	public TimeRecordSetUpdateList(EmpInfoTerminalCode empInfoTerCode, EmpInfoTerminalName empInfoTerName,
			NRRomVersion romVersion, ModelEmpInfoTer modelEmpInfoTer,
			List<TimeRecordSetUpdate> lstTRecordSetUpdate) {
		super(empInfoTerCode, empInfoTerName, romVersion, modelEmpInfoTer);
		this.lstTRecordSetUpdate = lstTRecordSetUpdate;
	}

}
