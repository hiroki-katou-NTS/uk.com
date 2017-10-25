package nts.uk.ctx.at.request.dom.setting.requestofeach;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@EqualsAndHashCode(callSuper=false)
public class RequestOfEachCommon extends AggregateRoot {
	
	@Getter
	@Setter
	private List<RequestAppDetailSetting> requestAppDetailSettings;

	public RequestOfEachCommon(List<RequestAppDetailSetting> requestAppDetailSettings) {
		super();
		this.requestAppDetailSettings = requestAppDetailSettings;
	}
}
