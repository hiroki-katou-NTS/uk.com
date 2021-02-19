package nts.uk.ctx.at.record.app.command.knr.knr002.f;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRLMachineInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;

@Value
public class TimeRecordSettingUpdateRegisterCommand {
	
	//	復旧先就業情報端末コード<List>
	private List<EmpInfoTerminalCode> restoreDestinationTerminalList;
	// 	タイムレコード設定フォーマット<List>
	private List<TimeRecordSetFormat> timeRecordSettingFormatList;
	//	<機種名、ROMバージョン、機種区分>（List)
	private List<NRLMachineInfo> nrlMachineInfoList; 
}
