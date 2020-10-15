package nts.uk.screen.at.app.query.kmp.kmp001.e;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.TargetPerson;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardNumbersMassGeneratedInput {

	// ログイン社員
	public String loginEmployee;
	
	// 打刻カード作成方法
	public int makeEmbossedCard;
	
	// 対象者リスト
	public List<TargetPerson> targetPerson;
}
