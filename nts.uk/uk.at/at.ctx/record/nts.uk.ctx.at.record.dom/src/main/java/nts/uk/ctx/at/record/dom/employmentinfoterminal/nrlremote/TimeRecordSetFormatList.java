package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat.TimeRecordSetFormatBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetUpdateReceptDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;

/**
 * @author ThanhNX
 *
 *         タイムレコード設定フォーマットリスト
 */
@Getter
public class TimeRecordSetFormatList extends NRLMachineInfo implements DomainAggregate {

	// タイムレコード設定フォーマット
	private final List<TimeRecordSetFormat> lstTRSetFormat;

	public TimeRecordSetFormatList(EmpInfoTerminalCode empInfoTerCode, EmpInfoTerminalName empInfoTerName,
			NRRomVersion romVersion, ModelEmpInfoTer modelEmpInfoTer, List<TimeRecordSetFormat> lstTRSetFormat) {
		super(empInfoTerCode, empInfoTerName, romVersion, modelEmpInfoTer);
		this.lstTRSetFormat = lstTRSetFormat;
	}

	// [S-1] 変換する
	public static TimeRecordSetFormatList convert(EmpInfoTerminalCode empInfoTerCode, TimeRecordSettingInfoDto input) {

		AtomicInteger countBig = new AtomicInteger(1);
		AtomicInteger countSmall = new AtomicInteger(1);
		List<TimeRecordSetFormat> lstTRSetFormat = new ArrayList<>();
		
		Map<String, List<TimeRecordSetReceptFormatDto>> mapInput = input.getLstReceptFormat().stream()
				.collect(Collectors.groupingBy(x -> x.getMajorClassification(), LinkedHashMap::new, Collectors.toList()));
                        
		mapInput.forEach((bigKey, dataValue )-> {
			countSmall.set(1);
			dataValue.stream().forEach(set -> {
				Optional<TimeRecordSetUpdateReceptDto> currentValueOpt = input.getLstUpdateRecept().stream()
						.filter(currentValue -> currentValue.getVariableName().equals(set.getVariableName())).findFirst();
				lstTRSetFormat.add(new TimeRecordSetFormatBuilder(new MajorNameClassification(set.getMajorClassification()),
						new MajorNameClassification(set.getSmallClassification()), new VariableName(set.getVariableName()),
						NrlRemoteInputType.valueInputTypeOf(set.getType()),
						new NumberOfDigits(Integer.parseInt(set.getNumberOfDigits())))
								.settingValue(new SettingValue(set.getSettingValue()))
								.inputRange(new NrlRemoteInputRange(set.getInputRange()))
								.rebootFlg(set.getRebootFlg().equals("1")).value(currentValueOpt
										.map(x -> new SettingValue(x.getUpdateValue())).orElse(new SettingValue("")))
								.smallNo(new MajorNoClassification(countSmall.get()))
								.majorNo(new MajorNoClassification(countBig.get()))
								.build());
				countSmall.incrementAndGet();
		});
			countBig.incrementAndGet();
		});
		return new TimeRecordSetFormatList(empInfoTerCode, new EmpInfoTerminalName(input.getEmpInfoTerName()),
				new NRRomVersion(input.getRomVersion()),
				ModelEmpInfoTer.valueOf(Integer.parseInt(input.getModelEmpInfoTer())), lstTRSetFormat);
	}

}
