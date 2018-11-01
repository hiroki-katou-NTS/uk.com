/**
 * 5:11:24 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecordPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlApplication;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaErrorAlarmWorkRecordRepository extends JpaRepository implements ErrorAlarmWorkRecordRepository {

	private static final String FIND_BY_COMPANY = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId ";
	private static final String FIND_BY_ERROR_ALARM_CHECK_ID = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId AND a.eralCheckId = :eralCheckId ";
	private static final String FIND_ALL_ER_AL_COMPANY = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId "
			+ " AND a.useAtr = 1 AND a.typeAtr IN (0,1)";
	private static final String SELECT_ERAL_BY_LIST_CODE_ERROR = "SELECT s FROM KwrmtErAlWorkRecord s WHERE s.kwrmtErAlWorkRecordPK.errorAlarmCode IN :listCode AND s.kwrmtErAlWorkRecordPK.companyId = :companyId AND s.typeAtr = 0";
	private static final String FIND_BY_COMPANY_AND_USEATR = "SELECT a FROM KwrmtErAlWorkRecord a WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId AND a.useAtr = :useAtr ";
	private static final String SELECT_ERAL_BY_LIST_CODE = "SELECT s FROM KwrmtErAlWorkRecord s WHERE s.kwrmtErAlWorkRecordPK.errorAlarmCode IN :listCode AND s.kwrmtErAlWorkRecordPK.companyId = :companyId";
	
	@Override
	public Optional<ErrorAlarmWorkRecord> findByCode(String code) {
		Optional<KwrmtErAlWorkRecord> entity = this.queryProxy()
				.find(new KwrmtErAlWorkRecordPK(AppContexts.user().companyId(), code), KwrmtErAlWorkRecord.class);
		return Optional.ofNullable(entity.isPresent() ? KwrmtErAlWorkRecord.toDomain(entity.get()) : null);
	}

	@Override
	public List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId) {
		List<KwrmtErAlWorkRecord> lstData = this.queryProxy().query(FIND_BY_COMPANY_AND_USEATR, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId)
				.setParameter("useAtr", 1).getList();
		return lstData.stream().map(entity -> KwrmtErAlWorkRecord.toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public void addErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain, ErrorAlarmCondition conditionDomain) {
		this.commandProxy().insert(KwrmtErAlWorkRecord.fromDomain(domain, conditionDomain));
	}

	@Override
	public void updateErrorAlarmWorkRecord(ErrorAlarmWorkRecord domain, ErrorAlarmCondition conditionDomain) {
		KwrmtErAlWorkRecord targetEntity = this.queryProxy()
				.find(new KwrmtErAlWorkRecordPK(domain.getCompanyId(), domain.getCode().v()), KwrmtErAlWorkRecord.class)
				.get();
		domain.setCheckId(targetEntity.eralCheckId);
		if (!domain.getFixedAtr()) {
			conditionDomain.setGroupId1(targetEntity.getGroup1Id());
			conditionDomain.setGroupId2(targetEntity.getGroup2Id());
		}
		KwrmtErAlWorkRecord domainAfterConvert = KwrmtErAlWorkRecord.fromDomain(domain, conditionDomain);
//		targetEntity.eralCheckId = domainAfterConvert.eralCheckId;
//		targetEntity.boldAtr = domainAfterConvert.boldAtr;
//		targetEntity.cancelableAtr = domainAfterConvert.cancelableAtr;
//		targetEntity.cancelRoleId = domainAfterConvert.cancelRoleId;
//		targetEntity.errorAlarmName = domainAfterConvert.errorAlarmName;
//		targetEntity.errorDisplayItem = domainAfterConvert.errorDisplayItem;
//		targetEntity.fixedAtr = domainAfterConvert.fixedAtr;
//		targetEntity.krcmtErAlCondition = domainAfterConvert.krcmtErAlCondition;
//		targetEntity.krcstErAlApplication = domainAfterConvert.krcstErAlApplication;
//		targetEntity.kwrmtErAlWorkRecordPK = domainAfterConvert.kwrmtErAlWorkRecordPK;
//		targetEntity.messageColor = domainAfterConvert.messageColor;
//		targetEntity.typeAtr = domainAfterConvert.typeAtr;
//		targetEntity.useAtr = domainAfterConvert.useAtr;
//		targetEntity.remarkCancelErrorInput = domainAfterConvert.remarkCancelErrorInput;
//		targetEntity.remarkColumnNo = domainAfterConvert.remarkColumnNo;
		this.commandProxy().update(domainAfterConvert);
	}

	@Override
	public void removeErrorAlarmWorkRecord(String code) {
		this.commandProxy().remove(KwrmtErAlWorkRecord.class,
				new KwrmtErAlWorkRecordPK(AppContexts.user().companyId(), code));
	}

    @Override
    public Optional<ErrorAlarmWorkRecord> findByErrorAlamCheckId(String eralCheckId) {
        Optional<KwrmtErAlWorkRecord> entity = this.queryProxy().query(FIND_BY_ERROR_ALARM_CHECK_ID, KwrmtErAlWorkRecord.class)
                .setParameter("companyId", AppContexts.user().companyId())
                .setParameter("eralCheckId", eralCheckId).getSingle();
                return Optional.ofNullable(entity.isPresent() ? KwrmtErAlWorkRecord.toDomain(entity.get()) : null);
    }

	@Override
	public List<ErrorAlarmWorkRecord> findByListErrorAlamCheckId(List<String> listEralCheckId) {
		List<ErrorAlarmWorkRecord> data = new ArrayList<>();
		for(String eralCheckId : listEralCheckId ) {
			Optional<ErrorAlarmWorkRecord> errorAlarmWorkRecord = this.findByErrorAlamCheckId(eralCheckId);
			if(errorAlarmWorkRecord.isPresent()) {
				data.add(errorAlarmWorkRecord.get());
			}
		}
		return data;
	}
	
	@Override
    public Optional<ErrorAlarmCondition> findConditionByErrorAlamCheckId(String eralCheckId) {
        Optional<KwrmtErAlWorkRecord> entity = this.queryProxy().query(FIND_BY_ERROR_ALARM_CHECK_ID, KwrmtErAlWorkRecord.class)
                .setParameter("companyId", AppContexts.user().companyId())
                .setParameter("eralCheckId", eralCheckId).getSingle();
                return Optional.ofNullable(entity.isPresent() ? KwrmtErAlWorkRecord.toConditionDomain(entity.get()) : null);
    }

	@Override
	public List<ErrorAlarmCondition> findConditionByListErrorAlamCheckId(List<String> listEralCheckId) {
		List<ErrorAlarmCondition> data = new ArrayList<>();
		for(String eralCheckId : listEralCheckId ) {
			Optional<ErrorAlarmCondition> errorAlarmWorkRecord = this.findConditionByErrorAlamCheckId(eralCheckId);
			if(errorAlarmWorkRecord.isPresent()) {
				data.add(errorAlarmWorkRecord.get());
			}
		}
		return data;
	}
	
	@Override
	public List<ErrorAlarmCondition> getListErrorAlarmCondition(String companyId) {
		List<KwrmtErAlWorkRecord> lstData = this.queryProxy().query(FIND_BY_COMPANY, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId).getList();
		return lstData.stream().map(entity -> KwrmtErAlWorkRecord.toConditionDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public List<ErrorAlarmWorkRecord> getListErAlByListCode(String companyId, List<String> listCode) {
		List<ErrorAlarmWorkRecord> datas = new ArrayList<>();
		if (listCode.isEmpty())
			return new ArrayList<ErrorAlarmWorkRecord>();
		
		CollectionUtil.split(listCode, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			datas.addAll(this.queryProxy().query(SELECT_ERAL_BY_LIST_CODE, KwrmtErAlWorkRecord.class)
					.setParameter("listCode", subIdList).setParameter("companyId", companyId)
					.getList(c -> KwrmtErAlWorkRecord.toDomain(c)));
		});
		return datas;
	}

	@Override
	public List<ErrorAlarmWorkRecord> getAllErAlCompany(String companyId) {
		return this.queryProxy().query(FIND_ALL_ER_AL_COMPANY, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId).getList(c -> {
					ErrorAlarmWorkRecord record = KwrmtErAlWorkRecord.toDomain(c);
					record.setErrorAlarmCondition(KwrmtErAlWorkRecord.toConditionDomain(c));
					return record;
				}
				);
	}

	@Override
	public List<ErrorAlarmWorkRecord> getListErAlByListCodeError(String companyId, List<String> listCode) {
		List<ErrorAlarmWorkRecord> datas = new ArrayList<>();
		if (listCode.isEmpty())
			return new ArrayList<ErrorAlarmWorkRecord>();
		
		CollectionUtil.split(listCode, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			datas.addAll(this.queryProxy().query(SELECT_ERAL_BY_LIST_CODE_ERROR, KwrmtErAlWorkRecord.class)
					.setParameter("listCode", subIdList).setParameter("companyId", companyId)
					.getList(c -> KwrmtErAlWorkRecord.toDomain(c)));
		});
		return datas;
	}

	@Override
	public List<ErrorAlarmWorkRecord> getAllErAlCompanyAndUseAtr(String companyId, boolean useAtr) {
		return this.queryProxy().query(FIND_BY_COMPANY_AND_USEATR, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId)
				.setParameter("useAtr", useAtr ? 1 : 0).getList(c -> {
					ErrorAlarmWorkRecord record = KwrmtErAlWorkRecord.toDomain(c);
					record.setErrorAlarmCondition(KwrmtErAlWorkRecord.toConditionDomain(c));
					return record;
				});
	}
	
	@Override
	public List<ErrorAlarmWorkRecord> getAllErAlCompanyAndUseAtrV2(String companyId, boolean useAtr) {
		StringBuilder builder = new StringBuilder("SELECT a, eac, eaa FROM KwrmtErAlWorkRecord a");
		builder.append(" LEFT JOIN a.krcmtErAlCondition eac ");
		builder.append(" LEFT JOIN a.krcstErAlApplication eaa ");
		builder.append(" WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId AND a.useAtr = :useAtr ");
		return this.queryProxy().query(builder.toString(), Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("useAtr", useAtr ? 1 : 0).getList()
				.stream().collect(Collectors.groupingBy(c -> c[0], Collectors.toList()))
				.entrySet().stream().map(e -> {
					KwrmtErAlWorkRecord eralRecord = (KwrmtErAlWorkRecord) e.getKey();
					List<KrcstErAlApplication> eralApp = e.getValue().stream().filter(al -> al[2] != null)
							.map(al -> (KrcstErAlApplication) al[2]).collect(Collectors.toList());
					KrcmtErAlCondition eralCon = e.getValue().stream().filter(al -> al[1] != null).findFirst().map(al -> (KrcmtErAlCondition) al[1]).orElse(null);
					ErrorAlarmWorkRecord record = KwrmtErAlWorkRecord.toDomain(eralRecord, eralApp);
					record.setErrorAlarmCondition(KwrmtErAlWorkRecord.toConditionDomain(eralRecord, eralCon));
					return record;
				}).collect(Collectors.toList());
	}

	@Override
	public List<Map<String, Object>> getErAlByComID(String companyId) {
		StringBuilder builder = new StringBuilder("SELECT a.errorDisplayItem, a.kwrmtErAlWorkRecordPK.errorAlarmCode, a.krcmtErAlCondition");
		builder.append(" FROM KwrmtErAlWorkRecord a");
		builder.append(" WHERE a.kwrmtErAlWorkRecordPK.companyId = :companyId ");
		builder.append(" AND a.useAtr = 1 AND a.typeAtr IN (0,1)");
		builder.append(" AND a.errorDisplayItem IS NOT NULL");
		builder.append(" AND a.krcmtErAlCondition IS NOT NULL");
		return this.queryProxy().query(builder.toString(), Object[].class)
				.setParameter("companyId", companyId).getList(c -> {
					ErrorAlarmCondition codition = KrcmtErAlCondition.toDomain((KrcmtErAlCondition) c[2], companyId, c[1].toString());
					Map<String, Object> mapped = new HashMap<>();
					mapped.put("Code", c[1].toString());
					mapped.put("ErrorDisplayItem", (int) c[0]);
					mapped.put("ErrorAlarmCondition", codition);
					return mapped;
				});
	}
	
	@Override
	public List<ErrorAlarmWorkRecord> getListErrorAlarmWorkRecord(String companyId, int fixed) {
		List<KwrmtErAlWorkRecord> lstData = this.queryProxy()
				.query(FIND_BY_COMPANY + " AND a.fixedAtr = :fixedAtr ", KwrmtErAlWorkRecord.class)
				.setParameter("companyId", companyId).setParameter("fixedAtr", fixed).getList();
		return lstData.stream().map(entity -> {
			ErrorAlarmWorkRecord record = KwrmtErAlWorkRecord.toDomain(entity);
			record.setErrorAlarmCondition(KwrmtErAlWorkRecord.toConditionDomain(entity));
			return record;
		}).collect(Collectors.toList());
	}

}
