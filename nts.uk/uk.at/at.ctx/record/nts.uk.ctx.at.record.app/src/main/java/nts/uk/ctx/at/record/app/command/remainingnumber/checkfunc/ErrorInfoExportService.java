package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVFileData;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;

@Stateless
public class ErrorInfoExportService extends ExportService<OutputErrorInfoCommand> {

	/** The generator. */
    @Inject
    private CSVReportGenerator generator;
    
    /** The Constant LST_NAME_ID. */
    private static final List<String> LST_NAME_ID_HEADER = Arrays.asList("","レコード番号","CSV項目名",
    		"受入項目","値","エラーメッセージ");
    /** The Constant FILE NAM CSV. */
    private static final String FILE_NAME = "エラー一覧.csv";
    
    @Override
	protected void handle(ExportServiceContext<OutputErrorInfoCommand> context) {
    	OutputErrorInfoCommand lstError = context.getQuery();
    	if (lstError == null) {
    		return;
    	}
    	List<String> header = this.getTextHeader();  
		List<Map<String, Object>> dataSource = new ArrayList<>();
    	CSVFileData dataExport = new CSVFileData(FILE_NAME, header, dataSource);
        // generate file
        this.generator.generate(context.getGeneratorContext(), dataExport);		
	}
    
    /**
     * Fin header.
     *
     * @return the list
     */
    private List<String> getTextHeader() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }
}
