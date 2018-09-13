package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.otkcustomize;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.repo.ContinuousHolCheckSetRepo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousVacationDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize.KrcctOtkVacationCk;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize.KrcctOtkWtNonTarget;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize.KrcctOtkWtTarget;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Stateless
public class ContinuousHolCheckSetRepoImpl extends JpaRepository implements ContinuousHolCheckSetRepo {

	@Override
	public List<ContinuousHolCheckSet> find(List<String> companyIds){
		if(companyIds.isEmpty()){
			return new ArrayList<>();
		}
		return this.queryProxy().query("SELECT v FROM KrcctOtkVacationCk v WHERE v.cid IN :cid", KrcctOtkVacationCk.class)
				.setParameter("cid", companyIds).getList(c -> c.toDomain());
	}

	@Override
	public Optional<ContinuousHolCheckSet> find(String companyId){
		return find(Arrays.asList(companyId)).stream()
					.filter(c -> c.getCompanyId().equals(companyId)).findFirst();
	}
	
	@Override
	public Optional<ContinuousHolCheckSet> findSpecial(String companyId) {
		try {
			StringBuilder queryString = new StringBuilder("SELECT a.CID, a.CONTINUOUS_DAYS, a.MESSAGE_DISPLAY, ");
			queryString.append(" STUFF((SELECT '; ' + b.WORKTYPE_CD FROM KRCCT_OTK_WT_TARGET b ");
			queryString.append(" WHERE a.CID = b.CID FOR XML PATH('')), 1, 1, '') [TARGET], ");
			queryString.append(" STUFF((SELECT '; ' + c.WORKTYPE_CD FROM KRCCT_OTK_WT_NONTARGET c ");
			queryString.append(" WHERE a.CID = c.CID FOR XML PATH('')), 1, 1, '') [NONTARGET] ");
			queryString.append(" FROM KRCCT_OTK_VACATION_CK a WHERE a.CID = ? AND a.USE_ATR = ?");
			PreparedStatement statement = this.connection().prepareStatement(queryString.toString());

			statement.setString(1, companyId);
			statement.setInt(2, 1);
			return new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
				return new ContinuousHolCheckSet(companyId, 
						Stream.of(rec.getString("TARGET").split(";")).map(c -> new WorkTypeCode(c.trim())).collect(Collectors.toList()), 
						Stream.of(rec.getString("NONTARGET").split(";")).map(c -> new WorkTypeCode(c.trim())).collect(Collectors.toList()), 
						true, 
						new DisplayMessage(rec.getString("MESSAGE_DISPLAY")), 
						new ContinuousVacationDays(rec.getInt("CONTINUOUS_DAYS")));
			});
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public void insert(ContinuousHolCheckSet setting){
		this.commandProxy().insert(KrcctOtkVacationCk.fromDomain(setting));
		this.commandProxy().insertAll(toNonTarget(setting.getIgnoreWorkType(), setting.getCompanyId()));
		this.commandProxy().insertAll(toTarget(setting.getTargetWorkType(), setting.getCompanyId()));
	}

	@Override
	public void update(ContinuousHolCheckSet setting){
		KrcctOtkVacationCk entity = findEntity(setting.getCompanyId());
		if(entity != null){
			if(!entity.krcctOtkWtNonTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtNonTarget);
			}
			if(!entity.krcctOtkWtTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtTarget);
			}
			this.getEntityManager().flush();
			entity.continuousDays = setting.getMaxContinuousDays().v();
			entity.messageDisplay = setting.getDisplayMessege().v();
			entity.useAtr = setting.isUseAtr() ? 1 : 0;
			this.commandProxy().insertAll(toNonTarget(setting.getIgnoreWorkType(), setting.getCompanyId()));
			this.commandProxy().insertAll(toTarget(setting.getTargetWorkType(), setting.getCompanyId()));
			this.getEntityManager().flush();
		}
	}

	@Override
	public void remove(String cid){
		KrcctOtkVacationCk entity = findEntity(cid);
		if(entity != null){
			if(!entity.krcctOtkWtNonTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtNonTarget);
			}
			if(!entity.krcctOtkWtTarget.isEmpty()){
				this.commandProxy().removeAll(entity.krcctOtkWtTarget);
			}
			this.commandProxy().remove(entity);
		}
	}

	private KrcctOtkVacationCk findEntity(String cid) {
		KrcctOtkVacationCk entity = this.queryProxy().query("SELECT v FROM KrcctOtkVacationCk v WHERE v.cid = :cid", KrcctOtkVacationCk.class)
											.setParameter("cid", cid).getSingleOrNull();
		return entity;
	}
	
	private List<KrcctOtkWtNonTarget> toNonTarget(List<WorkTypeCode> typeCode, String cid){
		return typeCode.stream().map(t -> new KrcctOtkWtNonTarget(cid, t.v())).collect(Collectors.toList());
	}
	
	private List<KrcctOtkWtTarget> toTarget(List<WorkTypeCode> typeCode, String cid){
		return typeCode.stream().map(t -> new KrcctOtkWtTarget(cid, t.v())).collect(Collectors.toList());
	}
}