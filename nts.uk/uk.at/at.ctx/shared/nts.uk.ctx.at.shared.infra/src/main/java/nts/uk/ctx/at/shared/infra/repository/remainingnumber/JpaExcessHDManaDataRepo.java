package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessHolidayManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessHolidayManagementData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.excessleave.KrcmtExcessHDManaData;

@Stateless
public class JpaExcessHDManaDataRepo extends JpaRepository implements ExcessHolidayManaDataRepository{

	private static final String QUERY_BYSID = "SELECT e FROM KrcmtExcessHDManaData e WHERE e.cID =:cid AND e.sID =:sid AND e.expiredState = :expiredState ";
	
	@Override
	public List<ExcessHolidayManagementData> getBySidWithExpCond(String cid, String sid, int state) {
		List<KrcmtExcessHDManaData> list = this.queryProxy().query(QUERY_BYSID,KrcmtExcessHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.setParameter("expiredState", state)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	private ExcessHolidayManagementData toDomain(KrcmtExcessHDManaData entity) {
		return new ExcessHolidayManagementData(entity.id, entity.cID, entity.sID, entity.grantDate, entity.expiredDate,
				entity.expiredState, entity.registrationType, entity.occurrencesNumber, entity.remainNumer,
				entity.remainNumer);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessHolidayManaDataRepository#getAllBySidWithExpCond(java.lang.String, java.util.List, int)
	 */
	@Override
	public Map<String, Double> getAllBySidWithExpCond(String cid, List<String> sids, int state) {
		Map <String ,Double> result = new HashMap<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCDT_EXCESS_HD_MNG WHERE  CID = ? AND EXPIRED_STATE = ?   AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);
				stmt.setInt(2, state);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(3 + i, subList.get(i));
				}
				List<KrcmtExcessHDManaData> data = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtExcessHDManaData entity = new KrcmtExcessHDManaData();
					entity.cID = rec.getString("CID");
					entity.sID = rec.getString("SID");
					entity.remainNumer = rec.getInt("REMAIN_NUMBER");
					return entity;
			
				});
				Map<String, List<KrcmtExcessHDManaData>> dataMap = data.parallelStream().collect(Collectors.groupingBy(c -> c.sID));
				dataMap.entrySet().parallelStream().forEach(c ->{
					result.put(c.getKey(), c.getValue().parallelStream().mapToDouble(i -> i.remainNumer).sum());
				});
			
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessHolidayManaDataRepository#getBySidWithExpCond(java.lang.String, java.util.List, int)
	 */
	

}
