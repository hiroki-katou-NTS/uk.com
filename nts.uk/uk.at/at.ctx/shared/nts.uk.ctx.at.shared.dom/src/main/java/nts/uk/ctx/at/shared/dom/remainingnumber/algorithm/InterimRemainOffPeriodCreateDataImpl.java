package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
@Stateless
public class InterimRemainOffPeriodCreateDataImpl implements InterimRemainOffPeriodCreateData{
	@Inject
	private InterimRemainOffDateCreateData createDataService;
	@Override
	public Map<GeneralDate, DailyInterimRemainMngData> createInterimRemainDataMng(
			InterimRemainCreateDataInputPara inputParam) {
		Map<GeneralDate, DailyInterimRemainMngData> dataOutput = new HashMap<>();
		for(int i = 0; inputParam.getDateData().start().compareTo(inputParam.getDateData().end()) + i <= 0; i++){
			GeneralDate loopDate = inputParam.getDateData().start().addDays(i);
			//対象日のデータを抽出する
			InterimRemainCreateInfor dataCreate = this.extractDataOfDate(loopDate, inputParam);
			//アルゴリズム「指定日の暫定残数管理データを作成する」
			DailyInterimRemainMngData outPutdata = createDataService.createData(inputParam.getCid(), inputParam.getSid(), loopDate, inputParam.isDayOffTimeIsUse(), dataCreate);
			dataOutput.put(loopDate, outPutdata);
		}
		
		return dataOutput;
	}

	@Override
	public InterimRemainCreateInfor extractDataOfDate(GeneralDate baseDate,
			InterimRemainCreateDataInputPara inputInfor) {
		InterimRemainCreateInfor detailData = new InterimRemainCreateInfor(Optional.empty(), Optional.empty(), Collections.emptyList());
		//実績を抽出する
		List<RecordRemainCreateInfor> recordData = inputInfor.getRecordData()
				.stream()
				.filter(x -> x.getSid().equals(inputInfor.getSid()) && x.getYmd().equals(baseDate))
				.collect(Collectors.toList());
		if(!recordData.isEmpty()) {
			detailData.setRecordData(Optional.of(recordData.get(0)));
		}
		//対象日の申請を抽出する
		List<AppRemainCreateInfor> appData = inputInfor.getAppData().stream()
				.filter(y -> y.getSid().equals(inputInfor.getSid()) && y.getAppDate().equals(baseDate))
				.collect(Collectors.toList());
		detailData.setAppData(appData);
		//対象日の予定を抽出する
		List<ScheRemainCreateInfor> scheData = inputInfor.getScheData().stream()
				.filter(z -> z.getSid().equals(inputInfor.getSid()) && z.getYmd().equals(baseDate))
				.collect(Collectors.toList());
		if(!scheData.isEmpty()) {
			detailData.setScheData(Optional.of(scheData.get(0)));
		}
	
		return detailData;
		
	}

	

}
