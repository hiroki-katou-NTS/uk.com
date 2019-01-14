package exportexcel.securitypolicy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccountLockPolicyDtoExcel {
	public int errorCount;
	public int lockInterval;
	public String lockOutMessage;
	public boolean isUse;
}
