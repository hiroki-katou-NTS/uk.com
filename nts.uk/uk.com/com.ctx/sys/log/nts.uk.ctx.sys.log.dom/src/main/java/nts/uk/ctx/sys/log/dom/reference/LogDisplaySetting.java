package nts.uk.ctx.sys.log.dom.reference;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/*
 * author: hiep.th
 */
@Getter
@Setter
@AllArgsConstructor
public class LogDisplaySetting extends AggregateRoot{
	
	/** ID */
	private String logSetId;

	/** the company id */
	private String cid;

	/** the log display setting code */
	private LogSettingCode code;

	/** the log display setting name */
	private LogSettingName name;

	/** the data type */
	private DataTypeEnum dataType;

	/** the record type */
	private RecordTypeEnum recordType;

	/** the list of log setting output items */
	private List<LogSetOutputItem> logSetOutputItems; 
	
	public static LogDisplaySetting createFromJavatype(String logSetId, String cid, String code, String name, 
			Integer dataType,int recordType, List<LogSetOutputItem> logSetOutputItems) {
		return new LogDisplaySetting(logSetId, cid, new LogSettingCode(code), 
				new LogSettingName(name), DataTypeEnum.valueOf(dataType), RecordTypeEnum.valueOf(recordType), logSetOutputItems);
	}

	public LogDisplaySetting(String logSetId, String cid, LogSettingCode code, LogSettingName name,
			DataTypeEnum dataType, RecordTypeEnum recordType) {	
		this.logSetId = logSetId;
		this.cid = cid;
		this.code = code;
		this.name = name;
		this.dataType = dataType;
		this.recordType = recordType;
	}

	

}
