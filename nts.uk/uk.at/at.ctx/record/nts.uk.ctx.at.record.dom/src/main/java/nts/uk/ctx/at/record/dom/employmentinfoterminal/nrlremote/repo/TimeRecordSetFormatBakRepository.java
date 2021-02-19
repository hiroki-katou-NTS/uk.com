package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

public interface TimeRecordSetFormatBakRepository {

	// [1] insert(タイムレコード設定フォーマットのバックアップ)
	void insert(TimeRecordSetFormatBak timeRecordSetFormatBak);
	
	// [2] update(タイムレコード設定フォーマットのバックアップ)
	void update(TimeRecordSetFormatBak timeRecordSetFormatBak);
	
	// [３] delete(タイムレコード設定フォーマットのバックアップ)
	void delete(TimeRecordSetFormatBak timeRecordSetFormatBak);
	
	// [4] 取得する
	List<TimeRecordSetFormatBak> get(ContractCode contractCode);
	
	// [5]タイムレコード設定フォーマットを 取得する
	List<TimeRecordSetFormat> getTimeRecordSetFormat(ContractCode contractCode, EmpInfoTerminalCode code);
	
	// [6] 取得する
	Optional<TimeRecordSetFormatBak> get(ContractCode contractCode, EmpInfoTerminalCode code);
}
