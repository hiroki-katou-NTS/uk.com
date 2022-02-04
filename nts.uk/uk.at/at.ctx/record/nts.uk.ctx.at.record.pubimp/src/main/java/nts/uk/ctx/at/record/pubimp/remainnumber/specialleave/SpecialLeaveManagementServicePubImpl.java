package nts.uk.ctx.at.record.pubimp.remainnumber.specialleave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.GrantRemainRegisterTypeExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveExpirationStatusExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveGrantNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveGrantRemainingDataExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveNumberInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveOverNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveRemainingNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveUsedNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.ComplileInPeriodOfSpecialLeavePubParam;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.InterimSpecialHolidayMngPubParam;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.SpecialLeaveManagementServicePub;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.InPeriodOfSpecialLeaveResultInforExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveGrantRemainingDataExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveInfoExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveRemainingDetailExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveRemainingExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveRemainingNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveRemainingNumberInfoExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveUndigestNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveUseNumberExport;
import nts.uk.ctx.at.record.pub.remainnumber.specialleave.export.SpecialLeaveUsedInfoExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveOverNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveUndigestNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingDetail;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUsedInfo;

/**
 * 特別休暇残数処理
 * @author masaaki_jinno
 *
 */
public class SpecialLeaveManagementServicePubImpl implements SpecialLeaveManagementServicePub{

	@Inject
	private RecordDomRequireService requireService;

