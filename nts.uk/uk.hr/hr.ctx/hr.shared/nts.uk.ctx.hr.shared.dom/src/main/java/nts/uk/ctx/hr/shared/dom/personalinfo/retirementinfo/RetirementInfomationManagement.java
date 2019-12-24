package nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;


/**
 * @author anhdt
 * 退職情報管理
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetirementInfomationManagement extends AggregateRoot {
	/**
	 * 退職情報リスト 
	 */
	private List<RetirementInfomation> retirementInfomations;
}
