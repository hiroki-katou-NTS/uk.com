package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author sonnlb
 * 
 *         打刻カード編集
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.打刻カード編集
 *
 */
@AllArgsConstructor
@Getter
public class StampCardEditing extends AggregateRoot {
	
	/**
	 * 会社ID
	 */
	private final String companyId;
	
	/**
	 * 桁数
	 */

	private StampCardDigitNumber digitsNumber;
	
	/**
	 * 編集方法
	 */
	private StampCardEditMethod stampMethod;

	public static StampCardEditing createFromJavaType(String companyId, int digitsNumber, int method) {
		return new StampCardEditing(companyId, new StampCardDigitNumber(digitsNumber),
				EnumAdaptor.valueOf(method, StampCardEditMethod.class));
	}
	
	/**
	 * [1] 打刻カードを作成する
	 * 
	 * @param 会社コード
	 *            companyCd
	 * @param 社員コード
	 *            employeeCd
	 * 
	 * @return 打刻カード番号 Optional<打刻カード番号>
	 */
	public Optional<String> createStampCard(String companyCd, String employeeCd) {
		// $打刻カード番号 = 会社コード + 社員コード
		String cardNumber = companyCd + employeeCd;
		// if $打刻カード番号.length() > @.桁数
		if (cardNumber.length() > this.digitsNumber.v()) {

			return Optional.empty();
		}
		// @打刻カード編集方法.打刻カード番号を編集する(@桁数, $打刻カード番号)
		return Optional.ofNullable(this.stampMethod.editCardNumber(String.valueOf(this.digitsNumber.v()), cardNumber));
	}

	/**
	 * [2] 打刻カード番号を編集する
	 * 
	 * @param 編集前番号
	 *            number
	 * @return 編集後番号 String
	 */

	public String editCardNumber(String cardNumber) {

		// @打刻カード編集方法.打刻カード番号を編集する(@桁数, 編集前番号)
		return this.stampMethod.editCardNumber(String.valueOf(this.digitsNumber.v()), cardNumber);

	}

}
