package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.InforFormerRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.OccurrenceUseDetail;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

@Stateless
public class TempRemainCreateEachDataImpl implements TempRemainCreateEachData{

	@Override
	public DailyInterimRemainMngData createInterimAnnualHoliday(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(!occUseDetail.isPresent()) {
			return mngData;
		}
		OccurrenceUseDetail useDetail = occUseDetail.get();
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain ramainData = new InterimRemain(mngId, 
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemain().get().getCreateData(), 
				RemainType.ANNUAL,
				RemainAtr.SINGLE);
		mngData.getRecAbsData().add(ramainData);
		TmpAnnualHolidayMng annualMng = new TmpAnnualHolidayMng(mngId, 
				inforData.getWorkTypeRemain().get().getWorkTypeCode(), 
				new UseDay(useDetail.getDays()));
		mngData.setAnnualHolidayData(Optional.of(annualMng));
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimReserveHoliday(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(!occUseDetail.isPresent()) {
			return mngData;
		}
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain ramainData = new InterimRemain(mngId, 
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemain().get().getCreateData(), 
				RemainType.FUNDINGANNUAL,
				RemainAtr.SINGLE);
		mngData.getRecAbsData().add(ramainData);
		TmpResereLeaveMng resereData = new TmpResereLeaveMng(mngId, new UseDay(occUseDetail.get().getDays()));
		mngData.setResereData(Optional.of(resereData));
		return mngData;
	}
	@Override
	public DailyInterimRemainMngData createInterimAbsData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(occUseDetail.isPresent()) {
			String mngId = IdentifierUtil.randomUniqueId();
			InterimRemain mngDataRemain = new InterimRemain(mngId,
					inforData.getSid(),
					inforData.getYmd(),
					inforData.getWorkTypeRemain().get().getCreateData(),
					RemainType.PAUSE,
					RemainAtr.SINGLE);
			InterimAbsMng absData = new InterimAbsMng(mngId,
					new RequiredDay(occUseDetail.get().getDays()),
					new UnOffsetDay(occUseDetail.get().getDays()));
			mngData.setInterimAbsData(Optional.of(absData));
			mngData.getRecAbsData().add(mngDataRemain);
		}
		
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimDayOffData(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(occUseDetail.isPresent()) {
			if(!occUseDetail.get().isUseAtr()) {
				String mngId = IdentifierUtil.randomUniqueId();
				InterimRemain mngDataRemain = new InterimRemain(mngId, inforData.getSid(), inforData.getYmd(), inforData.getWorkTypeRemain().get().getCreateData(), RemainType.SUBHOLIDAY, RemainAtr.SINGLE);
				InterimDayOffMng dayoffMng = new InterimDayOffMng(mngId, 
						new RequiredTime(0),
						new RequiredDay(occUseDetail.get().getDays()),
						new UnOffsetTime(0), 
						new UnOffsetDay(occUseDetail.get().getDays()));
				mngData.setDayOffData(Optional.of(dayoffMng));
				mngData.getRecAbsData().add(mngDataRemain);
			} else {
				//TODO 2018.06.20 chua lam trong giai doan nay
			}
		}
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimRecData(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		// 残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(!occUseDetail.isPresent()) {
			return mngData;
		}
		//アルゴリズム「振休使用期限日の算出」を実行する
		
		return mngData;
	}

}
