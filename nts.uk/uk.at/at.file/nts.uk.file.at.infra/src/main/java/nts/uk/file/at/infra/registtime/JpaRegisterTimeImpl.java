package nts.uk.file.at.infra.registtime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClosingDateType;
import nts.uk.ctx.at.record.dom.standardtime.enums.StartingMonthType;
import nts.uk.ctx.at.record.dom.standardtime.enums.TargetSettingAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.file.at.app.export.regisagreetime.RegistTimeColumn;
import nts.uk.file.at.app.export.regisagreetime.RegistTimeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaRegisterTimeImpl implements RegistTimeRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String SQL_EXPORT_SHEET_1 = "SELECT "
			+ "STARTING_MONTH_TYPE,"
			+ "CLOSING_DATE_ATR,"
			+ "CLOSING_DATE_TYPE,"
			+ "NUMBER_TIMES_OVER_LIMIT_TYPE,"
			+ "ALARM_LIST_ATR,"
			+ "YEARLY_WORK_TABLE_ATR "
			+ "FROM "
			+ "KMKMT_AGREEMENT_OPE_SET "
			+ "WHERE CID = ?1";
	
	@Override
	public List<MasterData> getDataExport() {
		List<MasterData> datas = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		Query query = entityManager.createNativeQuery(SQL_EXPORT_SHEET_1.toString()).setParameter(1, cid);
		Object[] data = (Object[]) query.getSingleResult();
		for (int i = 0; i < data.length; i++) {
			datas.add(new MasterData(dataContent(data[i],i), null, ""));
		}
		return datas;
	}
	
	private Map<String, Object> dataContent(Object object,int check) {
		Map<String, Object> data = new HashMap<>();
		data.put(RegistTimeColumn.KMK008_80, check == 0 ? RegistTimeColumn.KMK008_82 : "");
		data.put(RegistTimeColumn.HEADER_NONE1, check == 0 ? RegistTimeColumn.KMK008_83 : check == 1 ? RegistTimeColumn.KMK008_84 : check == 3 ? RegistTimeColumn.KMK008_85 : check == 4 ? RegistTimeColumn.KMK008_86 : "");
		data.put(RegistTimeColumn.HEADER_NONE2, check == 4 ? RegistTimeColumn.KMK008_87 : check == 5 ? RegistTimeColumn.KMK008_88 : "");
		data.put(RegistTimeColumn.KMK008_81, getValue(((BigDecimal)object).intValue(),check));
		return data;
	}
	
	private String getValue(int type,int param){
		String value = null;
		switch (param) {
		case 0:
			StartingMonthType startingMonthType = EnumAdaptor.valueOf(type, StartingMonthType.class);
			value = EnumAdaptor.convertToValueName(startingMonthType).getLocalizedName();
			break;
		case 1:
			ClosingDateAtr closingDateAtr = EnumAdaptor.valueOf(type, ClosingDateAtr.class);
			value = EnumAdaptor.convertToValueName(closingDateAtr).getLocalizedName();
			break;
		case 2:
			ClosingDateType closingDateType = EnumAdaptor.valueOf(type, ClosingDateType.class);
			value = EnumAdaptor.convertToValueName(closingDateType).getLocalizedName();
			break;
		case 3:
			TimeOverLimitType timeOverLimitType = EnumAdaptor.valueOf(type, TimeOverLimitType.class);
			value = EnumAdaptor.convertToValueName(timeOverLimitType).getLocalizedName();
			break;
		case 4:
			TargetSettingAtr targetSettingAtr = EnumAdaptor.valueOf(type, TargetSettingAtr.class);
			value = EnumAdaptor.convertToValueName(targetSettingAtr).getLocalizedName();
			break;
		case 5:
			TargetSettingAtr yearlyWorkTableAtr = EnumAdaptor.valueOf(type, TargetSettingAtr.class);
			value = EnumAdaptor.convertToValueName(yearlyWorkTableAtr).getLocalizedName();
			break;

		default:
			break;
		}
		return value;
	}
	
}
