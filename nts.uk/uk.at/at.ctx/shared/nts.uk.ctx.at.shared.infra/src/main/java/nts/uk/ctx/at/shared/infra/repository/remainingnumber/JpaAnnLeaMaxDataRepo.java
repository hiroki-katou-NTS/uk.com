package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea.KrcmtAnnLeaMax;

@Stateless
public class JpaAnnLeaMaxDataRepo extends JpaRepository implements AnnLeaMaxDataRepository {

	@Override
	public Optional<AnnualLeaveMaxData> get(String employeeId) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax ent = entityOpt.get();
			return Optional.of(AnnualLeaveMaxData.createFromJavaType(ent.sid, ent.maxTimes, ent.usedTimes,
					ent.maxMinutes, ent.usedMinutes));
		}
		return Optional.empty();
	}

	@Override
	public void add(AnnualLeaveMaxData maxData) {
		KrcmtAnnLeaMax entity = new KrcmtAnnLeaMax();
		entity.sid = maxData.getEmployeeId();
		entity.cid = maxData.getCompanyId();
		if ( maxData.getHalfdayAnnualLeaveMax().isPresent()) {
			HalfdayAnnualLeaveMax halfday = maxData.getHalfdayAnnualLeaveMax().get();
			entity.maxTimes = halfday.getMaxTimes().v();
			entity.usedTimes = halfday.getUsedTimes().v();
			entity.remainingTimes = halfday.getRemainingTimes().v();
		}
		if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
			TimeAnnualLeaveMax time = maxData.getTimeAnnualLeaveMax().get();
			entity.maxMinutes = time.getMaxMinutes().v();
			entity.usedMinutes = time.getUsedMinutes().v();
			entity.remainingMinutes = time.getRemainingMinutes().v();
		}
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(AnnualLeaveMaxData maxData) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(maxData.getEmployeeId(), KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax entity = entityOpt.get();
			if ( maxData.getHalfdayAnnualLeaveMax().isPresent()) {
				HalfdayAnnualLeaveMax halfday = maxData.getHalfdayAnnualLeaveMax().get();
				entity.maxTimes = halfday.getMaxTimes().v();
				entity.usedTimes = halfday.getUsedTimes().v();
				entity.remainingTimes = halfday.getRemainingTimes().v();
			} else {
				entity.maxTimes = null;
				entity.usedTimes = null;
				entity.remainingTimes = null;
			}
			if ( maxData.getTimeAnnualLeaveMax().isPresent()) {
				TimeAnnualLeaveMax time = maxData.getTimeAnnualLeaveMax().get();
				entity.maxMinutes = time.getMaxMinutes().v();
				entity.usedMinutes = time.getUsedMinutes().v();
				entity.remainingMinutes = time.getRemainingMinutes().v();
			} else {
				entity.maxMinutes = null;
				entity.usedMinutes = null;
				entity.remainingMinutes = null;
			}
			this.commandProxy().update(entity);
		} else {
			add(maxData);
		}
	}

	@Override
	public void delete(String employeeId) {
		Optional<KrcmtAnnLeaMax> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaMax.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaMax entity = entityOpt.get();
			this.commandProxy().remove(entity);
		}
	}

	@Override
	public List<AnnualLeaveMaxData> getAll(String cid, List<String> sids) {
		List<AnnualLeaveMaxData> result = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM KRCMT_ANNLEA_MAX WHERE CID = ? AND SID IN (" + NtsStatement.In.createParamsString(subList) + ")";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				stmt.setString( 1, cid);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString( i + 1, subList.get(i));
				}
				
				List<AnnualLeaveMaxData> annualLeavelst = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					return AnnualLeaveMaxData.createFromJavaType( r.getString("SID"),  r.getInt("MAX_TIMES"), r.getInt("USED_TIMES"),
							r.getInt("MAX_MINUTES"), r.getInt("USED_MINUTES"));
				});
				result.addAll(annualLeavelst);
				
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		
		return result;
	}

}
