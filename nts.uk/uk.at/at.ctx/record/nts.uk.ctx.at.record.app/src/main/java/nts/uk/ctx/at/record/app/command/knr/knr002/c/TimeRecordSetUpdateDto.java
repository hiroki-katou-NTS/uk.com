package nts.uk.ctx.at.record.app.command.knr.knr002.c;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeRecordSetUpdateDto {

	private String variableName;
	
	private String updateValue;
}
