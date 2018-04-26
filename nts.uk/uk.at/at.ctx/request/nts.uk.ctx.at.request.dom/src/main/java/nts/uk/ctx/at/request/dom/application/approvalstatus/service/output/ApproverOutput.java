package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Anh.Bd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApproverOutput {
	//フェーズ
	Integer phase;
	//社員名
	String empName;
	//人数
	Integer numOfPeople;
}
