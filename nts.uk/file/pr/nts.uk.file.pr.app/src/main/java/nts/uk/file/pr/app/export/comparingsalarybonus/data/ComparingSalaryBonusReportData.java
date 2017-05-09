package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * ComparingSalaryBonusReportData
 * @author lanlt
 *
 */

@Getter
@Setter
public class ComparingSalaryBonusReportData {
	ComparingSalaryBonusHeaderReportData headerData;
	HeaderTable  headerTable;
	List<DeparmentInf> deparmentInf;
	
}
