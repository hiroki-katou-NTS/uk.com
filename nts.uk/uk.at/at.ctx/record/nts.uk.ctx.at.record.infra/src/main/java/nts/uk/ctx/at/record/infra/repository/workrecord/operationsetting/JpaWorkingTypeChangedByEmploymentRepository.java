/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.ChangeableWorktypeGroup;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.WorkingTypeChangedByEmpRepo;
import nts.uk.ctx.at.record.dom.workrecord.workingtype.WorkingTypeChangedByEmployment;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtWorktypeChangeable;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtWorktypeChangeablePk;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaWorkingTypeChangedByEmploymentRepository extends JpaRepository implements WorkingTypeChangedByEmpRepo {

	private static final String GET_ALL_OF_EMPLOYEE = "SELECT wtc FROM KrcmtWorktypeChangeable wtc"
			+ " WHERE wtc.pk.cid = :companyId AND wtc.pk.empCode = :employeeCode";

	@Override
	public WorkingTypeChangedByEmployment get(CompanyId companyId, EmploymentCode empCode) {
		List<KrcmtWorktypeChangeable> entities = this.queryProxy()
				.query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class).setParameter("companyId", companyId.v())
				.setParameter("employeeCode", empCode.v()).getList();
		if (entities.isEmpty()) {
			// default company
			entities = this.queryProxy().query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class)
					.setParameter("companyId", "000000000000-0000").setParameter("employeeCode", "0").getList();
		}
		Map<Integer, ChangeableWorktypeGroup> map = new HashMap<>();
		entities.forEach(ent -> {
			ChangeableWorktypeGroup group = null;
			if (!map.containsKey(ent.pk.workTypeGroupNo.intValue())) {
				group = new ChangeableWorktypeGroup(ent.pk.workTypeGroupNo.intValue(), ent.workTypeGroupName);
			} else {
				group = map.get(ent.pk.workTypeGroupNo.intValue());
			}
			group.getWorkTypeList().add(ent.pk.workTypeCode);
			map.put(ent.pk.workTypeGroupNo.intValue(), group);
		});

		List<ChangeableWorktypeGroup> groups = new ArrayList<>(map.values());
		groups.forEach(group -> {
			if (group.getWorkTypeList().size() == 1) {
				List<String> workTypes = new ArrayList<>(group.getWorkTypeList());
				if (workTypes.get(0) == "") {
					group.getWorkTypeList().clear();
				}
			}
		});

		return new WorkingTypeChangedByEmployment(companyId, empCode, new ArrayList<>(map.values()));
	}

	@Override
	public void save(WorkingTypeChangedByEmployment workingType) {
		String cid = workingType.getCompanyId().v();
		String empCode = workingType.getEmpCode().v();

		// delete all old results
		List<KrcmtWorktypeChangeable> deleteEntities = this.queryProxy()
				.query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class).setParameter("companyId", cid)
				.setParameter("employeeCode", empCode).getList();
		this.commandProxy().removeAll(deleteEntities);
		this.getEntityManager().flush();
		// create and insert new results
		workingType.getChangeableWorkTypeGroups().forEach(group -> {
			if (group.getWorkTypeList().isEmpty()) {
				KrcmtWorktypeChangeablePk pk = new KrcmtWorktypeChangeablePk(cid, empCode,
						new BigDecimal(group.getNo()), "");
				KrcmtWorktypeChangeable entity = new KrcmtWorktypeChangeable(pk, group.getName() == null ? null : group.getName().v());
				this.commandProxy().insert(entity);
			} else {
				group.getWorkTypeList().forEach(workTypeCode -> {
					KrcmtWorktypeChangeablePk pk = new KrcmtWorktypeChangeablePk(cid, empCode,
							new BigDecimal(group.getNo()), workTypeCode);
					KrcmtWorktypeChangeable entity = new KrcmtWorktypeChangeable(pk,
							group.getName() != null ? group.getName().v() : null);
					this.commandProxy().insert(entity);
				});
			}
		});

	}

	@Override
	public List<String> checkSetting(String companyId, List<String> empCode) {
		String query = "SELECT wtc FROM KrcmtWorktypeChangeable wtc"
				+ " WHERE wtc.pk.cid = :companyId AND wtc.pk.empCode IN :employeeCode AND wtc.pk.workTypeCode != '　'";
		List<KrcmtWorktypeChangeable> entities = new ArrayList<>();
		CollectionUtil.split(empCode, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(query, KrcmtWorktypeChangeable.class)
								.setParameter("companyId", companyId)
								.setParameter("employeeCode", subList).getList());
		});
		
		if(entities.isEmpty()) {
			new ArrayList<>();
		}
		List<String> emps = entities.stream().map(e -> e.pk.empCode).collect(Collectors.toList());
		return empCode.stream().filter(e -> emps.contains(e)).collect(Collectors.toList());
	}

	@Override
	public void copyEmployment(String companyId, WorkingTypeChangedByEmployment sourceData,
			List<String> targetEmploymentCodes, boolean isOveride) {
		for (String target : targetEmploymentCodes) {
			// 上書き確認処理
			if (isOveride) {
				// 複写先の前準備設定を削除する
				List<KrcmtWorktypeChangeable> deleteEntities = this.queryProxy()
						.query(GET_ALL_OF_EMPLOYEE, KrcmtWorktypeChangeable.class).setParameter("companyId", companyId)
						.setParameter("employeeCode", target).getList();
				this.commandProxy().removeAll(deleteEntities);
				this.getEntityManager().flush();
			} else {
				//複写先に前準備設定が存在するかどうかチェック
				 WorkingTypeChangedByEmployment testData = this.get(new CompanyId(companyId), new EmploymentCode(target));
				 if(!testData.getChangeableWorkTypeGroups().isEmpty()){
					 //エラーメッセージ（Msg_888）を表示する
					 throw new BusinessException("Msg_888");
				 }					 
			}
			// 複写先の前準備設定を追加する (Add)
			addEmploymentSet(sourceData, target);
		}
		
	}
	
	/**
	 * 複写先の前準備設定を追加する (Add)
	 * @param sourceData: source data copy
	 * @param employmentCode: target employment code
	 */
	private void addEmploymentSet(WorkingTypeChangedByEmployment sourceData, String employmentCode){
		String companyId = AppContexts.user().companyId();
		WorkingTypeChangedByEmployment search = this.get(new CompanyId(companyId), new EmploymentCode(employmentCode));
		search.setChangeableWorkTypeGroups(sourceData.getChangeableWorkTypeGroups());
		this.save(search);
	}
	

}
