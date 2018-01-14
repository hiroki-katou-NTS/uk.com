package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod;

import java.util.HashMap;
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
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ExcessOutsideTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ExcessOutsideTimeTargetSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.LegalAggrSetOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.SettingOfFlex36AgreementTime;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.AggregateTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.CalcSettingOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.ExcessOutsideTimeSet;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatHolidayWorkTimeOfLessThanCriteriaPerWeek;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.TreatOverTimeOfLessThanCriteriaPerDay;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.employee.KrcstMonsetSyaIrgSetl;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggr;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.KrcstMonsetSyaRegAggrPK;

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
	
	/** 追加 */
	@Override
	public void insert(AggrSettingMonthlyForSyain aggrMonthlySettingOfRegular) {
		this.commandProxy().insert(toEntity(aggrMonthlySettingOfRegular, false));
	}
	
	/** 更新 */
	@Override
	public void update(AggrSettingMonthlyForSyain aggrMonthlySettingOfRegular) {
		this.toEntity(aggrMonthlySettingOfRegular, true);
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId, String employeeId) {
		this.commandProxy().remove(KrcstMonsetSyaRegAggr.class, new KrcstMonsetSyaRegAggrPK(companyId, employeeId));
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
						EnumAdaptor.valueOf(entity.setValue.carryforwardSet, CarryforwardSetInShortageFlex.class)),
				LegalAggrSetOfFlx.of(
						AggregateTimeSetting.of(
								EnumAdaptor.valueOf(entity.setValue.aggregateSet, AggregateSetting.class)),
						ExcessOutsideTimeSetting.of(
								EnumAdaptor.valueOf(entity.setValue.excessOutsideTimeTargetSet, ExcessOutsideTimeTargetSetting.class))),
				SettingOfFlex36AgreementTime.of(
						EnumAdaptor.valueOf(entity.setValue.aggregateMethodOf36AgreementTime, AggrMethodOfFlex36AgreementTime.class)));
		return domain;
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domain ドメイン：社員月別実績集計設定
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：社員月別実績集計設定
	 */
	private KrcstMonsetSyaRegAggr toEntity(AggrSettingMonthlyForSyain domain,
			boolean execUpdate){
		
		// キー
		val key = new KrcstMonsetSyaRegAggrPK(domain.getCompanyId(), domain.getEmployeeId());
		
		// 通常勤務の法定内集計設定
		val legalAggrSet = domain.getRegularWork();
		// 集計時間設定
		val aggregateTimeSet = legalAggrSet.getAggregateTimeSet();
		// 1日の基準時間未満の残業時間の扱い
		val treatOverTimeOfLessThanCriteriaPerDay = aggregateTimeSet.getTreatOverTimeOfLessThanCriteriaPerDay();
		// 1週間の基準時間未満の休日出勤時間の扱い
		val treatHolidayWorkTimeOfLessThanCriteriaPerWeek = aggregateTimeSet.getTreatHolidayWorkTimeOfLessThanCriteriaPerWeek();
		
		KrcstMonsetSyaRegAggr entity;
		if (execUpdate) {
			entity = this.queryProxy().find(key, KrcstMonsetSyaRegAggr.class).get();
		}
		else {
			entity = new KrcstMonsetSyaRegAggr();
			entity.PK = key;
		}
		entity.setValue.askPremium = (aggregateTimeSet.isAskPremium() ? 1 : 0);
		
		int atrAutoExcludeOverTime = 1;
		for (val autoExcludeOverTimeFrame : treatOverTimeOfLessThanCriteriaPerDay.getAutoExcludeOverTimeFrames()){
			switch(autoExcludeOverTimeFrame.v().intValue()){
			case 1:
				entity.setValue.treatOverTime01 = atrAutoExcludeOverTime;
				break;
			case 2:
				entity.setValue.treatOverTime02 = atrAutoExcludeOverTime;
				break;
			case 3:
				entity.setValue.treatOverTime03 = atrAutoExcludeOverTime;
				break;
			case 4:
				entity.setValue.treatOverTime04 = atrAutoExcludeOverTime;
				break;
			case 5:
				entity.setValue.treatOverTime05 = atrAutoExcludeOverTime;
				break;
			case 6:
				entity.setValue.treatOverTime06 = atrAutoExcludeOverTime;
				break;
			case 7:
				entity.setValue.treatOverTime07 = atrAutoExcludeOverTime;
				break;
			case 8:
				entity.setValue.treatOverTime08 = atrAutoExcludeOverTime;
				break;
			case 9:
				entity.setValue.treatOverTime09 = atrAutoExcludeOverTime;
				break;
			case 10:
				entity.setValue.treatOverTime10 = atrAutoExcludeOverTime;
				break;
			}
		}
		
		int atrLegalOverTime = 2;
		for (val legalOverTimeFrame : treatOverTimeOfLessThanCriteriaPerDay.getLegalOverTimeFrames()){
			switch(legalOverTimeFrame.v().intValue()){
			case 1:
				entity.setValue.treatOverTime01 = atrLegalOverTime;
				break;
			case 2:
				entity.setValue.treatOverTime02 = atrLegalOverTime;
				break;
			case 3:
				entity.setValue.treatOverTime03 = atrLegalOverTime;
				break;
			case 4:
				entity.setValue.treatOverTime04 = atrLegalOverTime;
				break;
			case 5:
				entity.setValue.treatOverTime05 = atrLegalOverTime;
				break;
			case 6:
				entity.setValue.treatOverTime06 = atrLegalOverTime;
				break;
			case 7:
				entity.setValue.treatOverTime07 = atrLegalOverTime;
				break;
			case 8:
				entity.setValue.treatOverTime08 = atrLegalOverTime;
				break;
			case 9:
				entity.setValue.treatOverTime09 = atrLegalOverTime;
				break;
			case 10:
				entity.setValue.treatOverTime10 = atrLegalOverTime;
				break;
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
		
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
