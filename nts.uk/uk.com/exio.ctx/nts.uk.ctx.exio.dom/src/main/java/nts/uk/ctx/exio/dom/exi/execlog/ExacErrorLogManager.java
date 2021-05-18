package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
import nts.uk.ctx.exio.dom.exi.csvimport.CsvItem;
import nts.uk.shr.com.i18n.TextResource;

public class ExacErrorLogManager {
    private int lastLogSeqNumber;
    private String cid;
    private String externalProcessId;
    private List<ExacErrorLog> logs;
	
    public ExacErrorLogManager(String cid, String externalProcessId) {
    	this.lastLogSeqNumber = -1;
    	this.cid = cid;
    	this.externalProcessId = externalProcessId;
    }

    public void addLog(CsvItem csvItem, String editedValue, int lineNo, String errorId, ErrorOccurrenceIndicator errorDiv) {
    	this.lastLogSeqNumber+=1;
        val log = new ExacErrorLog(
        		lastLogSeqNumber,
    			this.cid,
    			this.externalProcessId,
    			Optional.ofNullable(csvItem.getName()),
    			Optional.ofNullable(editedValue),
    			Optional.ofNullable(TextResource.localize(errorId)),
    			new AcceptanceLineNumber(lineNo),
				GeneralDateTime.now(),
				Optional.ofNullable(csvItem.getAcceptItem().get().getItemName()),
				errorDiv
    		);
    	this.logs.add(log);
    }

	public void addLogByTableName(CsvItem csvItem, String editedValue, int lineNo, String errorId, ErrorOccurrenceIndicator errorDiv) {
    	this.lastLogSeqNumber+=1;
    	val acceptItem = csvItem.getAcceptItem().get();
        val log = new ExacErrorLog(
        		lastLogSeqNumber,
    			this.cid,
    			this.externalProcessId,
    			Optional.ofNullable(acceptItem.getTableName() + "[" + acceptItem.getColumnName() + "]"),
    			Optional.ofNullable(editedValue),
    			Optional.ofNullable(TextResource.localize(errorId)),
    			new AcceptanceLineNumber(lineNo),
				GeneralDateTime.now(),
				Optional.ofNullable(acceptItem.getItemName()),
				errorDiv
    		);
    	this.logs.add(log);
		
	}
}
