package nts.uk.file.at.app.export.specialholiday;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
public class SpecialHolidayExportClass {
	
    @Inject
    private SpecialHolidayExRepository specialHolidayExRepository;
	
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0)
    {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_106, TextResource.localize("KMF004_106"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_107, TextResource.localize("KMF004_107"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_108, TextResource.localize("KMF004_108"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_109, TextResource.localize("KMF004_109"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_110, TextResource.localize("KMF004_110"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_111, TextResource.localize("KMF004_111"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_112, TextResource.localize("KMF004_112"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_113, TextResource.localize("KMF004_113"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_114, TextResource.localize("KMF004_114"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_115, TextResource.localize("KMF004_115"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_116, TextResource.localize("KMF004_116"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_117, TextResource.localize("KMF004_117"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_118, TextResource.localize("KMF004_118"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_119, TextResource.localize("KMF004_119"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_120, TextResource.localize("KMF004_120"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_121, TextResource.localize("KMF004_121"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_122, TextResource.localize("KMF004_122"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_123, TextResource.localize("KMF004_123"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_124, TextResource.localize("KMF004_124"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_125, TextResource.localize("KMF004_125"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_126, TextResource.localize("KMF004_126"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_127, TextResource.localize("KMF004_127"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_128, TextResource.localize("KMF004_128"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_129, TextResource.localize("KMF004_129"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_130, TextResource.localize("KMF004_130"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_131, TextResource.localize("KMF004_131"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_132, TextResource.localize("KMF004_132"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_133, TextResource.localize("KMF004_133"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_134, TextResource.localize("KMF004_134"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_135, TextResource.localize("KMF004_135"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_136, TextResource.localize("KMF004_136"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_137, TextResource.localize("KMF004_137"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_138, TextResource.localize("KMF004_138"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_139, TextResource.localize("KMF004_139"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_140, TextResource.localize("KMF004_140"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_141, TextResource.localize("KMF004_141"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_142, TextResource.localize("KMF004_142"),
        ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterData> getMasterDatas(MasterListExportQuery query)
    {
        String companyId = AppContexts.user().companyId();
        return specialHolidayExRepository.getSPHDExportData(companyId);
    }
    
    public String mainSheetName(){
		return TextResource.localize("KMF004_153");
	} 
    
    public List<MasterHeaderColumn> getHeaderColumnSPHDEvent(){
    	List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_143, TextResource.localize("KMF004_143"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_144, TextResource.localize("KMF004_144"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_161, TextResource.localize("KMF004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_145, TextResource.localize("KMF004_145"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_146, TextResource.localize("KMF004_146"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_147, TextResource.localize("KMF004_147"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_148, TextResource.localize("KMF004_148"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_149, TextResource.localize("KMF004_149"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_150, TextResource.localize("KMF004_150"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_162, TextResource.localize("KMF004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_151, TextResource.localize("KMF004_151"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(SpecialHolidayUtils.KMF004_152, TextResource.localize("KMF004_152"),
				ColumnTextAlign.LEFT, "", true));
    	return columns;
    }

	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
    
    public List<MasterData> getSPHDEventMasterData(){
    	String cid = AppContexts.user().companyId();
    	return specialHolidayExRepository.getSPHDEventExportData(cid);
    }
    
    public List<SheetData> extraSheets(MasterListExportQuery query){
    	
    	List<SheetData> sheets = new ArrayList<>();
    	
    	SheetData sheetData = SheetData.builder()
						    			.mainData(getSPHDEventMasterData())
						    			.mainDataColumns(getHeaderColumnSPHDEvent())
						    			.sheetName(TextResource.localize("KMF004_154"))
						    			.mode(MasterListMode.NONE)
						    			.build();
    	sheets.add(sheetData);
		return sheets;
	}
}
