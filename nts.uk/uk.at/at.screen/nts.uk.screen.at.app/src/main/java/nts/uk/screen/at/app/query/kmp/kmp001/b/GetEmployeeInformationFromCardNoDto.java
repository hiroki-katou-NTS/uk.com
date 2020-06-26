package nts.uk.screen.at.app.query.kmp.kmp001.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * カードNOから抽出した社員情報を取得する
 * 
 * 【output】
 * カードNOの設定されている社員
　* ・List<打刻カード>
　* ・List<社員情報>
 * @author chungnt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeInformationFromCardNoDto {

	//List<打刻カード>
	private List<StampCard> stampCards;
	
	//List<社員情報>
	List<EmployeeInformationImport> empInfoList;
}
