package nts.uk.ctx.sys.auth.app.find.user;

import java.util.List;

import lombok.Value;

@Value
public class UserKeyDto {

	 private String key;
	 
	 private boolean Special;
	 
	 private boolean Multi;
	 
	 private List<String> userId;
}