	/**
	 * 期間中の年休残数を取得
	 */
	@Override
	public InPeriodOfSpecialLeaveResultInforExport complileInPeriodOfSpecialLeave(
			ComplileInPeriodOfSpecialLeavePubParam pubParam) {

		// パラメータをドメインへ変換
		ComplileInPeriodOfSpecialLeaveParam param = toDomain(pubParam);

		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		// 期間中の年休残数を取得
		InPeriodOfSpecialLeaveResultInfor aggrResult
				= SpecialLeaveManagementService.complileInPeriodOfSpecialLeave(
						require, cacheCarrier, param);

		// Exportへ変換
		InPeriodOfSpecialLeaveResultInforExport result = fromDomain( aggrResult );

		return result;
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public InPeriodOfSpecialLeaveResultInforExport  fromDomain(InPeriodOfSpecialLeaveResultInfor domain) {

		// 付与時点
		List<SpecialLeaveInfoExport> asOfGrantList = new ArrayList<>();
		if ( domain.getAsOfGrant().isPresent() ) {
			domain.getAsOfGrant().get().forEach(action->asOfGrantList.add(fromDomain(action)));
		}

		// 消滅時点
		List<SpecialLeaveInfoExport> lapsedList = new ArrayList<>();
		if ( domain.getLapsed().isPresent() ) {
			domain.getLapsed().get().forEach(action->lapsedList.add(fromDomain(action)));
		}

		// エラー
		List<Integer> specialLeaveErrors = new ArrayList<>();
		domain.getSpecialLeaveErrors().forEach(action->specialLeaveErrors.add(new Integer(action.value)));

		return new InPeriodOfSpecialLeaveResultInforExport(
					fromDomain(domain.getAsOfPeriodEnd()),
					fromDomain(domain.getAsOfStartNextDayOfPeriodEnd()),
					asOfGrantList,
					lapsedList,
					specialLeaveErrors
					);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveInfoExport  fromDomain(SpecialLeaveInfo domain) {
		return new SpecialLeaveInfoExport(
				domain.getYmd(),
				fromDomain(domain.getRemainingNumber()),
				domain.getGrantRemainingDataList().stream().map(mapper->fromDomain(mapper)).collect(Collectors.toList())
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveGrantRemainingDataExport  fromDomain(SpecialLeaveGrantRemainingData domain) {
		return new SpecialLeaveGrantRemainingDataExport(
				fromDomain( (LeaveGrantRemainingData)domain ),
				domain.getSpecialLeaveCode());
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public LeaveGrantRemainingDataExport  fromDomain(LeaveGrantRemainingData domain) {
		return new LeaveGrantRemainingDataExport(
				domain.getEmployeeId(),
				domain.getGrantDate(),
				domain.getDeadline(),
				EnumAdaptor.valueOf(domain.getExpirationStatus().value, LeaveExpirationStatusExport.class),
				EnumAdaptor.valueOf(domain.getRegisterType().value, GrantRemainRegisterTypeExport.class),
				fromDomain(domain.getDetails())
				);

	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public LeaveNumberInfoExport  fromDomain(LeaveNumberInfo domain) {
		return new LeaveNumberInfoExport(
				fromDomain(domain.getGrantNumber()),
				fromDomain(domain.getUsedNumber()),
				fromDomain(domain.getRemainingNumber()),
				domain.getUsedPercent().v()
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public LeaveOverNumberExport  fromDomain(LeaveOverNumber domain) {
		return new LeaveOverNumberExport(
				domain.numberOverDays.v(),
				domain.timeOver.map(mapper->mapper.v())
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public LeaveRemainingNumberExport  fromDomain(LeaveRemainingNumber domain) {
		return new LeaveRemainingNumberExport(
				domain.getDays().v(),
				domain.getMinutes().map(mapper->mapper.v())
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public LeaveUsedNumberExport  fromDomain(LeaveUsedNumber domain) {
		return new LeaveUsedNumberExport(
				domain.getDays().v(),
				domain.getMinutes().map(mapper->mapper.v()),
				domain.getStowageDays().map(mapper->new Double(mapper.v())),
				domain.getLeaveOverLimitNumber()
					.map(mapper->new LeaveOverNumberExport(mapper.numberOverDays.v(), mapper.timeOver.map(mapper2->mapper2.v())))
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public LeaveGrantNumberExport  fromDomain(LeaveGrantNumber domain) {
		return new LeaveGrantNumberExport(
				domain.getDays().v(),
				domain.getMinutes().map(mapper->mapper.v())
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveRemainingExport  fromDomain(SpecialLeaveRemaining domain) {
		return new SpecialLeaveRemainingExport(
				fromDomain(domain.getSpecialLeaveNoMinus()),
				fromDomain(domain.getSpecialLeaveWithMinus()),
				domain.getSpecialLeaveUndigestNumber().map(mapper->fromDomain(mapper))
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveExport  fromDomain(SpecialLeave domain) {
		return new SpecialLeaveExport(
				fromDomain(domain.getUsedNumberInfo()),
				fromDomain(domain.getRemainingNumberInfo()));
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveRemainingNumberInfoExport  fromDomain(SpecialLeaveRemainingNumberInfo domain) {
		return new SpecialLeaveRemainingNumberInfoExport(
				fromDomain(domain.getRemainingNumber()),
				fromDomain(domain.getRemainingNumberBeforeGrant()),
				domain.getRemainingNumberAfterGrantOpt().map(mapper->fromDomain(mapper))
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveRemainingNumberExport  fromDomain(SpecialLeaveRemainingNumber domain) {
		return new SpecialLeaveRemainingNumberExport(
				domain.getDayNumberOfRemain().v(),
				domain.getTimeOfRemain().map(mapper->new Integer(mapper.v())),
				domain.getDetails().stream().map(mapper->fromDomain(mapper)).collect(Collectors.toList())
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveRemainingDetailExport  fromDomain(SpecialLeaveRemainingDetail domain) {
		return new SpecialLeaveRemainingDetailExport(
				domain.getGrantDate(),
				domain.getDays().v(),
				domain.getTime().map(mapper->new Integer(mapper.v()))
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveUndigestNumberExport  fromDomain(SpecialLeaveUndigestNumber domain) {
		return new SpecialLeaveUndigestNumberExport(
				domain.getDays().v(),
				domain.getMinutes().map(mapper->mapper.v())
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveUsedInfoExport  fromDomain(SpecialLeaveUsedInfo domain) {
		return new SpecialLeaveUsedInfoExport(
				fromDomain( domain.getUsedNumber() ),
				fromDomain( domain.getUsedNumberBeforeGrant() ),
				domain.getSpecialLeaveUsedTimes().v(),
				domain.getSpecialLeaveUsedDayTimes().v(),
				domain.getUsedNumberAfterGrantOpt().map(mapper->fromDomain(mapper))
				);
	}

	/**
	 * ドメインからExportへ変換
	 * @param domain
	 * @return
	 */
	static public SpecialLeaveUseNumberExport  fromDomain(SpecialLeaveUseNumber domain) {
		return new SpecialLeaveUseNumberExport(
				domain.getUseDays().map(mapper->new Double(mapper.v())),
				domain.getUseTimes().map(useTimes->new Integer(useTimes.getUseTimes().v()))
				);
	}

	/**
	 * ドメインへ変換
	 * @param param
	 * @return
	 */
	static public ComplileInPeriodOfSpecialLeaveParam toDomain(ComplileInPeriodOfSpecialLeavePubParam param) {
		return new ComplileInPeriodOfSpecialLeaveParam(
					param.getCid(),
					param.getSid(),
					param.getComplileDate(),
					param.isMode(),
					param.getBaseDate(),
					param.getSpecialLeaveCode(),
					param.isMngAtr(),
					param.isOverwriteFlg(),
					param.getInterimSpecialData().stream().map(action->toDomain(param.getSid(),param.getBaseDate(),action)).collect(Collectors.toList()),
					param.getIsOverWritePeriod()
					);
	}

	/**
	 * ドメインへ変換
	 * @param generalDate
	 * @param sId
	 * @param param
	 * @return
	 */
	static public InterimSpecialHolidayMng toDomain(String sId, GeneralDate generalDate, InterimSpecialHolidayMngPubParam param) {
		return new InterimSpecialHolidayMng(
				param.getSpecialHolidayId(),
				sId,
				generalDate,
				CreateAtr.SCHEDULE,
				RemainType.SPECIAL,
				param.getSpecialHolidayCode(),
				EnumAdaptor.valueOf(param.getMngAtr(), ManagermentAtr.class),
				param.getUseTimes().map(mapper->new UseTime(mapper.v())),
				param.getUseDays().map(mapper->new UseDay(mapper.v())),
				Optional.empty());
	}

}
