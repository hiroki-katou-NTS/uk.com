package nts.uk.ctx.at.shared.dom.outsideot.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 36協定時間対象項目
 */
@Getter
@AllArgsConstructor
public class Time36AgreementTargetItem {
	/**
	 * 対象残業枠NO
	 */
	private List<Integer> overtimeFrNo;
	/**
	 * 対象休出枠NO
	 */
	private List<Integer> breakFrNo;
	/**
	 * フレックスを対象とする
	 */
	private boolean targetFlex;
}
