package nts.uk.ctx.at.shared.dom.employmentrules.temporarywork;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 臨時勤務利用管理
 * 
 * @author nampt
 *
 */
@Getter
@NoArgsConstructor
public class TemporaryWorkUseManage extends AggregateRoot {

	private String companyId;
	
	private NotUseAtr useClassification;

	public TemporaryWorkUseManage(String companyId, NotUseAtr useClassification) {
		super();
		this.companyId = companyId;
		this.useClassification = useClassification;
	}
}
