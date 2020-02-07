package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.List;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
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
@Stateless
public class MedicalhistoryManagement  extends AggregateRoot {
	/**
	 * 外国人在留履歴情報のリスト
	 */
	private List<MedicalhistoryItemResults> medicalhistoryItemResults;
	/**
	 * 検索済み個人IDリスト
	 */
	private List<String> searchedSIDs;
	
}

