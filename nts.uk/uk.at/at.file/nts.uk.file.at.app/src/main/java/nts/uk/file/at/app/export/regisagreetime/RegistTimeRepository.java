package nts.uk.file.at.app.export.regisagreetime;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

/**
 *  マスタリストを出力する
 */
public interface RegistTimeRepository {
	
	List<MasterData> getDataExportSheet1();
	List<MasterData> getDataExportSheet2();
	List<MasterData> getDataExportSheet3();
	List<MasterData> getDataExportSheet4();
	List<MasterData> getDataExportSheet5();
	List<MasterData> getDataExportSheet6();
	List<MasterData> getDataExportSheet7();
	List<MasterData> getDataExportSheet8();
	List<MasterData> getDataExportSheet9();
	List<MasterData> getDataExportSheet10();
	List<MasterData> getDataExportSheet11();
	List<MasterData> getDataExportSheet12(GeneralDate startDate, GeneralDate endDate);
}
