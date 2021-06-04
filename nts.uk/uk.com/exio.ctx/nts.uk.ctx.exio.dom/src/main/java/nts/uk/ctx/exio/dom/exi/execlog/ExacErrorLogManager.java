package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceLineNumber;
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

    public void addLog(String csvItem, String editedValue, int lineNo, String errorId, ErrorOccurrenceIndicator errorDiv) {
    	this.lastLogSeqNumber+=1;
        val log = new ExacErrorLog(
        		lastLogSeqNumber,
    			this.cid,
    			this.externalProcessId,
    			Optional.ofNullable(""),
    			Optional.ofNullable(editedValue),
    			Optional.ofNullable(TextResource.localize(errorId)),
    			new AcceptanceLineNumber(lineNo),
				GeneralDateTime.now(),
				Optional.ofNullable(""),
				errorDiv
    		);
    	this.logs.add(log);
    }

	public void addLogByTableName(String csvItem, String editedValue, int lineNo, String errorId, ErrorOccurrenceIndicator errorDiv) {
    	this.lastLogSeqNumber+=1;
        val log = new ExacErrorLog(
        		lastLogSeqNumber,
    			this.cid,
    			this.externalProcessId,
    			Optional.ofNullable("" + "[" + "" + "]"),
    			Optional.ofNullable(editedValue),
    			Optional.ofNullable(TextResource.localize(errorId)),
    			new AcceptanceLineNumber(lineNo),
				GeneralDateTime.now(),
				Optional.ofNullable(""),
				errorDiv
    		);
    	this.logs.add(log);
		
	}
	
	public void save(Require require) {
		require.save(this.logs);
	}
	
	public interface Require {
		void save(List<ExacErrorLog> logs);
	}
}
