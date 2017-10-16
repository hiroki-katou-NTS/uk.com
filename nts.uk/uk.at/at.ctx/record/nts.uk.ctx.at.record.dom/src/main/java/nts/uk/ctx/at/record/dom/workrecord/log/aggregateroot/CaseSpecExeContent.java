/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.log.usecase.CalExeSettingInfor;

/**
 * @author danpv
 *
 */
@Getter
public class CaseSpecExeContent extends AggregateRoot {

	private long id;

	private int orderNumber;

	private String useCaseName;

	private CalExeSettingInfor settingInformation;

}
