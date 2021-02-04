package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
/**
 * 
 * @author dungbn
 * タイムレコード設定フォーマットのバックアップ
 *
 */
@Getter
public class TimeRecordSetFormatBak extends NRLMachineInfo implements DomainAggregate {

	// タイムレコード設定フォーマット
	private List<TimeRecordSetFormat> listTimeRecordSetFormat;

	// バックアップ年月日
	private GeneralDateTime backupDate;

	// [C-0]
	public TimeRecordSetFormatBak(EmpInfoTerminalCode empInfoTerCode, EmpInfoTerminalName empInfoTerName,
			NRRomVersion romVersion, ModelEmpInfoTer modelEmpInfoTer, List<TimeRecordSetFormat> listTimeRecordSetFormat, GeneralDateTime backupDate) {
		super(empInfoTerCode, empInfoTerName, romVersion, modelEmpInfoTer);
		
		this.listTimeRecordSetFormat = listTimeRecordSetFormat;
		this.backupDate = backupDate;
	}

}
