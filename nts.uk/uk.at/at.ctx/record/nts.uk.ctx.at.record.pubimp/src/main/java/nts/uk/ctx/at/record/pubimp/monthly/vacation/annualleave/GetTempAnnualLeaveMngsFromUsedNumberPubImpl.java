package nts.uk.ctx.at.record.pubimp.monthly.vacation.annualleave;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetTempAnnualLeaveMngsFromUsedNumberService;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetTempAnnualLeaveMngsFromUsedNumberPub;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveOverNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp.AppTimeTypeExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp.CreateAtrExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp.RemainAtrExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp.RemainTypeExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp.TempAnnualLeaveMngsExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

@Stateless
public class GetTempAnnualLeaveMngsFromUsedNumberPubImpl implements GetTempAnnualLeaveMngsFromUsedNumberPub {

	/**
	 * 使用数を暫定年休管理データに変換する
	 *
	 * @param employeeId 社員ID
	 * @param usedNumber 使用数
	 * @return 暫定年休管理データ(List)
	 */
	@Override
	public List<TempAnnualLeaveMngsExport> getTempAnnualLeaveData(String employeeId, LeaveUsedNumberExport usedNumber) {

		LeaveUsedNumber usedNumberDom = LeaveUsedNumber.createFromJavaType(usedNumber.getDays(),
				usedNumber.getMinutes().orElse(null), usedNumber.getStowageDays().orElse(null));
		return GetTempAnnualLeaveMngsFromUsedNumberService.tempAnnualLeaveMngs(employeeId, usedNumberDom).stream()
				.map(x -> convert(x)).collect(Collectors.toList());
	}

	private TempAnnualLeaveMngsExport convert(TempAnnualLeaveMngs domain) {

		return new TempAnnualLeaveMngsExport(domain.getRemainManaID(), domain.getSID(), domain.getYmd(),
				EnumAdaptor.valueOf(domain.getCreatorAtr().value, CreateAtrExport.class),
				EnumAdaptor.valueOf(domain.getRemainType().value, RemainType.class),
				domain.getWorkTypeCode().v(),
				new LeaveUsedNumberExport(domain.getUsedNumber().getDays().v(),
						domain.getUsedNumber().getMinutes().map(x -> x.v()),
						domain.getUsedNumber().getStowageDays().map(x -> x.v()),
						domain.getUsedNumber().getLeaveOverLimitNumber()
								.map(x -> new LeaveOverNumberExport(x.numberOverDays.v(), x.timeOver.map(y -> y.v())))),
				domain.getAppTimeType().map(x -> EnumAdaptor.valueOf(x.value, AppTimeTypeExport.class)));
	}
}
