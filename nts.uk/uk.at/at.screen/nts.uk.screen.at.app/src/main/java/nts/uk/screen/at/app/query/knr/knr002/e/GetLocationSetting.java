package nts.uk.screen.at.app.query.knr.knr002.e;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｅ：就業情報端末のバックアップ.メニュー別OCD.Ｅ：現地の設定の取得
 *
 */
@Stateless
public class GetLocationSetting {
	
	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;
	
	public List<LocationSettingDto> handle(String empInfoTerminalCode) {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		Optional<TimeRecordSetFormatList> timeRecordSetFormatList = timeRecordSetFormatListRepository.findSetFormat(new EmpInfoTerminalCode(empInfoTerminalCode), contractCode);
		
		if (!timeRecordSetFormatList.isPresent()) {
			return Collections.emptyList();
		}
		
		return timeRecordSetFormatList.get().getLstTRSetFormat().stream().map(e -> new LocationSettingDto(e.getMajorNo().v(), e.getMajorClassification().v(), e.getSmallNo().v(), e.getSmallClassification().v(), e.getSettingValue().v(), e.getInputRange().v())).collect(Collectors.toList());
	}

}
