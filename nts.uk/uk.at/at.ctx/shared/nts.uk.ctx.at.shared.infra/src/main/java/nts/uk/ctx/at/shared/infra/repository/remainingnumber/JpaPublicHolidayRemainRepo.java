package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.publicholiday.RemainNumber;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.publicholiday.KrcmtPubHolidayRemain;

@Stateless
public class JpaPublicHolidayRemainRepo extends JpaRepository implements PublicHolidayRemainRepository{

	private PublicHolidayRemain toDomain(KrcmtPubHolidayRemain entity){
		return new PublicHolidayRemain(entity.cid, entity.employeeId, entity.remainNumber);
	}
	
	private KrcmtPubHolidayRemain toEntity(PublicHolidayRemain domain){
		KrcmtPubHolidayRemain entity = new KrcmtPubHolidayRemain();
		entity.employeeId = domain.getSID();
		entity.cid = domain.getCID();
		entity.remainNumber = domain.getRemainNumber().v();
		return entity;
	}
	
	@Override
	public Optional<PublicHolidayRemain> get(String sid) {
		Optional<KrcmtPubHolidayRemain> pubHoli = this.queryProxy().find(sid, KrcmtPubHolidayRemain.class);
		
		if (pubHoli.isPresent()){
			return Optional.of(toDomain(pubHoli.get()));
		}
		
		return Optional.empty();
	}

	@Override
	public void add(PublicHolidayRemain domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PublicHolidayRemain domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void delete(String sid) {
		this.commandProxy().remove(KrcmtPubHolidayRemain.class, sid);
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository#getAll(java.util.List)
	 */
	@Override
	public List<PublicHolidayRemain> getAll(List<String> sids) {
		List<KrcmtPubHolidayRemain> entities = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN WHERE  SPECIAL_LEAVE_CD = ? AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(i, subList.get(i));
				}
				List<KrcmtPubHolidayRemain> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtPubHolidayRemain entity = new KrcmtPubHolidayRemain();
					entity.cid = rec.getString("CID");
					entity.employeeId = rec.getString("SID");
					entity.remainNumber = rec.getBigDecimal("REMAIN_NUMBER");
					return entity;
				});
				entities.addAll(result);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return entities.stream()
				.map(x -> PublicHolidayRemain.creatFromJavaType(x.cid, x.employeeId, new RemainNumber(x.remainNumber)))
				.collect(Collectors.toList());
	}
}
