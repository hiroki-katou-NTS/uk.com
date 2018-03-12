package alarmPatternRepo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AlarmPatternSettingSimple {
	private String alarmCode;
	private String alarmName;
	private boolean authSetting;
	private List<String> roleIds; 
}
