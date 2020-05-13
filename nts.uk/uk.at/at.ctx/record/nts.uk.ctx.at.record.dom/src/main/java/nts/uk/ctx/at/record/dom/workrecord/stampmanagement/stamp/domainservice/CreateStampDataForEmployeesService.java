package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
/**
 * DS : 社員の打刻データを作成する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.社員の打刻データを作成する
 * @author tutk
 *
 */
public class CreateStampDataForEmployeesService {
	/**
	 * [1] 作成する
	 * 
	 * @param require
	 * @param employeeId
	 *            	社員ID
	 * @param datetime
	 *            打刻日時
	 * @param relieve
	 *            打刻する方法
	 * @param buttonType
	 *            ボタン種類
	 * @param refActualResults
	 *            実績への反映内容
	 * @param positionInfo
	 *            打刻位置情報
	 * @param empInfoTerCode
	 *            就業情報端末コード
	 * @return
	 */
	public static StampDataReflectResult create(Require require, String employeeId, GeneralDateTime datetime,
			Relieve relieve, ButtonType buttonType, Optional<RefectActualResult> refActualResults,
			Optional<GeoCoordinate> positionInfo, Optional<EmpInfoTerminalCode> empInfoTerCode) {
		StampNumber stampNumber = getCardNumber(require, employeeId);
		boolean stampAtr = buttonType.checkStampType();
		
		StampRecord stampRecord = new StampRecord(stampNumber, datetime, stampAtr, buttonType.getReservationArt(), empInfoTerCode); 
		if(!stampAtr) {
			return StampDataReflectProcessService.reflect(require, Optional.of(employeeId), stampRecord, Optional.empty());
		}
		Stamp stamp = createStampDataInfo(stampNumber, datetime, stampAtr, relieve, buttonType.getStampType().get(), refActualResults.get(), positionInfo);
		
		return StampDataReflectProcessService.reflect(require, Optional.of(employeeId), stampRecord, Optional.of(stamp));
	}

	/**
	 * [prv-1] 打刻カード番号を取得する
	 * 
	 * @param require
	 * @param employeeId
	 *            社員ID
	 * @return 打刻カード番号
	 */
	private static StampNumber getCardNumber(Require require, String employeeId) {
		List<StampCard> listStampCard = require.getListStampCard(employeeId);
		if (listStampCard.isEmpty()) {
			throw new BusinessException("Msg_433");
		}
		listStampCard = listStampCard.stream().sorted((x, y) -> y.getRegisterDate().compareTo(x.getRegisterDate()))
				.collect(Collectors.toList());
		return listStampCard.get(0).getStampNumber();
	}

	/**
	 * [prv-2] 打刻データ情報を作成する
	 * @param stampNumber 打刻カード番号
	 * @param datetime 打刻日時
	 * @param stampAtr 打刻区分
	 * @param relieve 打刻する方法		
	 * @param type 打刻種類	
	 * @param refActualResults 実績への反映内容	
	 * @param positionInfo 打刻位置情報	
	 * @return 	打刻
	 */
	private static Stamp createStampDataInfo(StampNumber stampNumber, GeneralDateTime datetime,
			boolean stampAtr,Relieve relieve, StampType type, RefectActualResult refActualResults,
			Optional<GeoCoordinate> positionInfo) {
		StampLocationInfor stampLocationInfor = null;
		if(positionInfo.isPresent()) {
			stampLocationInfor = new StampLocationInfor(true, positionInfo.get()) ;
		}
		return new Stamp(
				stampNumber, 
				datetime, 
				relieve, 
				type, refActualResults, stampLocationInfor);
	}

	public static interface Require extends StampDataReflectProcessService.Require {
		/**
		 * [R-1] 打刻カード番号を取得する - StampCardRepository
		 * 
		 * @param sid
		 * @return
		 */
		List<StampCard> getListStampCard(String sid);
	}
}
