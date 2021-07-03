package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.DigestionHourlyTimeTypeImport;
import nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.InterimRemainImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

/**
 * 暫定子の看護介護管理データ
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class TempChildCareNurseManagementImport extends InterimRemainImport {

	/** 使用数 */
	private ChildCareNurseUsedNumberImport usedNumber;
	/** 時間休暇種類 */
	private  Optional<DigestionHourlyTimeTypeImport> appTimeType;

}
