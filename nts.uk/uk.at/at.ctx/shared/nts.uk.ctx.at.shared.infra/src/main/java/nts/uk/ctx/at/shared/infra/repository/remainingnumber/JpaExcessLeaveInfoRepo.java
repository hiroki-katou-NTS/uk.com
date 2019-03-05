package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.math.BigDecimal;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.KrcmtSpecialLeaveReam;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.excessleave.KrcmtExcessLeaveInfo;

@Stateless
public class JpaExcessLeaveInfoRepo extends JpaRepository  implements ExcessLeaveInfoRepository{

	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private ExcessLeaveInfo toDomain(KrcmtExcessLeaveInfo entity){
		return new ExcessLeaveInfo(entity.cID, entity.employeeId, entity.useAtr, entity.occurrenceUnit, entity.paymentMethod);
	}
	
	/**
	 * Convert to entity
	 * @param domain
	 * @return
	 */
	private KrcmtExcessLeaveInfo toEntity(ExcessLeaveInfo domain){
		KrcmtExcessLeaveInfo entity = new KrcmtExcessLeaveInfo();
		entity.cID = domain.getCid();
		entity.employeeId = domain.getSID();
		entity.useAtr = domain.getUseAtr().value;
		entity.occurrenceUnit = domain.getOccurrenceUnit().v();
		entity.paymentMethod = domain.getPaymentMethod().value;
		return entity;
	}
	@Override
	public Optional<ExcessLeaveInfo> get(String sid) {
		Optional<KrcmtExcessLeaveInfo> leaveInfo = this.queryProxy().find(sid, KrcmtExcessLeaveInfo.class);
		if (leaveInfo.isPresent()){
			return Optional.of(toDomain(leaveInfo.get()));
		}
		
		return Optional.empty();
	}

	@Override
	public void add(ExcessLeaveInfo domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(ExcessLeaveInfo domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void delete(String sid) {
		this.commandProxy().remove(KrcmtExcessLeaveInfo.class, sid);
 	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository#getAll(java.util.List, java.lang.String)
	 */
	@Override
	public List<ExcessLeaveInfo> getAll(List<String> sids, String cid) {
		List<KrcmtExcessLeaveInfo> entities = new ArrayList<>();
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_SPEC_LEAVE_REMAIN WHERE  CID = ? AND SID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString(1, cid);

				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}
				List<KrcmtExcessLeaveInfo> result = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					KrcmtExcessLeaveInfo entity = new KrcmtExcessLeaveInfo();
					entity.cID = rec.getString("CID");
					entity.employeeId = rec.getString("SID");
					entity.useAtr = rec.getInt("USE_ATR");
					entity.occurrenceUnit = rec.getInt("OCCURRENCE_UNIT");
					entity.paymentMethod = rec.getInt("PAYMENT_METHOD");
					
					return entity;
				});
				entities.addAll(result);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return entities.stream()
				.map(x -> ExcessLeaveInfo.createDomain(
							    x.cID,
								x.employeeId,
								new BigDecimal(x.useAtr),
								new BigDecimal(x.occurrenceUnit),
								new BigDecimal(x.paymentMethod))).collect(Collectors.toList());
	
	}


}
