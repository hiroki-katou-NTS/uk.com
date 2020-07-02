package nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 並び替え設定
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.社員並び替え
 * @author HieuLt
 *
 */

public class SortSetting implements DomainAggregate{
	@Getter   
	/** 会社ID **/
	private final String companyID;
	@Getter
	/** 並び替え優先順 **/
	private List<OrderedList> lstOrderedList;
	
	public SortSetting(String companyID, List<OrderedList> lstOrderedList) {
		super();
		Set<OrderedList> uniqueElements = new HashSet<OrderedList>(lstOrderedList);
		//inv-1	@並び替え優先順.種類は重複しないこと
		if(uniqueElements.size() < lstOrderedList.size()){
			throw new BusinessException("Msg_1612");
		}
		//inv-2	1 <= @並び替え優先順.Size <= 3			
		if(lstOrderedList.size() <=3){
			throw new BusinessException("Msg_1613");
		}
		this.companyID = companyID;
		this.lstOrderedList = lstOrderedList;
	}
	
	
	
}


