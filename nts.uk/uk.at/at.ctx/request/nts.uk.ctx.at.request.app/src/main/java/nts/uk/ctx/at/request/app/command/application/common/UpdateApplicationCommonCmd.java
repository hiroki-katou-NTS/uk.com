package nts.uk.ctx.at.request.app.command.application.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationCommonCmd {
	private int version;
	
	/**
	 * application ID
	 */
	private String appId;
	
	private String applicationReason;

}
