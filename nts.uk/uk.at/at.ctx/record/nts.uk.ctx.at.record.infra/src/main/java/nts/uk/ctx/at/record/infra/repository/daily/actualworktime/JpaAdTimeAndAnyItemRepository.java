package nts.uk.ctx.at.record.infra.repository.daily.actualworktime;


import java.sql.Date;

import javax.ejb.Stateless;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AdTimeAnyItemStoredForDailyCalc;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤怠時間と任意項目の同時更新＆登録用
 * @author keisuke_hoshina
 *
 */
@Stateless
public class JpaAdTimeAndAnyItemRepository extends JpaRepository implements AdTimeAnyItemStoredForDailyCalc{

	@Override
	public void storeAd(String employeeId, GeneralDate ymd) {
		//PROCEDURE_DAILY_DATA プログラム名
		StoredProcedureQuery stQuery = this.getEntityManager().createStoredProcedureQuery("PROCEDURE_DAILY_DATA");
		//プロシージャ側に定義されているパラメータ名を定義
		stQuery.registerStoredProcedureParameter("CId", String.class, ParameterMode.IN)//第一引数
			   .registerStoredProcedureParameter("SId", String.class, ParameterMode.IN)//第二引数
			   .registerStoredProcedureParameter("Ymd", Date.class, ParameterMode.IN)//第三引数
			   .setParameter("CId", AppContexts.user().companyId().toString()).setParameter("SId", employeeId.toString()).setParameter("Ymd", ymd.date());
//		//実行
		stQuery.execute();
		
	}
}
