package nts.uk.ctx.workflow.dom.approvermanagement.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class JobAssignSetting extends AggregateRoot{
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * 兼務者を含める
	 */
	private Boolean isConcurrently;
}
