package nts.uk.screen.at.app.kdw013.query;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormat;

/**
 * @author thanhpv
 * @description out put for 作業入力パネルを起動する
 */
public class WorkDataMasterInformationDispLayDto extends WorkDataMasterInformationDto{
	
	//Optional<工数入力表示フォーマット>
	public ManHrInputDisplayFormatDto manHrInputDisplayFormat;

	public WorkDataMasterInformationDispLayDto(WorkDataMasterInformationDto workDataMasterInformation,
			Optional<ManHrInputDisplayFormat> manHrInputDisplayFormat) {
		super(workDataMasterInformation.taskFrameNo1, workDataMasterInformation.taskFrameNo2, workDataMasterInformation.taskFrameNo3, workDataMasterInformation.taskFrameNo4, workDataMasterInformation.taskFrameNo5, workDataMasterInformation.workLocation,
				workDataMasterInformation.taskSupInfoChoicesDetails, workDataMasterInformation.manHourRecordItems);
		this.manHrInputDisplayFormat = manHrInputDisplayFormat.map(c -> new ManHrInputDisplayFormatDto(c)).orElse(null);
	}
}
