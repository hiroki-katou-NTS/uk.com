package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.util.List;

import lombok.Data;

/**
 * The Class CompanyDivergenceReferenceTimeSaveCommand.
 */
@Data
public class ComDivergenceRefTimeSaveCommand {
	
	/** The list data setting. */
	private List<ComDivergenceRefTimeSaveDto> listDataSetting;
	
	/**
	 * Instantiates a new company divergence reference time save command.
	 *
	 * @param listDataSetting the list data setting
	 */
	public ComDivergenceRefTimeSaveCommand(List<ComDivergenceRefTimeSaveDto> listDataSetting) {
		this.listDataSetting = listDataSetting;
	}
}
