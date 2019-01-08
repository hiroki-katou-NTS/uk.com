package nts.uk.ctx.exio.app.find.exo.awdataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.dataformat.init.AwDataFormatSet;

@Getter
@Setter
@AllArgsConstructor
public class AwDataFormatDTO {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 休業時出力
	 */
	private String closedOutput;

	/**
	 * 休職時出力
	 */
	private String absenceOutput;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 在職時出力
	 */
	private String atWorkOutput;

	/**
	 * 退職時出力
	 */
	private String retirementOutput;

	public static AwDataFormatDTO fromDomain(AwDataFormatSet domain) {
		String closedOutput      = domain.getClosedOutput().isPresent() ? domain.getClosedOutput().get().v() : null;
		String absenceOutput     = domain.getAbsenceOutput().isPresent() ? domain.getAbsenceOutput().get().v() : null;
		String valueOfFixedValue = domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null;
		String atWorkOutput      = domain.getAtWorkOutput().isPresent() ? domain.getAtWorkOutput().get().v() : null;
		String retirementOutput  = domain.getRetirementOutput().isPresent() ? domain.getRetirementOutput().get().v() : null;
		return new AwDataFormatDTO(domain.getCid(), closedOutput, absenceOutput, domain.getFixedValue().value,
				valueOfFixedValue, atWorkOutput, retirementOutput);
	}
}
