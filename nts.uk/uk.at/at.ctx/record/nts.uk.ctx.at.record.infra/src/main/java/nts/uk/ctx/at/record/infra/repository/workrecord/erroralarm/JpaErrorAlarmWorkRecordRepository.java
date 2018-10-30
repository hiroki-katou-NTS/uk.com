/**
 * 5:11:24 PM Jul 24, 2017
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecordPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlApplication;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlApplicationPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlBusinessType;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlBusinessTypePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlClass;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlClassPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlEmployment;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlEmploymentPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlJobTitle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcstErAlJobTitlePK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemCon;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcmtErAlAtdItemConPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTarget;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlAtdTargetPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlConGroup;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlInputCheck;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleAtd;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleAtdPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhActual;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhPlan;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhPlanActualPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtActual;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtPlan;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtPlanActualPK;
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
		//get data to call KwrmtErAlWorkRecord.toDomain(c)
		List<KwrmtErAlWorkRecord> kwrmtErAlWorkRecords = new ArrayList<>();
		try (PreparedStatement statement = this.connection().prepareStatement(
				"SELECT s.CID, s.ERROR_ALARM_CD, s.ERROR_ALARM_NAME, s.FIXED_ATR, s.USE_ATR,"
						+ " s.REMARK_CANCEL_ERR_INP, s.REMARK_COLUMN_NO, s.ERAL_ATR,"
						+ " s.BOLD_ATR, s.MESSAGE_COLOR, s.CANCELABLE_ATR, s.ERROR_DISPLAY_ITEM,"
						+ " s.ERAL_CHECK_ID, a.ERROR_CD, a.APP_TYPE_CD "
						+ " FROM KRCMT_ERAL_SET s LEFT JOIN KRCST_ER_AL_APPLICATION a "
						+ " ON s.CID = a.CID AND s.ERROR_ALARM_CD = a.ERROR_CD "
						+ " WHERE s.CID = ? AND s.USE_ATR = ?")) {
			statement.setString(1, companyId);
			statement.setBoolean(2, useAtr);
			new NtsResultSet(statement.executeQuery()).getList(rs -> {
				KwrmtErAlWorkRecord kwrmtErAlWorkRecord = new KwrmtErAlWorkRecord();
				KwrmtErAlWorkRecordPK kwrmtErAlWorkRecordPK = new KwrmtErAlWorkRecordPK(
						rs.getString("CID"), rs.getString("ERROR_ALARM_CD")); 
				kwrmtErAlWorkRecord.kwrmtErAlWorkRecordPK = kwrmtErAlWorkRecordPK;
				kwrmtErAlWorkRecord.errorAlarmName = rs.getString("ERROR_ALARM_NAME");
				kwrmtErAlWorkRecord.fixedAtr = rs.getInt("FIXED_ATR");
				kwrmtErAlWorkRecord.useAtr = rs.getInt("USE_ATR");
				kwrmtErAlWorkRecord.remarkCancelErrorInput = rs.getInt("REMARK_CANCEL_ERR_INP");
				kwrmtErAlWorkRecord.remarkColumnNo = rs.getInt("REMARK_COLUMN_NO");
				kwrmtErAlWorkRecord.typeAtr = rs.getInt("ERAL_ATR");
				kwrmtErAlWorkRecord.boldAtr = rs.getInt("BOLD_ATR");
				kwrmtErAlWorkRecord.messageColor = rs.getString("MESSAGE_COLOR");
				kwrmtErAlWorkRecord.cancelableAtr = rs.getInt("CANCELABLE_ATR");
				kwrmtErAlWorkRecord.errorDisplayItem = rs.getInt("ERROR_DISPLAY_ITEM");
				kwrmtErAlWorkRecord.eralCheckId = rs.getString("ERAL_CHECK_ID");
				//create KrcstErAlApplication
				if(rs.getString("ERROR_CD") != null){
					KrcstErAlApplication krcstErAlApplication = new KrcstErAlApplication();
					KrcstErAlApplicationPK krcstErAlApplicationPK = new KrcstErAlApplicationPK(
							rs.getString("CID"), rs.getString("ERROR_CD"), rs.getInt("APP_TYPE_CD"));
					krcstErAlApplication.krcstErAlApplicationPK = krcstErAlApplicationPK;
					//add into KwrmtErAlWorkRecord
					kwrmtErAlWorkRecord.oneKrcstErAlApplication = krcstErAlApplication;					
				}
				//add KwrmtErAlWorkRecord
				kwrmtErAlWorkRecords.add(kwrmtErAlWorkRecord);
				return null;
			});
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
		Set<String> eralCheckId = new HashSet<>();
		// get ERAL_CHECK_ID
		try (PreparedStatement statement = this.connection().prepareStatement(
				"SELECT s.ERAL_CHECK_ID, s.CID, s.ERROR_ALARM_CD FROM KRCMT_ERAL_SET s "
						+ " WHERE s.CID = ? AND s.USE_ATR = ?")) {
			statement.setString(1, companyId);
			statement.setBoolean(2, useAtr);
			new NtsResultSet(statement.executeQuery()).getList(rs -> {
				eralCheckId.add(rs.getString("ERAL_CHECK_ID"));
				return null;
			});
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
		// get data to call KwrmtErAlWorkRecord.toConditionDomain(c)
		List<KrcmtErAlCondition> erAlConditions = new ArrayList<>();
		List<String> conditionGroup1Ids = new ArrayList<>();
		List<String> conditionGroup2Ids = new ArrayList<>();
		CollectionUtil.split(eralCheckId.stream().collect(Collectors.toList()), DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			try (PreparedStatement statement = this.connection().prepareStatement(
					"SELECT a.ERAL_CHECK_ID, a.MESSAGE_DISPLAY, a.CONTINUOUS_PERIOD, a.FILTER_BY_BUSINESS_TYPE, a.WT_COMPARE_ATR, "
					+ " a.FILTER_BY_JOB_TITLE, a.FILTER_BY_EMPLOYMENT, a.FILTER_BY_CLASSIFICATION, a.ATD_ITEM_CONDITION_GROUP1, "
					+ " a.WT_ACTUAL_FILTER_ATR, a.WT_PLAN_ACTUAL_OPERATOR, a.WH_PLAN_ACTUAL_OPERATOR, "
					+ " a.ATD_ITEM_CONDITION_GROUP2, b.BUSINESS_TYPE_CD, c.JOB_ID, d.EMPTCD, e.CLSCD, "
					+ " a.WORKTYPE_USE_ATR, a.WT_COMPARE_ATR, a.WT_PLAN_FILTER_ATR, "
					+ " f.WORKTYPE_CD as wtPlanCD, g.WORKTYPE_CD as wtActualCD, a.WORKING_HOURS_USE_ATR, a.WH_COMPARE_ATR, "
					+ " a.WH_PLAN_FILTER_ATR, h.WORK_TIME_CD as whPlanWtCD, a.WH_ACTUAL_FILTER_ATR, "
					+ " i.WORK_TIME_CD as whActualWtCD, a.OPERATOR_BETWEEN_GROUPS, a.GROUP2_USE_ATR "
					+ " FROM KRCMT_ERAL_CONDITION a "
					+ " LEFT JOIN KRCST_ER_AL_BUSINESS_TYPE b ON a.ERAL_CHECK_ID = b.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_JOB_TITLE c ON a.ERAL_CHECK_ID = c.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_EMPLOYMENT d  ON a.ERAL_CHECK_ID = d.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_CLASS e  ON a.ERAL_CHECK_ID = e.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_WT_PLAN f  ON a.ERAL_CHECK_ID = f.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_WT_ACTUAL g  ON a.ERAL_CHECK_ID = g.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_WH_PLAN h  ON a.ERAL_CHECK_ID = h.ERAL_CHECK_ID "
					+ " LEFT JOIN KRCST_ER_AL_WH_ACTUAL i  ON a.ERAL_CHECK_ID = i.ERAL_CHECK_ID "
					+ " WHERE a.ERAL_CHECK_ID IN (" + subList.stream().map(s -> "?").collect(Collectors.joining(",")) + ")")) {
				
				for (int i = 0; i < subList.size(); i++) {
					statement.setString(1 + i, subList.get(i));
				}
				new NtsResultSet(statement.executeQuery()).getList(rs -> {
					if(rs.getString("ATD_ITEM_CONDITION_GROUP1") != null) {
						conditionGroup1Ids.add(rs.getString("ATD_ITEM_CONDITION_GROUP1"));
					}
					if(rs.getString("ATD_ITEM_CONDITION_GROUP2") != null) {
						conditionGroup2Ids.add(rs.getString("ATD_ITEM_CONDITION_GROUP2"));
					}
					KrcmtErAlCondition erAlCondition = new KrcmtErAlCondition();
					erAlCondition.eralCheckId = rs.getString("ERAL_CHECK_ID");
					erAlCondition.atdItemConditionGroup1 = rs.getString("ATD_ITEM_CONDITION_GROUP1");
					erAlCondition.atdItemConditionGroup2 = rs.getString("ATD_ITEM_CONDITION_GROUP2");
					erAlCondition.messageDisplay = rs.getString("MESSAGE_DISPLAY");
					erAlCondition.operatorBetweenGroups = rs.getInt("OPERATOR_BETWEEN_GROUPS") != null ? rs.getInt("OPERATOR_BETWEEN_GROUPS") : 0;
					erAlCondition.group2UseAtr = rs.getInt("GROUP2_USE_ATR") != null ? rs.getInt("GROUP2_USE_ATR") : 0;
					erAlCondition.continuousPeriod = rs.getInt("CONTINUOUS_PERIOD") != null ? rs.getInt("CONTINUOUS_PERIOD") : 0;
					erAlCondition.filterByBusinessType = rs.getInt("FILTER_BY_BUSINESS_TYPE") != null ? rs.getInt("FILTER_BY_BUSINESS_TYPE") : 0;
					erAlCondition.filterByJobTitle = rs.getInt("FILTER_BY_JOB_TITLE")  != null ? rs.getInt("FILTER_BY_JOB_TITLE") : 0;
					erAlCondition.filterByEmployment = rs.getInt("FILTER_BY_EMPLOYMENT") != null ? rs.getInt("FILTER_BY_EMPLOYMENT") : 0;
					erAlCondition.filterByClassification = rs.getInt("FILTER_BY_CLASSIFICATION") != null ? rs.getInt("FILTER_BY_CLASSIFICATION") : 0;
					if(rs.getString("BUSINESS_TYPE_CD") != null) {
						KrcstErAlBusinessType alBusinessType = new KrcstErAlBusinessType();
						KrcstErAlBusinessTypePK alBusinessTypePK = new KrcstErAlBusinessTypePK();
						alBusinessTypePK.businessTypeCd = rs.getString("BUSINESS_TYPE_CD");
						alBusinessType.krcstErAlBusinessTypePK = alBusinessTypePK;
						erAlCondition.businessType = alBusinessType;
					}
					if(rs.getString("JOB_ID") != null) {
						KrcstErAlJobTitle alJobTitle = new KrcstErAlJobTitle();
						KrcstErAlJobTitlePK alJobTitlePK = new KrcstErAlJobTitlePK();
						alJobTitlePK.jobId = rs.getString("JOB_ID");
						alJobTitle.krcstErAlJobTitlePK = alJobTitlePK;
						erAlCondition.jobTitle = alJobTitle;
					}
					if(rs.getString("EMPTCD") != null) {
						KrcstErAlEmployment alEmployment = new KrcstErAlEmployment();
						KrcstErAlEmploymentPK alEmploymentPK = new KrcstErAlEmploymentPK();
						alEmploymentPK.emptcd = rs.getString("EMPTCD");
						alEmployment.krcstErAlEmploymentPK = alEmploymentPK;
						erAlCondition.employment = alEmployment;
					}
					if(rs.getString("CLSCD") != null) {
						KrcstErAlClass alClass = new KrcstErAlClass();
						KrcstErAlClassPK alClassPK = new KrcstErAlClassPK();
						alClassPK.clscd = rs.getString("CLSCD");
						alClass.krcstErAlClassPK = alClassPK;
						erAlCondition.classification = alClass;
					}
					erAlCondition.workTypeUseAtr = rs.getInt("WORKTYPE_USE_ATR") != null ? rs.getInt("WORKTYPE_USE_ATR") : 0;
					erAlCondition.wtCompareAtr = rs.getInt("WT_COMPARE_ATR");
					erAlCondition.wtPlanFilterAtr = rs.getInt("WT_PLAN_FILTER_ATR");
					if(rs.getString("wtPlanCD") != null) {
						KrcstErAlWtPlan alWtPlan = new KrcstErAlWtPlan();
						KrcstErAlWtPlanActualPK actualPK = new KrcstErAlWtPlanActualPK();
						actualPK.workTypeCode = rs.getString("wtPlanCD");
						alWtPlan.krcstErAlWtPlanPK = actualPK;
						erAlCondition.wtPlan = alWtPlan;
					}
					erAlCondition.wtActualFilterAtr = rs.getInt("WT_ACTUAL_FILTER_ATR");
					if(rs.getString("wtActualCD") != null){
						KrcstErAlWtActual actual = new  KrcstErAlWtActual();
						KrcstErAlWtPlanActualPK actualPK = new KrcstErAlWtPlanActualPK();
						actualPK.workTypeCode = rs.getString("wtActualCD");
						actual.krcstErAlWtPlanActualPK = actualPK;
						erAlCondition.wtActual = actual;
					}
					erAlCondition.wtPlanActualOperator = rs.getInt("WT_PLAN_ACTUAL_OPERATOR") != null ? rs.getInt("WT_PLAN_ACTUAL_OPERATOR") : 0;
					erAlCondition.workingHoursUseAtr = rs.getInt("WORKING_HOURS_USE_ATR") != null ? rs.getInt("WORKING_HOURS_USE_ATR") : 0;
					erAlCondition.whCompareAtr = rs.getInt("WH_COMPARE_ATR");
					erAlCondition.whPlanFilterAtr = rs.getInt("WH_PLAN_FILTER_ATR");
					if(rs.getString("whPlanWtCD") != null) {
						KrcstErAlWhPlan alWhPlan = new KrcstErAlWhPlan();
						KrcstErAlWhPlanActualPK actualPK = new KrcstErAlWhPlanActualPK();
						actualPK.workTimeCode = rs.getString("whPlanWtCD");
						alWhPlan.krcstErAlWhPlanActualPK = actualPK;
						erAlCondition.whPlan = alWhPlan;
					}
					erAlCondition.whActualFilterAtr = rs.getInt("WH_ACTUAL_FILTER_ATR");
					if(rs.getString("whActualWtCD") != null) {
						KrcstErAlWhActual actual = new KrcstErAlWhActual();
						KrcstErAlWhPlanActualPK actualPK = new KrcstErAlWhPlanActualPK();
						actualPK.workTimeCode = rs.getString("whActualWtCD");
						actual.krcstErAlWhPlanActualPK = actualPK;
						erAlCondition.whActual = actual;
					}
					erAlCondition.whPlanActualOperator = rs.getInt("WH_PLAN_ACTUAL_OPERATOR") != null ? rs.getInt("WH_PLAN_ACTUAL_OPERATOR") : 0;
					erAlConditions.add(erAlCondition);
					return null;
				});
				
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			
		});
		
		// get condition group 1
		Map<List<KrcstErAlConGroup>, List<KrcmtErAlAtdItemCon>> conditionGroup1 = getConditionGroup(conditionGroup1Ids);

		// get condition group 2
		Map<List<KrcstErAlConGroup>, List<KrcmtErAlAtdItemCon>> conditionGroup2 = getConditionGroup(conditionGroup2Ids);
		// map KRCMT_ERAL_CONDITION
		Map<String, List<KrcmtErAlCondition>> mapErAlConditions = erAlConditions.stream().
				collect(Collectors.groupingBy(x->x.eralCheckId));
		//create result
		List<ErrorAlarmWorkRecord> results = new ArrayList<>();
		Map<String,List<KwrmtErAlWorkRecord>> mapKwrmtErAlWorkRecord = kwrmtErAlWorkRecords.stream().
				collect(Collectors.groupingBy(x -> x.kwrmtErAlWorkRecordPK.errorAlarmCode));
		mapKwrmtErAlWorkRecord.forEach((key, value) -> {
			//create domain of  KRCMT_ERAL_SET and KRCST_ER_AL_APPLICATION
			ErrorAlarmWorkRecord record = KwrmtErAlWorkRecord.toDomainForRes(value);
			List<KrcmtErAlCondition>  listerAlCondition = null;
			if(value.get(0).eralCheckId != null) {
				listerAlCondition = mapErAlConditions.get(value.get(0).eralCheckId);
			}
			//create domain of KRCMT_ERAL_CONDITION
			record.setErrorAlarmCondition(KwrmtErAlWorkRecord.toConditionDomainForRes(value.get(0), listerAlCondition,
					 conditionGroup1, conditionGroup2));
			results.add(record);
		});
		
		return results;
	
//		return this.queryProxy().query(FIND_BY_COMPANY_AND_USEATR, KwrmtErAlWorkRecord.class)
//				.setParameter("companyId", companyId)
//				.setParameter("useAtr", useAtr ? 1 : 0).getList(c -> {
//					ErrorAlarmWorkRecord record = KwrmtErAlWorkRecord.toDomain(c);
//					record.setErrorAlarmCondition(KwrmtErAlWorkRecord.toConditionDomain(c));
//					return record;
//				});
	}
	
	private Map<List<KrcstErAlConGroup>, List<KrcmtErAlAtdItemCon>> getConditionGroup(List<String> conditionGroupId) {
		Map<List<KrcstErAlConGroup>, List<KrcmtErAlAtdItemCon>> result = new HashMap<>();
		List<KrcstErAlConGroup> krcstErAlConGroup = new ArrayList<>();
		Map<String, Integer> conGroup1IdNos = new HashMap<>();
//		Set<String> conGroupIds = new HashSet<>();
//		Set<Integer> conGroup1Nos = new HashSet<>();
		if(!conditionGroupId.isEmpty()) {
			CollectionUtil.split(conditionGroupId.stream().collect(Collectors.toList()), DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
				try (PreparedStatement statement = this.connection().prepareStatement(
						"SELECT s.CONDITION_GROUP_ID, s.CONDITION_OPERATOR, a.CONDITION_GROUP_ID as childId, "
						+ " a.ATD_ITEM_CON_NO, a.CONDITION_ATR, a.USE_ATR, a.CONDITION_TYPE "
						+ " FROM KRCST_ER_AL_CON_GROUP s LEFT JOIN KRCMT_ER_AL_ATD_ITEM_CON a "
						+ " ON s.CONDITION_GROUP_ID = a.CONDITION_GROUP_ID "
						+ " WHERE s.CONDITION_GROUP_ID IN (" + subList.stream().map(s -> "?").collect(Collectors.joining(",")) + ")")) {
					for (int i = 0; i < subList.size(); i++) {
						statement.setString(1 + i, subList.get(i));
					}
					new NtsResultSet(statement.executeQuery()).getList(rs -> {
						KrcstErAlConGroup alConGroup = new KrcstErAlConGroup();
						alConGroup.conditionGroupId = rs.getString("CONDITION_GROUP_ID");
						alConGroup.conditionOperator = rs.getInt("CONDITION_OPERATOR");
						if(rs.getString("childId") != null) {
							KrcmtErAlAtdItemCon alAtdItemCon = new KrcmtErAlAtdItemCon();
							KrcmtErAlAtdItemConPK alAtdItemConPK = new KrcmtErAlAtdItemConPK();
							alAtdItemConPK.conditionGroupId = rs.getString("childId");
							//conGroupIds.add(rs.getString("childId"));
							alAtdItemConPK.atdItemConNo = rs.getInt("ATD_ITEM_CON_NO");
							//conGroup1Nos.add(rs.getInt("ATD_ITEM_CON_NO"));
							
							conGroup1IdNos.put(rs.getString("childId"), rs.getInt("ATD_ITEM_CON_NO"));
							alAtdItemCon.krcmtErAlAtdItemConPK = alAtdItemConPK;
							alAtdItemCon.conditionAtr = rs.getInt("CONDITION_ATR");
							alAtdItemCon.useAtr = rs.getInt("USE_ATR");
							alAtdItemCon.type = rs.getInt("CONDITION_TYPE");
						}
						
						krcstErAlConGroup.add(alConGroup);
						return null;
					});
					
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			});
		}
		
		//get children of group 
		List<KrcmtErAlAtdItemCon> alAtdItemConsGroup = new ArrayList<>();
		if(!conGroup1IdNos.isEmpty()) {
			CollectionUtil.split(conGroup1IdNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT/2, (subList) -> {
				try (PreparedStatement statement = this.connection().prepareStatement(
						"SELECT s.CONDITION_GROUP_ID, s.ATD_ITEM_CON_NO, s.CONDITION_ATR, s.USE_ATR, "
						+ " a.TARGET_ATR, "
						+ " a.ATTENDANCE_ITEM_ID, b.CONDITION_GROUP_ID as compareRanID, b.COMPARE_ATR, "
						+ " b.START_VALUE, b.END_VALUE, "
						+ " f.COMPARE_ATR as eraComSingleAtr, f.CONDITION_TYPE as eraComSingleCType, "
						+ " c.FIXED_VALUE, d.ATTENDANCE_ITEM_ID as erSingID, e.INPUT_CHECK "
						+ " FROM KRCMT_ER_AL_ATD_ITEM_CON s "
						+ " LEFT JOIN KRCST_ER_AL_ATD_TARGET a "
						+ " ON s.CONDITION_GROUP_ID = a.CONDITION_GROUP_ID "
						+ " AND s.ATD_ITEM_CON_NO = a.ATD_ITEM_CON_NO "
						+ " LEFT JOIN KRCST_ERAL_COMPARE_RANGE b "
						+ " ON s.CONDITION_GROUP_ID = b.CONDITION_GROUP_ID "
						+ " AND s.ATD_ITEM_CON_NO = b.ATD_ITEM_CON_NO "
						+ " LEFT JOIN KRCST_ERAL_COMPARE_SINGLE f "
						+ " ON s.CONDITION_GROUP_ID = f.CONDITION_GROUP_ID "
						+ " AND s.ATD_ITEM_CON_NO = f.ATD_ITEM_CON_NO "
						+ " LEFT JOIN KRCST_ERAL_SINGLE_FIXED c "
						+ " ON s.CONDITION_GROUP_ID = c.CONDITION_GROUP_ID "
						+ " AND s.ATD_ITEM_CON_NO = c.ATD_ITEM_CON_NO "
						+ " LEFT JOIN KRCST_ERAL_SINGLE_ATD d "
						+ " ON s.CONDITION_GROUP_ID = d.CONDITION_GROUP_ID "
						+ " AND s.ATD_ITEM_CON_NO = d.ATD_ITEM_CON_NO "
						+ " LEFT JOIN KRCST_ERAL_INPUT_CHECK e "
						+ " ON s.CONDITION_GROUP_ID = e.CONDITION_GROUP_ID "
						+ " AND s.ATD_ITEM_CON_NO = e.ATD_ITEM_CON_NO "
						+ " WHERE s.CONDITION_GROUP_ID IN (" + subList.keySet().stream().map(s -> "?").collect(Collectors.joining(",")) + ") "
						+ " AND s.ATD_ITEM_CON_NO IN (" + subList.values().stream().map(s -> "?").collect(Collectors.joining(",")) + ")")) {
					for (int i = 0; i < subList.size(); i++) {
						statement.setString(1 + i, subList.keySet().stream().collect(Collectors.toList()).get(i));
					}
					for (int i = 0; i < subList.size(); i++) {
						statement.setInt(subList.size() + 1 + i, subList.values().stream().collect(Collectors.toList()).get(i));
					}
					new NtsResultSet(statement.executeQuery()).getList(rs -> {
						KrcmtErAlAtdItemCon alAtdItemCon = new KrcmtErAlAtdItemCon();
						KrcmtErAlAtdItemConPK alAtdItemConPK = new KrcmtErAlAtdItemConPK();
						alAtdItemConPK.atdItemConNo = rs.getInt("ATD_ITEM_CON_NO");
						alAtdItemConPK.conditionGroupId = rs.getString("CONDITION_GROUP_ID");
						alAtdItemCon.krcmtErAlAtdItemConPK = alAtdItemConPK;
						
						alAtdItemCon.conditionAtr = rs.getInt("CONDITION_ATR");
						alAtdItemCon.useAtr = rs.getInt("USE_ATR");
						if(rs.getInt("ATTENDANCE_ITEM_ID") != null) {
							KrcstErAlAtdTarget alAtdTarget = new KrcstErAlAtdTarget();
							KrcstErAlAtdTargetPK alAtdTargetPK = new KrcstErAlAtdTargetPK();
							alAtdTargetPK.attendanceItemId = rs.getInt("ATTENDANCE_ITEM_ID");
							alAtdTarget.krcstErAlAtdTargetPK = alAtdTargetPK;
							alAtdTarget.targetAtr = rs.getInt("TARGET_ATR");
							alAtdItemCon.atdItemTarget = alAtdTarget;
						}
						if(rs.getString("compareRanID") != null) {
							KrcstErAlCompareRange alCompareRange = new KrcstErAlCompareRange();
							alCompareRange.compareAtr = rs.getInt("COMPARE_ATR");
							alCompareRange.startValue = rs.getDouble("START_VALUE");
							alCompareRange.endValue = rs.getDouble("END_VALUE");
							alAtdItemCon.erAlCompareRange = alCompareRange;
						}
						if(rs.getInt("eraComSingleAtr") != null) {
							KrcstErAlCompareSingle alCompareSingle = new KrcstErAlCompareSingle();
							alCompareSingle.compareAtr = rs.getInt("eraComSingleAtr");
							alCompareSingle.conditionType = rs.getInt("eraComSingleCType");
							alAtdItemCon.erAlCompareSingle = alCompareSingle;
						}
						if(rs.getDouble("FIXED_VALUE") != null) {
							KrcstErAlSingleFixed alSingleFixed = new KrcstErAlSingleFixed();
							alSingleFixed.fixedValue = rs.getDouble("FIXED_VALUE");
							alAtdItemCon.erAlSingleFixed = alSingleFixed;
						}
						if(rs.getInt("erSingID") != null) {
							KrcstErAlSingleAtd alSingleAtd = new KrcstErAlSingleAtd();
							KrcstErAlSingleAtdPK alSingleAtdPK = new KrcstErAlSingleAtdPK();
							alSingleAtdPK.attendanceItemId = rs.getInt("erSingID");
							alSingleAtd.krcstEralSingleAtdPK = alSingleAtdPK;
							alAtdItemCon.alSingleAtd = alSingleAtd;
						}
						if(rs.getInt("INPUT_CHECK") != null) {
							KrcstErAlInputCheck alInputCheck = new KrcstErAlInputCheck();
							alInputCheck.inputCheckCondition = rs.getInt("INPUT_CHECK");
							alAtdItemCon.erAlInputCheck = alInputCheck;
						}
						alAtdItemConsGroup.add(alAtdItemCon);
						return null;
					});
					
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			});
		}
		result.put(krcstErAlConGroup, alAtdItemConsGroup);
		return result;
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
