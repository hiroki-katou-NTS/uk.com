package nts.uk.screen.at.app.query.knr.knr002.e;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;

@AllArgsConstructor
@Setter
@Getter
public class BackupContentDto {

	private Integer majorNo;
	
	private String majorClassification;
	
	private Integer smallNo;
	
	private String smallClassification;
	
	private String settingValue;
	
	private String inputRange;
	
	// grid primary key
	private String key;
	
	public static BackupContentDto toDto(TimeRecordSetFormat domain) {
		return new BackupContentDto(domain.getMajorNo().v(), domain.getMajorClassification().v(),
									domain.getSmallNo().v(), domain.getSmallClassification().v(),
									domain.getSettingValue().v(), domain.getInputRange().v(), domain.getMajorClassification().v() + domain.getSmallClassification().v());
	}
}
