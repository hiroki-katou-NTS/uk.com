package nts.uk.ctx.exio.infra.repository.input.canonicalize.domaindata;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.history.ExternalImportHistory;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryKeyColumnNames;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaDomainDataRepository extends JpaRepository implements DomainDataRepository {

	@Override
	public boolean exists(DomainDataId id) {
		val statement = createStatement(id, "select 1");
		return statement.getList(rec -> 1).isEmpty() == false;
	}

	@Override
	public void delete(DomainDataId id) {
		val statement = createStatement(id, "delete");
		statement.execute();
	}
	

	private NtsStatement createStatement(DomainDataId id, String baseCommand) {
		
		StringBuilder sql = new StringBuilder()
				.append(baseCommand)
				.append(" from ")
				.append(id.getTableName())
				.append(" where ");
		
		for (int i = 0; i < id.getKeys().size(); i++) {
			
			if (i > 0) {
				sql.append(" and ");
			}
			
			val column = id.getKeys().get(i).getColumn().getColumnName();
			sql.append(column).append(" = @p").append(i);
		}
		
		val statement = this.jdbcProxy().query(sql.toString());
		
		for (int i = 0; i < id.getKeys().size(); i++) {
			val key = id.getKeys();
			key.get(i).setParam(statement, "p" + i);
		}
		
		return statement;
	}

	@Override
	@SneakyThrows
	public ExternalImportHistory getHistory(DomainDataId id, HistoryType historyType, HistoryKeyColumnNames keyColumnNames) {
		val statement = createStatement(id, "select *");
		List<DateHistoryItem> list = statement.getList(rec ->{
			return new DateHistoryItem(rec.getString("HIST_ID"),
					new DatePeriod(
							rec.getGeneralDate(keyColumnNames.getStartDate()),
							rec.getGeneralDate(keyColumnNames.getEndDate())));
		});
		//EmployeeHistoryCanonicalization??????????????????DomainDataId?????????ID???????????????0?????????
		String employeeId = (String)id.getKeys().get(0).getValue();
		return (ExternalImportHistory)historyType.GetHistoryClass().getConstructors()[0].newInstance(employeeId, list);
	}

	@Override
	public void update(DomainDataId targetKey, DatePeriod period) {
		StringBuilder sql = new StringBuilder()
				.append(" update ")
				.append(targetKey.getTableName())
				.append(" set ");
				
		//????????????=START_END???????????????????????????
		// + ????????????START_DATE???????????????END_DATE????????????????????????????????????
		sql.append("START_DATE").append(" = @startDate ");
		sql.append(",");
		sql.append("END_DATE").append(" = @endDate ");
				
		
		sql = sql.append(" where ");
		
		//???????????????Table???key???????????????????????????
		for (int keysInt = 0; keysInt < targetKey.getKeys().size(); keysInt++) {
			
			if (keysInt > 0) {
				sql.append(" and ");
			}
			
			val column = targetKey.getKeys().get(keysInt).getColumn().getColumnName();
			sql.append(column).append(" = @p").append(keysInt);
		}
		
		val statement = this.jdbcProxy().query(sql.toString());

		for (int i = 0; i < targetKey.getKeys().size(); i++) {
			val key = targetKey.getKeys();
			key.get(i).setParam(statement, "p" + i);
		}
		
		statement.paramDate("startDate", period.start())
						.paramDate("endDate", period.end())
						.execute();
	}

}
