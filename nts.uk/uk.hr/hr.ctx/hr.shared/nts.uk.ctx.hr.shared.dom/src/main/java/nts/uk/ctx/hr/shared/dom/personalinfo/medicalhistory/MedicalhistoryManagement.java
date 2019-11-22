package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.List;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author laitv
 * 受診履歴管理
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Stateless
public class MedicalhistoryManagement  extends AggregateRoot {
	/**
	 * 外国人在留履歴情報のリスト
	 */
	private List<MedicalhistoryItem> medicalhistoryItems;
	/**
	 * 検索済み個人IDリスト
	 */
	private List<String> searchedSIDs;
	
	public void fillData(List<MedicalhistoryItem> medicalhistoryItems, List<String> searchedSIDs){
		this.medicalhistoryItems = medicalhistoryItems;
		this.searchedSIDs = searchedSIDs;
	}
}

