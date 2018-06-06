package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Anh.Bd
 *
 */
@NoArgsConstructor
@Getter
public class ApproverOutput {
	//フェーズ
	Integer phase;
	//社員名
	String empName;
	//人数
	Integer numOfPeople;
	public ApproverOutput(Integer phase, String empName, Integer numOfPeople) {
		super();
		this.phase = phase;
		this.empName = Objects.isNull(empName) ? "" : empName;
		this.numOfPeople = Objects.isNull(numOfPeople) ? 0 : numOfPeople;
	}
	
}
