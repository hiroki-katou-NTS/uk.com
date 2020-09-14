package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthlyForSyain;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.AggrSettingMonthlyForSyainRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrMethodOfFlex36AgreementTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ExcessOutsideTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ExcessOutsideTimeTargetSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.LegalAggrSetOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.SettingOfFlex36AgreementTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.AggregateTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.CalcSettingOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatHolidayWorkTimeOfLessThanCriteriaPerWeek;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatOverTimeOfLessThanCriteriaPerDay;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetRegAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggrPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaFlxAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgSetlPK;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaRegExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.flex.KrcstMonsetFlxAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgExot;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetIrgSetl;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular.KrcstMonsetRegExot;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.AggregateSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.SettlePeriodMonths;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * リポジトリ実装：社員月別実績集計設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaAggrSettingMonthlyForSyain extends JpaRepository implements AggrSettingMonthlyForSyainRepository {

	/** 検索 */
	@Override
	public Optional<AggrSettingMonthlyForSyain> find(String companyId, String employeeId) {
		return this.queryProxy()
				.find(new KrcstMonsetSyaRegAggrPK(companyId, employeeId), KrcstMonsetSyaRegAggr.class)
				.map(c -> toDomain(c));
	}
	
	/**
	 * エンティティ→ドメイン
	 * @param entity エンティティ：社員月別実績集計設定
	 * @return ドメイン：社員月別実績集計設定
	 */
	private static AggrSettingMonthlyForSyain toDomain(KrcstMonsetSyaRegAggr entity){
		
		// 残業枠・休出枠区分の再構成
		Map<Integer, Integer> treatOverTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatOverTime01);}
			{put(2, entity.setValue.treatOverTime02);}
			{put(3, entity.setValue.treatOverTime03);}
			{put(4, entity.setValue.treatOverTime04);}
			{put(5, entity.setValue.treatOverTime05);}
			{put(6, entity.setValue.treatOverTime06);}
			{put(7, entity.setValue.treatOverTime07);}
			{put(8, entity.setValue.treatOverTime08);}
			{put(9, entity.setValue.treatOverTime09);}
			{put(10, entity.setValue.treatOverTime10);}
		};
		Map<Integer, Integer> treatHolidayWorkTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatHolidayWorkTime01);}
			{put(2, entity.setValue.treatHolidayWorkTime02);}
			{put(3, entity.setValue.treatHolidayWorkTime03);}
			{put(4, entity.setValue.treatHolidayWorkTime04);}
			{put(5, entity.setValue.treatHolidayWorkTime05);}
			{put(6, entity.setValue.treatHolidayWorkTime06);}
			{put(7, entity.setValue.treatHolidayWorkTime07);}
			{put(8, entity.setValue.treatHolidayWorkTime08);}
			{put(9, entity.setValue.treatHolidayWorkTime09);}
			{put(10, entity.setValue.treatHolidayWorkTime10);}
		};
		
		// 不変条件：　時間外超過設定．割増を求める=trueの時、集計時間設定．割増を求めるは、常にtrue
		if (entity.krcstMonsetSyaRegExot != null){
			if (entity.krcstMonsetSyaRegExot.setValue.askPremium != 0) {
				entity.setValue.askPremium = 1;
			}
		}
		
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay =
				TreatOverTimeOfLessThanCriteriaPerDay.of(treatOverTimes);
		
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
				TreatHolidayWorkTimeOfLessThanCriteriaPerWeek.of(treatHolidayWorkTimes);
		
		// 通常勤務の法定内集計設定
		val legalAggrSetOfReg = LegalAggrSetOfReg.of(
				AggregateTimeSet.of(
						(entity.setValue.askPremium != 0),
						treatOverTimeOfLessThanCriteriaPerDay,
						treatHolidayWorkTimeOfLessThanCriteriaPerWeek),
				toDomainExcessOutsideTimeSetOfReg(entity)); 
		
		// 会社月別実績集計設定
		val domain = AggrSettingMonthlyForSyain.of(
				entity.PK.companyId,
				entity.PK.employeeId,
				legalAggrSetOfReg,
				toDomainLegalAggrSetOfIrg(entity),
				toDomainAggrSettingMonthlyOfFlx(entity));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（通常勤務の時間外超過設定）
	 * @param parentEntity エンティティ：社員月別実績集計設定
	 * @return ドメイン：通常勤務の時間外超過設定
	 */
	private static ExcessOutsideTimeSet toDomainExcessOutsideTimeSetOfReg(KrcstMonsetSyaRegAggr parentEntity){
		
		val entity = parentEntity.krcstMonsetSyaRegExot;
		if (entity == null) return new ExcessOutsideTimeSet();
		
		// 残業枠・休出枠区分の再構成
		Map<Integer, Integer> treatOverTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatOverTime01);}
			{put(2, entity.setValue.treatOverTime02);}
			{put(3, entity.setValue.treatOverTime03);}
			{put(4, entity.setValue.treatOverTime04);}
			{put(5, entity.setValue.treatOverTime05);}
			{put(6, entity.setValue.treatOverTime06);}
			{put(7, entity.setValue.treatOverTime07);}
			{put(8, entity.setValue.treatOverTime08);}
			{put(9, entity.setValue.treatOverTime09);}
			{put(10, entity.setValue.treatOverTime10);}
		};
		Map<Integer, Integer> treatHolidayWorkTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatHolidayWorkTime01);}
			{put(2, entity.setValue.treatHolidayWorkTime02);}
			{put(3, entity.setValue.treatHolidayWorkTime03);}
			{put(4, entity.setValue.treatHolidayWorkTime04);}
			{put(5, entity.setValue.treatHolidayWorkTime05);}
			{put(6, entity.setValue.treatHolidayWorkTime06);}
			{put(7, entity.setValue.treatHolidayWorkTime07);}
			{put(8, entity.setValue.treatHolidayWorkTime08);}
			{put(9, entity.setValue.treatHolidayWorkTime09);}
			{put(10, entity.setValue.treatHolidayWorkTime10);}
		};
		
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay =
				TreatOverTimeOfLessThanCriteriaPerDay.of(treatOverTimes);
		
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
				TreatHolidayWorkTimeOfLessThanCriteriaPerWeek.of(treatHolidayWorkTimes);
		
		val domain = ExcessOutsideTimeSet.of(
				(entity.setValue.askPremium != 0),
				(entity.setValue.exceptLegalHolidayWorkTime != 0),
				treatOverTimeOfLessThanCriteriaPerDay,
				treatHolidayWorkTimeOfLessThanCriteriaPerWeek);
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（変形労働時間勤務の法定内集計設定）
	 * @param parentEntity エンティティ：社員月別実績集計設定
	 * @return ドメイン：変形労働時間勤務の法定内集計設定
	 */
	private static LegalAggrSetOfIrg toDomainLegalAggrSetOfIrg(KrcstMonsetSyaRegAggr parentEntity){
		
		val entity = parentEntity.krcstMonsetSyaIrgAggr;
		if (entity == null) return new LegalAggrSetOfIrg();
		
		// 残業枠・休出枠区分の再構成
		Map<Integer, Integer> treatOverTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatOverTime01);}
			{put(2, entity.setValue.treatOverTime02);}
			{put(3, entity.setValue.treatOverTime03);}
			{put(4, entity.setValue.treatOverTime04);}
			{put(5, entity.setValue.treatOverTime05);}
			{put(6, entity.setValue.treatOverTime06);}
			{put(7, entity.setValue.treatOverTime07);}
			{put(8, entity.setValue.treatOverTime08);}
			{put(9, entity.setValue.treatOverTime09);}
			{put(10, entity.setValue.treatOverTime10);}
		};
		Map<Integer, Integer> treatHolidayWorkTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatHolidayWorkTime01);}
			{put(2, entity.setValue.treatHolidayWorkTime02);}
			{put(3, entity.setValue.treatHolidayWorkTime03);}
			{put(4, entity.setValue.treatHolidayWorkTime04);}
			{put(5, entity.setValue.treatHolidayWorkTime05);}
			{put(6, entity.setValue.treatHolidayWorkTime06);}
			{put(7, entity.setValue.treatHolidayWorkTime07);}
			{put(8, entity.setValue.treatHolidayWorkTime08);}
			{put(9, entity.setValue.treatHolidayWorkTime09);}
			{put(10, entity.setValue.treatHolidayWorkTime10);}
		};
		
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay =
				TreatOverTimeOfLessThanCriteriaPerDay.of(treatOverTimes);
		
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
				TreatHolidayWorkTimeOfLessThanCriteriaPerWeek.of(treatHolidayWorkTimes);
		
		// 変形労働の精算期間
		val settlementPeriodOfIrg = SettlementPeriodOfIrg.of(
				parentEntity.krcstMonsetSyaIrgSetls.stream()
					.map(c -> toDomainSettlementPeriod(c)).collect(Collectors.toList()));
		
		// 変形労働時間勤務の法定内集計設定
		// ※　「割増を求める」は、変形労働時間の時は、常にtrue。
		val domain = LegalAggrSetOfIrg.of(
				AggregateTimeSet.of(
						true,
						treatOverTimeOfLessThanCriteriaPerDay,
						treatHolidayWorkTimeOfLessThanCriteriaPerWeek),
				toDomainExcessOutsideTimeSetOfIrg(parentEntity),
				CalcSettingOfIrregular.of(
						(entity.setValue.toOverTimeWithinIrregularCriteria != 0),
						(entity.setValue.toWorkTimeOutsideCriteria != 0)),
				settlementPeriodOfIrg); 
		
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（変形労働時間勤務の時間外超過設定）
	 * @param parentEntity エンティティ：社員月別実績集計設定
	 * @return ドメイン：変形労働時間勤務の時間外超過設定
	 */
	private static ExcessOutsideTimeSet toDomainExcessOutsideTimeSetOfIrg(KrcstMonsetSyaRegAggr parentEntity){
		
		val entity = parentEntity.krcstMonsetSyaIrgExot;
		if (entity == null) return new ExcessOutsideTimeSet();
		
		// 残業枠・休出枠区分の再構成
		Map<Integer, Integer> treatOverTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatOverTime01);}
			{put(2, entity.setValue.treatOverTime02);}
			{put(3, entity.setValue.treatOverTime03);}
			{put(4, entity.setValue.treatOverTime04);}
			{put(5, entity.setValue.treatOverTime05);}
			{put(6, entity.setValue.treatOverTime06);}
			{put(7, entity.setValue.treatOverTime07);}
			{put(8, entity.setValue.treatOverTime08);}
			{put(9, entity.setValue.treatOverTime09);}
			{put(10, entity.setValue.treatOverTime10);}
		};
		Map<Integer, Integer> treatHolidayWorkTimes = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.setValue.treatHolidayWorkTime01);}
			{put(2, entity.setValue.treatHolidayWorkTime02);}
			{put(3, entity.setValue.treatHolidayWorkTime03);}
			{put(4, entity.setValue.treatHolidayWorkTime04);}
			{put(5, entity.setValue.treatHolidayWorkTime05);}
			{put(6, entity.setValue.treatHolidayWorkTime06);}
			{put(7, entity.setValue.treatHolidayWorkTime07);}
			{put(8, entity.setValue.treatHolidayWorkTime08);}
			{put(9, entity.setValue.treatHolidayWorkTime09);}
			{put(10, entity.setValue.treatHolidayWorkTime10);}
		};
		
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay =
				TreatOverTimeOfLessThanCriteriaPerDay.of(treatOverTimes);
		
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
				TreatHolidayWorkTimeOfLessThanCriteriaPerWeek.of(treatHolidayWorkTimes);
		
		// ※　「割増を求める」は、変形労働時間の時は、常にtrue。
		val domain = ExcessOutsideTimeSet.of(
				true,
				(entity.setValue.exceptLegalHolidayWorkTime != 0),
				treatOverTimeOfLessThanCriteriaPerDay,
				treatHolidayWorkTimeOfLessThanCriteriaPerWeek);
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（変形労働の精算期間）
	 * @param entity エンティティ：社員の変形労働の精算期間
	 * @return ドメイン：変形労働の精算期間
	 */
	private static SettlementPeriod toDomainSettlementPeriod(KrcstMonsetSyaIrgSetl entity){
		
		return new SettlementPeriod(new Month(entity.PK.startMonth), new Month(entity.setValue.endMonth));
	}
	
	/**
	 * エンティティ→ドメイン　（フレックス時間勤務の月の集計設定）
	 * @param parentEntity エンティティ：社員月別実績集計設定
	 * @return ドメイン：フレックス時間勤務の月の集計設定
	 */
	private static AggrSettingMonthlyOfFlx toDomainAggrSettingMonthlyOfFlx(KrcstMonsetSyaRegAggr parentEntity){
		
		val entity = parentEntity.krcstMonsetSyaFlxAggr;
		if (entity == null) return new AggrSettingMonthlyOfFlx();
		
		val domain = AggrSettingMonthlyOfFlx.of(
				EnumAdaptor.valueOf(entity.setValue.aggregateMethod, FlexAggregateMethod.class),
				(entity.setValue.includeOverTime != 0),
				ShortageFlexSetting.of(
						EnumAdaptor.valueOf(entity.setValue.carryforwardSet, CarryforwardSetInShortageFlex.class),
						EnumAdaptor.valueOf(entity.setValue.settlePeriod, SettlePeriod.class),
						new Month(entity.setValue.startMonth),
						EnumAdaptor.valueOf(entity.setValue.settlePeriodMon, SettlePeriodMonths.class)),
				LegalAggrSetOfFlx.of(
						AggregateTimeSetting.of(
								EnumAdaptor.valueOf(entity.setValue.aggregateSet, AggregateSetting.class)),
						ExcessOutsideTimeSetting.of(
								EnumAdaptor.valueOf(entity.setValue.excessOutsideTimeTargetSet, ExcessOutsideTimeTargetSetting.class))),
				SettingOfFlex36AgreementTime.of(
						EnumAdaptor.valueOf(entity.setValue.aggregateMethodOf36AgreementTime, AggrMethodOfFlex36AgreementTime.class)));
		return domain;
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AggrSettingMonthlyForSyain domain){
		
		// キー
		val key = new KrcstMonsetSyaRegAggrPK(domain.getCompanyId(), domain.getEmployeeId());
		
		// 通常勤務の法定内集計設定
		val legalAggrSet = domain.getRegularWork();
		// 集計時間設定
		val aggregateTimeSet = legalAggrSet.getAggregateTimeSet();
		// 1日の基準時間未満の残業時間の扱い
		TreatOverTimeOfLessThanCriteriaPerDay treatOverTimeOfLessThanCriteriaPerDay =
				aggregateTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		// 1週間の基準時間未満の休日出勤時間の扱い
		TreatHolidayWorkTimeOfLessThanCriteriaPerWeek treatHolidayWorkTimeOfLessThanCriteriaPerWeek =
				aggregateTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		
		// 登録・更新を判断　および　キー値設定
		boolean isNeedPersist = false;
		KrcstMonsetSyaRegAggr entity = this.getEntityManager().find(KrcstMonsetSyaRegAggr.class, key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcstMonsetSyaRegAggr();
			entity.PK = key;
			entity.setValue = new KrcstMonsetRegAggr();
		}
		
		// 登録・更新値の設定
		entity.setValue.askPremium = (aggregateTimeSet.isAskPremium() ? 1 : 0);
		
		for (int atrTreatOverTime = 1; atrTreatOverTime <= 2; atrTreatOverTime++){
			List<OverTimeFrameNo> overTimeFrameNoList =
					treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames();
			if (atrTreatOverTime == 2){
				overTimeFrameNoList = treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames();
			}
			for (val overTimeFrameNo : overTimeFrameNoList){
				switch(overTimeFrameNo.v().intValue()){
				case 1:
					entity.setValue.treatOverTime01 = atrTreatOverTime;
					break;
				case 2:
					entity.setValue.treatOverTime02 = atrTreatOverTime;
					break;
				case 3:
					entity.setValue.treatOverTime03 = atrTreatOverTime;
					break;
				case 4:
					entity.setValue.treatOverTime04 = atrTreatOverTime;
					break;
				case 5:
					entity.setValue.treatOverTime05 = atrTreatOverTime;
					break;
				case 6:
					entity.setValue.treatOverTime06 = atrTreatOverTime;
					break;
				case 7:
					entity.setValue.treatOverTime07 = atrTreatOverTime;
					break;
				case 8:
					entity.setValue.treatOverTime08 = atrTreatOverTime;
					break;
				case 9:
					entity.setValue.treatOverTime09 = atrTreatOverTime;
					break;
				case 10:
					entity.setValue.treatOverTime10 = atrTreatOverTime;
					break;
				}
			}
		}
		
		int atrAutoExcludeHolidayWork = 1;
		for (val autoExcludeHolidayWorkFrame : treatHolidayWorkTimeOfLessThanCriteriaPerWeek.getAutoExcludeHolidayWorkFrames()){
			switch(autoExcludeHolidayWorkFrame.v().intValue()){
			case 1:
				entity.setValue.treatHolidayWorkTime01 = atrAutoExcludeHolidayWork;
				break;
			case 2:
				entity.setValue.treatHolidayWorkTime02 = atrAutoExcludeHolidayWork;
				break;
			case 3:
				entity.setValue.treatHolidayWorkTime03 = atrAutoExcludeHolidayWork;
				break;
			case 4:
				entity.setValue.treatHolidayWorkTime04 = atrAutoExcludeHolidayWork;
				break;
			case 5:
				entity.setValue.treatHolidayWorkTime05 = atrAutoExcludeHolidayWork;
				break;
			case 6:
				entity.setValue.treatHolidayWorkTime06 = atrAutoExcludeHolidayWork;
				break;
			case 7:
				entity.setValue.treatHolidayWorkTime07 = atrAutoExcludeHolidayWork;
				break;
			case 8:
				entity.setValue.treatHolidayWorkTime08 = atrAutoExcludeHolidayWork;
				break;
			case 9:
				entity.setValue.treatHolidayWorkTime09 = atrAutoExcludeHolidayWork;
				break;
			case 10:
				entity.setValue.treatHolidayWorkTime10 = atrAutoExcludeHolidayWork;
				break;
			}
		}
		
		// 通常勤務：時間外超過設定
		val regExcessOutsideTimeSet = legalAggrSet.getExcessOutsideTimeSet();
		if (entity.krcstMonsetSyaRegExot == null){
			entity.krcstMonsetSyaRegExot = new KrcstMonsetSyaRegExot();
			entity.krcstMonsetSyaRegExot.PK = key;
			entity.krcstMonsetSyaRegExot.setValue = new KrcstMonsetRegExot();
		}
		val entityRegExot = entity.krcstMonsetSyaRegExot;
		entityRegExot.setValue.askPremium = (regExcessOutsideTimeSet.isAskPremium() ? 1 : 0);
		entityRegExot.setValue.exceptLegalHolidayWorkTime =
				(regExcessOutsideTimeSet.isAutoExcludeHolidayWorkTimeFromExcessOutsideWorkTime() ? 1 : 0);

		treatOverTimeOfLessThanCriteriaPerDay = regExcessOutsideTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		treatHolidayWorkTimeOfLessThanCriteriaPerWeek = regExcessOutsideTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
				
		for (int atrTreatOverTime = 1; atrTreatOverTime <= 2; atrTreatOverTime++){
			List<OverTimeFrameNo> overTimeFrameNoList =
					treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames();
			if (atrTreatOverTime == 2){
				overTimeFrameNoList = treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames();
			}
			for (val overTimeFrameNo : overTimeFrameNoList){
				switch(overTimeFrameNo.v().intValue()){
				case 1:
					entityRegExot.setValue.treatOverTime01 = atrTreatOverTime;
					break;
				case 2:
					entityRegExot.setValue.treatOverTime02 = atrTreatOverTime;
					break;
				case 3:
					entityRegExot.setValue.treatOverTime03 = atrTreatOverTime;
					break;
				case 4:
					entityRegExot.setValue.treatOverTime04 = atrTreatOverTime;
					break;
				case 5:
					entityRegExot.setValue.treatOverTime05 = atrTreatOverTime;
					break;
				case 6:
					entityRegExot.setValue.treatOverTime06 = atrTreatOverTime;
					break;
				case 7:
					entityRegExot.setValue.treatOverTime07 = atrTreatOverTime;
					break;
				case 8:
					entityRegExot.setValue.treatOverTime08 = atrTreatOverTime;
					break;
				case 9:
					entityRegExot.setValue.treatOverTime09 = atrTreatOverTime;
					break;
				case 10:
					entityRegExot.setValue.treatOverTime10 = atrTreatOverTime;
					break;
				}
			}
		}
		
		atrAutoExcludeHolidayWork = 1;
		for (val autoExcludeHolidayWorkFrame : treatHolidayWorkTimeOfLessThanCriteriaPerWeek.getAutoExcludeHolidayWorkFrames()){
			switch(autoExcludeHolidayWorkFrame.v().intValue()){
			case 1:
				entityRegExot.setValue.treatHolidayWorkTime01 = atrAutoExcludeHolidayWork;
				break;
			case 2:
				entityRegExot.setValue.treatHolidayWorkTime02 = atrAutoExcludeHolidayWork;
				break;
			case 3:
				entityRegExot.setValue.treatHolidayWorkTime03 = atrAutoExcludeHolidayWork;
				break;
			case 4:
				entityRegExot.setValue.treatHolidayWorkTime04 = atrAutoExcludeHolidayWork;
				break;
			case 5:
				entityRegExot.setValue.treatHolidayWorkTime05 = atrAutoExcludeHolidayWork;
				break;
			case 6:
				entityRegExot.setValue.treatHolidayWorkTime06 = atrAutoExcludeHolidayWork;
				break;
			case 7:
				entityRegExot.setValue.treatHolidayWorkTime07 = atrAutoExcludeHolidayWork;
				break;
			case 8:
				entityRegExot.setValue.treatHolidayWorkTime08 = atrAutoExcludeHolidayWork;
				break;
			case 9:
				entityRegExot.setValue.treatHolidayWorkTime09 = atrAutoExcludeHolidayWork;
				break;
			case 10:
				entityRegExot.setValue.treatHolidayWorkTime10 = atrAutoExcludeHolidayWork;
				break;
			}
		}

		// 変形労働時間勤務
		val irregularWork = domain.getIrregularWork();
		val irgAggregateTimeSet = irregularWork.getAggregateTimeSet();
		val calcSetOfIrregular = irregularWork.getCalcSetOfIrregular();
		if (entity.krcstMonsetSyaIrgAggr == null){
			entity.krcstMonsetSyaIrgAggr = new KrcstMonsetSyaIrgAggr();
			entity.krcstMonsetSyaIrgAggr.PK = key;
			entity.krcstMonsetSyaIrgAggr.setValue = new KrcstMonsetIrgAggr();
		}
		val entityIrgAggr = entity.krcstMonsetSyaIrgAggr;
		entityIrgAggr.setValue.toOverTimeWithinIrregularCriteria =
				(calcSetOfIrregular.isToOverTimeWithinIrregularCriteria() ? 1 : 0);
		entityIrgAggr.setValue.toWorkTimeOutsideCriteria =
				(calcSetOfIrregular.isToWorkTimeOutsideCriteria() ? 1 : 0);

		treatOverTimeOfLessThanCriteriaPerDay = irgAggregateTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		treatHolidayWorkTimeOfLessThanCriteriaPerWeek = irgAggregateTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
				
		for (int atrTreatOverTime = 1; atrTreatOverTime <= 2; atrTreatOverTime++){
			List<OverTimeFrameNo> overTimeFrameNoList =
					treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames();
			if (atrTreatOverTime == 2){
				overTimeFrameNoList = treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames();
			}
			for (val overTimeFrameNo : overTimeFrameNoList){
				switch(overTimeFrameNo.v().intValue()){
				case 1:
					entityIrgAggr.setValue.treatOverTime01 = atrTreatOverTime;
					break;
				case 2:
					entityIrgAggr.setValue.treatOverTime02 = atrTreatOverTime;
					break;
				case 3:
					entityIrgAggr.setValue.treatOverTime03 = atrTreatOverTime;
					break;
				case 4:
					entityIrgAggr.setValue.treatOverTime04 = atrTreatOverTime;
					break;
				case 5:
					entityIrgAggr.setValue.treatOverTime05 = atrTreatOverTime;
					break;
				case 6:
					entityIrgAggr.setValue.treatOverTime06 = atrTreatOverTime;
					break;
				case 7:
					entityIrgAggr.setValue.treatOverTime07 = atrTreatOverTime;
					break;
				case 8:
					entityIrgAggr.setValue.treatOverTime08 = atrTreatOverTime;
					break;
				case 9:
					entityIrgAggr.setValue.treatOverTime09 = atrTreatOverTime;
					break;
				case 10:
					entityIrgAggr.setValue.treatOverTime10 = atrTreatOverTime;
					break;
				}
			}
		}
		
		atrAutoExcludeHolidayWork = 1;
		for (val autoExcludeHolidayWorkFrame : treatHolidayWorkTimeOfLessThanCriteriaPerWeek.getAutoExcludeHolidayWorkFrames()){
			switch(autoExcludeHolidayWorkFrame.v().intValue()){
			case 1:
				entityIrgAggr.setValue.treatHolidayWorkTime01 = atrAutoExcludeHolidayWork;
				break;
			case 2:
				entityIrgAggr.setValue.treatHolidayWorkTime02 = atrAutoExcludeHolidayWork;
				break;
			case 3:
				entityIrgAggr.setValue.treatHolidayWorkTime03 = atrAutoExcludeHolidayWork;
				break;
			case 4:
				entityIrgAggr.setValue.treatHolidayWorkTime04 = atrAutoExcludeHolidayWork;
				break;
			case 5:
				entityIrgAggr.setValue.treatHolidayWorkTime05 = atrAutoExcludeHolidayWork;
				break;
			case 6:
				entityIrgAggr.setValue.treatHolidayWorkTime06 = atrAutoExcludeHolidayWork;
				break;
			case 7:
				entityIrgAggr.setValue.treatHolidayWorkTime07 = atrAutoExcludeHolidayWork;
				break;
			case 8:
				entityIrgAggr.setValue.treatHolidayWorkTime08 = atrAutoExcludeHolidayWork;
				break;
			case 9:
				entityIrgAggr.setValue.treatHolidayWorkTime09 = atrAutoExcludeHolidayWork;
				break;
			case 10:
				entityIrgAggr.setValue.treatHolidayWorkTime10 = atrAutoExcludeHolidayWork;
				break;
			}
		}

		// 変形労働時間勤務：時間外超過設定
		val irgExcessOutsideTimeSet = irregularWork.getExcessOutsideTimeSet();
		if (entity.krcstMonsetSyaIrgExot == null){
			entity.krcstMonsetSyaIrgExot = new KrcstMonsetSyaIrgExot();
			entity.krcstMonsetSyaIrgExot.PK = key;
			entity.krcstMonsetSyaIrgExot.setValue = new KrcstMonsetIrgExot();
		}
		val entityIrgExot = entity.krcstMonsetSyaIrgExot;
		entityIrgExot.setValue.exceptLegalHolidayWorkTime =
				(irgExcessOutsideTimeSet.isAutoExcludeHolidayWorkTimeFromExcessOutsideWorkTime() ? 1 : 0);

		treatOverTimeOfLessThanCriteriaPerDay = irgExcessOutsideTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		treatHolidayWorkTimeOfLessThanCriteriaPerWeek = irgExcessOutsideTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
				
		for (int atrTreatOverTime = 1; atrTreatOverTime <= 2; atrTreatOverTime++){
			List<OverTimeFrameNo> overTimeFrameNoList =
					treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames();
			if (atrTreatOverTime == 2){
				overTimeFrameNoList = treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames();
			}
			for (val overTimeFrameNo : overTimeFrameNoList){
				switch(overTimeFrameNo.v().intValue()){
				case 1:
					entityIrgExot.setValue.treatOverTime01 = atrTreatOverTime;
					break;
				case 2:
					entityIrgExot.setValue.treatOverTime02 = atrTreatOverTime;
					break;
				case 3:
					entityIrgExot.setValue.treatOverTime03 = atrTreatOverTime;
					break;
				case 4:
					entityIrgExot.setValue.treatOverTime04 = atrTreatOverTime;
					break;
				case 5:
					entityIrgExot.setValue.treatOverTime05 = atrTreatOverTime;
					break;
				case 6:
					entityIrgExot.setValue.treatOverTime06 = atrTreatOverTime;
					break;
				case 7:
					entityIrgExot.setValue.treatOverTime07 = atrTreatOverTime;
					break;
				case 8:
					entityIrgExot.setValue.treatOverTime08 = atrTreatOverTime;
					break;
				case 9:
					entityIrgExot.setValue.treatOverTime09 = atrTreatOverTime;
					break;
				case 10:
					entityIrgExot.setValue.treatOverTime10 = atrTreatOverTime;
					break;
				}
			}
		}
		
		atrAutoExcludeHolidayWork = 1;
		for (val autoExcludeHolidayWorkFrame : treatHolidayWorkTimeOfLessThanCriteriaPerWeek.getAutoExcludeHolidayWorkFrames()){
			switch(autoExcludeHolidayWorkFrame.v().intValue()){
			case 1:
				entityIrgExot.setValue.treatHolidayWorkTime01 = atrAutoExcludeHolidayWork;
				break;
			case 2:
				entityIrgExot.setValue.treatHolidayWorkTime02 = atrAutoExcludeHolidayWork;
				break;
			case 3:
				entityIrgExot.setValue.treatHolidayWorkTime03 = atrAutoExcludeHolidayWork;
				break;
			case 4:
				entityIrgExot.setValue.treatHolidayWorkTime04 = atrAutoExcludeHolidayWork;
				break;
			case 5:
				entityIrgExot.setValue.treatHolidayWorkTime05 = atrAutoExcludeHolidayWork;
				break;
			case 6:
				entityIrgExot.setValue.treatHolidayWorkTime06 = atrAutoExcludeHolidayWork;
				break;
			case 7:
				entityIrgExot.setValue.treatHolidayWorkTime07 = atrAutoExcludeHolidayWork;
				break;
			case 8:
				entityIrgExot.setValue.treatHolidayWorkTime08 = atrAutoExcludeHolidayWork;
				break;
			case 9:
				entityIrgExot.setValue.treatHolidayWorkTime09 = atrAutoExcludeHolidayWork;
				break;
			case 10:
				entityIrgExot.setValue.treatHolidayWorkTime10 = atrAutoExcludeHolidayWork;
				break;
			}
		}

		// 変形労働時間勤務：精算期間
		val settlementPeriods = irregularWork.getSettlementPeriod().getSettlementPeriods();
		if (entity.krcstMonsetSyaIrgSetls == null) entity.krcstMonsetSyaIrgSetls = new ArrayList<>();
		val entityIrgSetls = entity.krcstMonsetSyaIrgSetls;
		entityIrgSetls.removeIf(a -> { return !settlementPeriods.stream()
				.filter(c -> c.getStartMonth().v() == a.PK.startMonth).findFirst().isPresent(); });
		for (val settlementPeriod : settlementPeriods){
			KrcstMonsetSyaIrgSetl entityIrgSetl = new KrcstMonsetSyaIrgSetl();
			boolean isAddIrgSetl = false;
			val entityIrgSetlOpt = entityIrgSetls.stream()
					.filter(c -> c.PK.startMonth == settlementPeriod.getStartMonth().v()).findFirst();
			if (entityIrgSetlOpt.isPresent()){
				entityIrgSetl = entityIrgSetlOpt.get();
			}
			else {
				isAddIrgSetl = true;
				entityIrgSetl.PK = new KrcstMonsetSyaIrgSetlPK(
						domain.getCompanyId(),
						domain.getEmployeeId(),
						settlementPeriod.getStartMonth().v());
				entityIrgSetl.setValue = new KrcstMonsetIrgSetl();
			}
			entityIrgSetl.setValue.endMonth = settlementPeriod.getEndMonth().v();
			if (isAddIrgSetl) entityIrgSetls.add(entityIrgSetl);
		}
		
		// フレックス時間勤務
		val flexWork = domain.getFlexWork();
		val flxShortageSet = flexWork.getShortageSet();
		val flxLegalAggrSet = flexWork.getLegalAggregateSet();
		val flxAggrTimeSet = flxLegalAggrSet.getAggregateTimeSet();
		val flxExcessOutsideTimeSet = flxLegalAggrSet.getExcessOutsideTimeSet();
		val flxAggrMethod36Agreement = flexWork.getArrgMethod36Agreement();
		if (entity.krcstMonsetSyaFlxAggr == null){
			entity.krcstMonsetSyaFlxAggr = new KrcstMonsetSyaFlxAggr();
			entity.krcstMonsetSyaFlxAggr.PK = key;
			entity.krcstMonsetSyaFlxAggr.setValue = new KrcstMonsetFlxAggr();
		}
		val entityFlxAggr = entity.krcstMonsetSyaFlxAggr;
		entityFlxAggr.setValue.aggregateMethod = flexWork.getAggregateMethod().value;
		entityFlxAggr.setValue.includeOverTime = (flexWork.isIncludeOverTime() ? 1 : 0);
		entityFlxAggr.setValue.carryforwardSet = flxShortageSet.getCarryforwardSet().value;
		entityFlxAggr.setValue.settlePeriod = flxShortageSet.getSettlePeriod().value;
		entityFlxAggr.setValue.startMonth = flxShortageSet.getStartMonth().v();
		entityFlxAggr.setValue.settlePeriodMon = flxShortageSet.getPeriod().value;
		entityFlxAggr.setValue.aggregateSet = flxAggrTimeSet.getAggregateSet().value;
		entityFlxAggr.setValue.excessOutsideTimeTargetSet = flxExcessOutsideTimeSet.getExcessOutsideTimeTargetSet().value;
		entityFlxAggr.setValue.aggregateMethodOf36AgreementTime = flxAggrMethod36Agreement.getAggregateMethod().value;
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) this.getEntityManager().persist(entity);
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId, String employeeId) {
		this.commandProxy().remove(KrcstMonsetSyaRegAggr.class, new KrcstMonsetSyaRegAggrPK(companyId, employeeId));
	}
}
