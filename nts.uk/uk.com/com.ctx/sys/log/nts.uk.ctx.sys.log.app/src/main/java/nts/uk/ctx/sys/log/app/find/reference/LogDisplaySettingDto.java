package nts.uk.ctx.sys.log.app.find.reference;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.log.dom.reference.LogDisplaySetting;

/*
 * author: hiep.th
 */

@Data
@AllArgsConstructor
public class LogDisplaySettingDto {

	public LogDisplaySettingDto() {
		super();
	}

	/* ID */
	private String logSetId;
	/* Company ID */
	private String cid;
	/* Log display setting code */
	private String code;
	/* Log display setting name */
	private String name;
	/* Data Type */
	private Integer dataType;
	/* Record Type */
	private int recordType;
	
	/** the list of log setting output items */
	private List<LogSetOutputItemDto> logSetOutputItems; 

	public static LogDisplaySettingDto fromDomain(LogDisplaySetting domain) {
		return new LogDisplaySettingDto(domain.getLogSetId(), domain.getCid(), domain.getCode().v(), domain.getName().v(),
				domain.getDataType().code, domain.getRecordType().code, 
				domain.getLogSetOutputItems().stream().map(item -> LogSetOutputItemDto.fromDomain(item)).collect(Collectors.toList()));
	}
	
	public static LogDisplaySettingDto fromDomainNotLogSetOutputItems(LogDisplaySetting domain) {
		return new LogDisplaySettingDto(domain.getLogSetId(), domain.getCid(), domain.getCode().v(), domain.getName().v(),
				domain.getDataType().code, domain.getRecordType().code, null);
	}
	
}
