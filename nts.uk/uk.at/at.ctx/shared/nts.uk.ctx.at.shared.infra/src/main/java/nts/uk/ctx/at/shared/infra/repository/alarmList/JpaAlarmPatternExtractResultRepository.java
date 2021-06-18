package nts.uk.ctx.at.shared.infra.repository.alarmList;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmExtracResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmPatternExtractResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmPatternExtractResultRepository;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.infra.entity.alarmList.KfndtAlarmExtracResultOld;
import nts.uk.ctx.at.shared.infra.entity.alarmList.KfndtAlarmExtracResultPK;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaAlarmPatternExtractResultRepository  extends JpaRepository implements AlarmPatternExtractResultRepository{

	@Override
	@SneakyThrows
	public Optional<AlarmPatternExtractResult> optAlarmExtractResult(String cid, String runCode, String patternCode) {
		try(PreparedStatement sql = this.connection().prepareStatement("SELECT * FROM KFNDT_ALARM_EXTRAC_RESULT"
				+ " WHERE CID = ?"
				+ " AND AUTORUN_CODE = ?"
				+ " AND PATTERN_CODE = ?");
		)
		{
			sql.setString(1, cid);
			sql.setString(2, runCode);
			sql.setString(3, patternCode);			
			List<KfndtAlarmExtracResultOld> lstEntity = new NtsResultSet(sql.executeQuery())
					.getList(x -> toEntity(x));
			Optional<AlarmPatternExtractResult> result = this.toObject(lstEntity);
			return result;
		}
		
	}
	private KfndtAlarmExtracResultOld toEntity(NtsResultRecord rec){
		KfndtAlarmExtracResultPK pk = new KfndtAlarmExtracResultPK(rec.getString("CID"),
				rec.getString("AUTORUN_CODE"),
				rec.getString("PATTERN_CODE"),
				rec.getInt("CATEGORY"),
				rec.getString("ALARM_CHECK_CODE"),
				rec.getInt("CHECK_ATR"),
				rec.getString("CONDITION_NO"),
				rec.getString("SID"));
		KfndtAlarmExtracResultOld result = new KfndtAlarmExtracResultOld(pk,
				rec.getString("CONTRACT_CD"),
				rec.getGeneralDate("START_DATE"),
				rec.getGeneralDate("END_DATE"),
				rec.getString("PATTERN_NAME"),
				rec.getString("ALARM_ITEM_NAME"),
				rec.getString("ALARM_CONTENT"),
				rec.getGeneralDateTime("RUN_TIME"),
				rec.getString("WORKPLACE_ID"),
				rec.getString("COMMENT"), 
				rec.getString("CHECK_VALUE"));
		return result;
	}
	private Optional<AlarmPatternExtractResult> toObject(List<KfndtAlarmExtracResultOld> lstEntity) {
		if(lstEntity.isEmpty()) {
			Optional.empty();
		}
		
		List<AlarmExtracResult> lstExtracResult = new ArrayList<>();
		AlarmPatternExtractResult result = new AlarmPatternExtractResult("","","","", new ArrayList<>());
		
		lstEntity.stream().forEach(x -> {
			ResultOfEachCondition eachCond = new ResultOfEachCondition(AlarmListCheckType.Excess,"",new ArrayList<>());
			AlarmExtracResult extracResult = new AlarmExtracResult("", AlarmCategory.DAILY, new ArrayList<>());
			ExtractionAlarmPeriodDate periodDate = new ExtractionAlarmPeriodDate(Optional.ofNullable(x.getStartDate()), 
					Optional.ofNullable(x.getEndDate()));
			ExtractionResultDetail detail = new ExtractionResultDetail(x.getPk().getSid()
					,periodDate
					,x.getAlarmItemName()
					,x.getAlarmContent()
					,x.getRunTime()
					,Optional.ofNullable(x.getWpId())
					,Optional.ofNullable(x.getComment())
					,Optional.ofNullable(x.getCheckValue()));
			eachCond.setCheckType(EnumAdaptor.valueOf(x.getPk().getCheckAtr(), AlarmListCheckType.class));
			eachCond.setNo(x.getPk().getConditionNo().replace(" ", ""));
			eachCond.getLstResultDetail().add(detail);	
			extracResult.setCategory(EnumAdaptor.valueOf(x.getPk().getCategory(), AlarmCategory.class));
			extracResult.setCoditionCode(x.getPk().getAlarmCheckCode());
			extracResult.getLstResult().add(eachCond);
			if(result.getLstExtracResult().isEmpty()) {
				result.setCID(x.getPk().getCid());
				result.setPatternCode(x.getPk().getPatternCode());
				result.setPatternName(x.getPatternName());
				result.setRunCode(x.getPk().getAutoRunCode());
				result.getLstExtracResult().add(extracResult);
			} else {
				
				result.getLstExtracResult().stream().forEach(ex -> {
					if(ex.getCategory().value == x.getPk().getCategory() && ex.getCoditionCode().equals(x.getPk().getAlarmCheckCode())) {
						List<ResultOfEachCondition> lstResult = new ArrayList<>();
						ex.getLstResult().stream().forEach(re -> {
							if(re.getNo().equals(x.getPk().getConditionNo()) && re.getCheckType().value == x.getPk().getCheckAtr()) {
								re.getLstResultDetail().add(detail);
							} else {
								lstResult.add(eachCond);
							}
						});
						ex.getLstResult().addAll(lstResult);
					} else {
						lstExtracResult.add(extracResult);
					}
				});
			}
		});
		result.getLstExtracResult().addAll(lstExtracResult);
		return Optional.ofNullable(result);
	}

	@Override
	public void addAlarmExtractResult(AlarmPatternExtractResult alarmPattern) {
		if(alarmPattern == null || alarmPattern.getLstExtracResult().isEmpty()) {
			return;
		}
		List<KfndtAlarmExtracResultOld> lstAlarm = this.toEntity(alarmPattern);
		lstAlarm.stream().forEach(x -> {
			this.commandProxy().insert(x);	
		});
	}
	
	private List<KfndtAlarmExtracResultOld> toEntity(AlarmPatternExtractResult alarmPattern) {
		List<KfndtAlarmExtracResultOld> lstResult = new ArrayList<>();
		String cid = alarmPattern.getCID();
		String patternCd = alarmPattern.getPatternCode();
		String patternName = alarmPattern.getPatternName();
		String runCode = alarmPattern.getRunCode();
		alarmPattern.getLstExtracResult().stream().forEach(x -> {
			x.getLstResult().stream().forEach(y -> {
				y.getLstResultDetail().stream().forEach(z -> {
					KfndtAlarmExtracResultPK pk = new KfndtAlarmExtracResultPK(cid, 
							runCode, 
							patternCd, 
							x.getCategory().value, 
							x.getCoditionCode(), 
							y.getCheckType().value,
							y.getNo(), 
							z.getSID());
					
					lstResult.add(new KfndtAlarmExtracResultOld(pk,
							AppContexts.user().contractCode(),
							z.getPeriodDate().getStartDate().isPresent() ? z.getPeriodDate().getStartDate().get() : null,
							z.getPeriodDate().getEndDate().isPresent() ? z.getPeriodDate().getEndDate().get() : null, 
							patternName,
							z.getAlarmName(),
							z.getAlarmContent(),
							GeneralDateTime.now(),
							z.getWpID().isPresent() ? z.getWpID().get() : null,
							z.getComment().isPresent() ? z.getComment().get() : null,
							z.getCheckValue().isPresent() ? z.getCheckValue().get() : null));
				});
			});
		});
		
		return lstResult;
	}

	@Override
	public void deleteAlarmExtractResult(AlarmPatternExtractResult alarmPattern) {
		if(alarmPattern == null || alarmPattern.getLstExtracResult().isEmpty()) {
			return;
		}
		List<KfndtAlarmExtracResultPK> lstpk = new ArrayList<>();
		String cid = alarmPattern.getCID();
		String patternCd = alarmPattern.getPatternCode();
		String runCode = alarmPattern.getRunCode();
		alarmPattern.getLstExtracResult().stream().forEach(x -> {
			x.getLstResult().stream().forEach(y -> {
				y.getLstResultDetail().stream().forEach(z -> {
					KfndtAlarmExtracResultPK pk = new KfndtAlarmExtracResultPK(cid, 
							runCode, 
							patternCd, 
							x.getCategory().value, 
							x.getCoditionCode(), 
							y.getCheckType().value,
							y.getNo(),
							z.getSID());
					lstpk.add(pk);
				});
			});
		});
		this.commandProxy().remove(lstpk);
	}

}
