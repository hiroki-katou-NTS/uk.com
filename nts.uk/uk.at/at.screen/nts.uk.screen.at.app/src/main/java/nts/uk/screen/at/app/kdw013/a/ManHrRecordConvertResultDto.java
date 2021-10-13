package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHrRecordConvertResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManHrRecordConvertResultDto {
	/** 年月日 */
	private GeneralDate ymd;

	/** 作業リスト */
	private List<ManHrTaskDetailDto> taskList;

	/**
	 * 実績内容 thằng này không phải trả về DTo vì tất cả các thuộc tính bên trong
	 * của nó là kiểu dữ liệu nguyên thủy
	 */
	private List<ItemValue> manHrContents;

	public static ManHrRecordConvertResultDto fromDomain(ManHrRecordConvertResult domain) {

		return new ManHrRecordConvertResultDto(domain.getYmd(),
				domain.getTaskList().stream().map(t -> ManHrTaskDetailDto.fromDomain(t)).collect(Collectors.toList()),
				domain.getManHrContents());
	}

}
