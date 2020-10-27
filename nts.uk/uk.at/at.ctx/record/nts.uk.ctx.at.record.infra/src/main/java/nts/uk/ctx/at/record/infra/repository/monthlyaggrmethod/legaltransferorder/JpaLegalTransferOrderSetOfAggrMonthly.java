package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.legaltransferorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.legaltransferorder.KrcmtCalcMTrnsSort;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.legaltransferorder.KrcmtCalcMTrnsSortPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalHolidayWorkTransferOrder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalHolidayWorkTransferOrderOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalOverTimeTransferOrder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalOverTimeTransferOrderOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * リポジトリ実装：月別実績の法定内振替順設定
 * @author shuichi_ishida
 */
@Stateless
public class JpaLegalTransferOrderSetOfAggrMonthly extends JpaRepository implements LegalTransferOrderSetOfAggrMonthlyRepository {

	/** 検索 */
	@Override
	public Optional<LegalTransferOrderSetOfAggrMonthly> find(String companyId) {
		return this.queryProxy()
				.find(new KrcmtCalcMTrnsSortPK(companyId), KrcmtCalcMTrnsSort.class)
				.map(c -> toDomain(c));
	}
	
	/**
	 * エンティティ→ドメイン
	 * @param entity エンティティ：月別実績の法定内振替順設定
	 * @return ドメイン：月別実績の法定内振替順設定
	 */
	private static LegalTransferOrderSetOfAggrMonthly toDomain(KrcmtCalcMTrnsSort entity){
		
		// 残業・休出並び順の再構成
		Map<Integer, Integer> overTimeOrderMap = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.overTimeOrder01);}
			{put(2, entity.overTimeOrder02);}
			{put(3, entity.overTimeOrder03);}
			{put(4, entity.overTimeOrder04);}
			{put(5, entity.overTimeOrder05);}
			{put(6, entity.overTimeOrder06);}
			{put(7, entity.overTimeOrder07);}
			{put(8, entity.overTimeOrder08);}
			{put(9, entity.overTimeOrder09);}
			{put(10, entity.overTimeOrder10);}
		};
		Map<Integer, Integer> holidayWorkTimeOrderMap = new HashMap<Integer, Integer>(){
			private static final long serialVersionUID = 1L;
			{put(1, entity.holidayWorkTimeOrder01);}
			{put(2, entity.holidayWorkTimeOrder02);}
			{put(3, entity.holidayWorkTimeOrder03);}
			{put(4, entity.holidayWorkTimeOrder04);}
			{put(5, entity.holidayWorkTimeOrder05);}
			{put(6, entity.holidayWorkTimeOrder06);}
			{put(7, entity.holidayWorkTimeOrder07);}
			{put(8, entity.holidayWorkTimeOrder08);}
			{put(9, entity.holidayWorkTimeOrder09);}
			{put(10, entity.holidayWorkTimeOrder10);}
		};
		List<LegalOverTimeTransferOrder> overTimeOrders = new ArrayList<>();
		List<LegalHolidayWorkTransferOrder> holidayWorkTimeOrders = new ArrayList<>();
		for (int i = 1; i <= 10; i++){
			if (overTimeOrderMap.containsKey(i)){
				val sortOrder = overTimeOrderMap.get(i); 
				if (sortOrder > 0){
					overTimeOrders.add(LegalOverTimeTransferOrder.of(new OverTimeFrameNo(i), sortOrder));
				}
			}
			if (holidayWorkTimeOrderMap.containsKey(i)){
				val sortOrder = holidayWorkTimeOrderMap.get(i);
				if (sortOrder > 0){
					holidayWorkTimeOrders.add(LegalHolidayWorkTransferOrder.of(new HolidayWorkFrameNo(i), sortOrder));
				}
			}
		}
		
		// 月次集計の法定内残業振替順
		val legalOverTimeTransferOrder =
				LegalOverTimeTransferOrderOfAggrMonthly.of(overTimeOrders);
		
		// 月次集計の法定内休出振替順
		val legalHolidayWorkTransferOrder =
				LegalHolidayWorkTransferOrderOfAggrMonthly.of(holidayWorkTimeOrders);
		
		val domain = LegalTransferOrderSetOfAggrMonthly.of(
				entity.PK.companyId,
				legalOverTimeTransferOrder,
				legalHolidayWorkTransferOrder);
		return domain;
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(LegalTransferOrderSetOfAggrMonthly domain) {
		
		// キー
		val key = new KrcmtCalcMTrnsSortPK(domain.getCompanyId());
		
		// 月次集計の法定内残業振替順
		val legalOverTimeTransferOrder = domain.getLegalOverTimeTransferOrder();
		// 月次集計の法定内休出振替順
		val legalHolidayWorkTransferOrder = domain.getLegalHolidayWorkTransferOrder();

		// 登録・更新を判断　および　キー値設定
		boolean isNeedPersist = false;
		KrcmtCalcMTrnsSort entity = this.getEntityManager().find(KrcmtCalcMTrnsSort.class, key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcmtCalcMTrnsSort();
			entity.PK = key;
		}
		
		// 登録・更新値の設定
		for (val overTimeOrder : legalOverTimeTransferOrder.getLegalOverTimeTransferOrders()){
			switch(overTimeOrder.getOverTimeFrameNo().v()){
			case 1:
				entity.overTimeOrder01 = overTimeOrder.getSortOrder();
				break;
			case 2:
				entity.overTimeOrder02 = overTimeOrder.getSortOrder();
				break;
			case 3:
				entity.overTimeOrder03 = overTimeOrder.getSortOrder();
				break;
			case 4:
				entity.overTimeOrder04 = overTimeOrder.getSortOrder();
				break;
			case 5:
				entity.overTimeOrder05 = overTimeOrder.getSortOrder();
				break;
			case 6:
				entity.overTimeOrder06 = overTimeOrder.getSortOrder();
				break;
			case 7:
				entity.overTimeOrder07 = overTimeOrder.getSortOrder();
				break;
			case 8:
				entity.overTimeOrder08 = overTimeOrder.getSortOrder();
				break;
			case 9:
				entity.overTimeOrder09 = overTimeOrder.getSortOrder();
				break;
			case 10:
				entity.overTimeOrder10 = overTimeOrder.getSortOrder();
				break;
			}
		}
		
		for (val holidayWorkTimeOrder : legalHolidayWorkTransferOrder.getLegalHolidayWorkTransferOrders()){
			switch(holidayWorkTimeOrder.getHolidayWorkFrameNo().v()){
			case 1:
				entity.holidayWorkTimeOrder01 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 2:
				entity.holidayWorkTimeOrder02 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 3:
				entity.holidayWorkTimeOrder03 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 4:
				entity.holidayWorkTimeOrder04 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 5:
				entity.holidayWorkTimeOrder05 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 6:
				entity.holidayWorkTimeOrder06 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 7:
				entity.holidayWorkTimeOrder07 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 8:
				entity.holidayWorkTimeOrder08 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 9:
				entity.holidayWorkTimeOrder09 = holidayWorkTimeOrder.getSortOrder();
				break;
			case 10:
				entity.holidayWorkTimeOrder10 = holidayWorkTimeOrder.getSortOrder();
				break;
			}
		}
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) this.getEntityManager().persist(entity);
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KrcmtCalcMTrnsSort.class, new KrcmtCalcMTrnsSortPK(companyId));
	}
}
