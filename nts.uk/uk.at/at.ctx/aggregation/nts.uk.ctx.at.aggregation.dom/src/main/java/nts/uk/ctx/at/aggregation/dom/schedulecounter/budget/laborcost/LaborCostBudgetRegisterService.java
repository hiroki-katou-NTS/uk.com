package nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 人件費予算を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.予算.人件費予算.人件費予算を登録する
 * @author kumiko_otake
 */
@Stateless
public class LaborCostBudgetRegisterService {

	/**
	 * 登録する
	 * @param require require
	 * @param targetOrg 対象組織
	 * @param ymd 年月日
	 * @param amount 予算
	 * @return AtomTask
	 */
	public static AtomTask register(Require require
			, TargetOrgIdenInfor targetOrg, GeneralDate ymd, Optional<LaborCostBudgetAmount> amount
	) {

		// 予算なし：delete
		if( !amount.isPresent() ) {
			return AtomTask.of( () -> require.delete( targetOrg, ymd ) );
		}

		// 予算あり：delete -> insert
		val budget = new LaborCostBudget( targetOrg, ymd, amount.get() );
		return AtomTask.of( () -> {
			require.delete( targetOrg, ymd );
			require.insert( budget );
		} );

	}



	public static interface Require {

		/**
		 * 人件費予算を追加する
		 * @param domain 人件費予算
		 */
		public void insert( LaborCostBudget domain );

		/**
		 * 人件費予算を削除する
		 * @param targetOrg 対象組織
		 * @param ymd 年月日
		 */
		public void delete( TargetOrgIdenInfor targetOrg, GeneralDate ymd );

	}

}
