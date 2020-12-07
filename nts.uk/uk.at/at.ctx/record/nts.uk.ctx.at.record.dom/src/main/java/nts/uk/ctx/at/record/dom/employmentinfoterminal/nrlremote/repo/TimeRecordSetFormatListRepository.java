package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

public interface TimeRecordSetFormatListRepository {

	// [3]  タイムレコード設定フォーマットリストを取得する
	public Optional<TimeRecordSetFormatList> findSetFormat(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);

	// [1]  タイムレコード設定フォーマットリストを削除する
	public void removeTRSetFormatList(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);

	// [2]  タイムレコード設定フォーマットリストをインサートする
	public void insert(ContractCode code, TimeRecordSetFormatList trSetFormat);
	
	// 	[4]取得する
	List<TimeRecordSetFormatList> get(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode);
}
