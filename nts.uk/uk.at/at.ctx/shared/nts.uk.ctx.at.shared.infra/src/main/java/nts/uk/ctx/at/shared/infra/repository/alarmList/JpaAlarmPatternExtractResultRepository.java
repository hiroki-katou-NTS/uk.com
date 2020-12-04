package nts.uk.ctx.at.shared.infra.repository.alarmList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmExtracResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmPatternExtractResult;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmPatternExtractResultRepository;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.infra.entity.alarmList.KfndtAlarmExtracResult;
import nts.uk.ctx.at.shared.infra.entity.alarmList.KfndtAlarmExtracResultPK;
@Stateless
public class JpaAlarmPatternExtractResultRepository  extends JpaRepository implements AlarmPatternExtractResultRepository{

	@Override
	@SneakyThrows
	public Optional<AlarmPatternExtractResult> optAlarmPattern(String cid, String runCode, String patternCode) {
		String sql = "SELECT * FROM KFNDT_ALARM_EXTRAC_RESULT"
				+ " WHERE CID = @cid"
				+ " AND AUTORUN_CODE = @runCode"
				+ " AND PATTERN_CODE = @patternCode";
		
		List<NtsResultRecord> mapLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", cid)
				.paramString("runCode", runCode)
				.paramString("patternCode", patternCode)
				.getList(rec -> rec);
		Optional<AlarmPatternExtractResult> result = toObject(mapLst);		
		return result;
	}

	private Optional<AlarmPatternExtractResult> toObject(List<NtsResultRecord> lstRec) {
		ResultOfEachCondition eachCond = new ResultOfEachCondition(AlarmListCheckType.Excess,"",new ArrayList<>());
		AlarmExtracResult extracResult = new AlarmExtracResult("", 0, new ArrayList<>());
		AlarmPatternExtractResult result = new AlarmPatternExtractResult("","","","",new ArrayList<>());
		if(lstRec.isEmpty()) {
			return Optional.empty();
		}
		lstRec.stream().forEach(rec ->{
			String cid = rec.getString("CID");
			String runCode = rec.getString("AUTORUN_CODE");
			String patternCode = rec.getString("PATTERN_CODE");
			int category = rec.getInt("CATEGORY");
			String condCode = rec.getString("ALARM_CHECK_CODE");
			String condNo = rec.getString("CONDITION_NO");
			AlarmListCheckType chekType = EnumAdaptor.valueOf(rec.getInt("CHECK_ATR"), AlarmListCheckType.class);
			ExtractionAlarmPeriodDate periodDate = new ExtractionAlarmPeriodDate(rec.getGeneralDate("START_DATE"), 
					Optional.ofNullable(rec.getGeneralDate("END_DATE")));
			ExtractionResultDetail detail = new ExtractionResultDetail(rec.getString("SID")
					,periodDate
					,rec.getString("ALARM_ITEM_NAME")
					,rec.getString("ALARM_CONTENT")
					,rec.getGeneralDateTime("RUN_TIME")
					,Optional.ofNullable(rec.getString("WORKPLACE_ID"))
					,Optional.ofNullable(rec.getString("COMMENT"))
					,Optional.ofNullable(rec.getString("CHECK_VALUE")));
			eachCond.setCheckType(chekType);
			eachCond.setNo(condNo);
			eachCond.getLstResultDetail().add(detail);
			
			extracResult.setCategory(category);
			extracResult.setCoditionCode(condCode);
			extracResult.getLstResult().add(eachCond);
			
			if(result.getCID().isEmpty()) {				
				result.setCID(cid);
				result.setPatternCode(patternCode);
				result.setPatternName("");
				result.setRunCode(runCode);
				result.setLstExtracResult(Arrays.asList(extracResult));
			} else {
				List<AlarmExtracResult> lstExtracResult = new ArrayList<>(result.getLstExtracResult());
				lstExtracResult.stream().forEach(ex -> {
					if(ex.getCategory() == category && ex.getCoditionCode().equals(condCode)) {
						List<ResultOfEachCondition> lstResult = new ArrayList<>(ex.getLstResult());
						lstResult.stream().forEach(re -> {
							if(re.getNo().equals(condNo) && re.getCheckType() == chekType) {
								re.getLstResultDetail().add(detail);
							} else {
								ex.getLstResult().add(eachCond);
							}
						});
					} else {
						result.getLstExtracResult().add(extracResult);
					}
				});
			}
		});
		
		return Optional.ofNullable(result);
	}

	@Override
	public void addAlarmPattern(AlarmPatternExtractResult alarmPattern) {
		if(alarmPattern == null || alarmPattern.getLstExtracResult().isEmpty()) {
			return;
		}
		List<KfndtAlarmExtracResult> lstAlarm = this.toEntity(alarmPattern);
		this.commandProxy().insert(lstAlarm);		
	}
	
	private List<KfndtAlarmExtracResult> toEntity(AlarmPatternExtractResult alarmPattern) {
		List<KfndtAlarmExtracResult> lstResult = new ArrayList<>();
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
							x.getCategory(), 
							x.getCoditionCode(), 
							y.getCheckType().value,
							y.getNo(), 
							z.getSID(),
							z.getPeriodDate().getStartDate());
					
					lstResult.add(new KfndtAlarmExtracResult(pk, 
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
	public void deleteAlarmPattern(AlarmPatternExtractResult alarmPattern) {
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
							x.getCategory(), 
							x.getCoditionCode(), 
							y.getCheckType().value,
							y.getNo(),
							z.getSID(),
							z.getPeriodDate().getStartDate());
					lstpk.add(pk);
				});
			});
		});
		this.commandProxy().remove(lstpk);
	}

}
