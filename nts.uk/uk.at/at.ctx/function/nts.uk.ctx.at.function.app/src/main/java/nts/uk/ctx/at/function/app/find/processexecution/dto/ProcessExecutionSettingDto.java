package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Process execution setting dto.<br>
 * Dto 更新処理実行設定
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessExecutionSettingDto {

	/**
	 * アラーム抽出
	 */
	private AlarmExtractionDto alarmExtraction;

	/**
	 * 個人スケジュール作成
	 */
	private PersonalScheduleCreationDto perSchedule;

	/**
	 * 日別実績の作成・計算
	 */
	private DailyPerformanceCreationDto dailyPerf;

	/**
	 * 承認結果反映
	 */
	private boolean reflectResultCls;

	/**
	 * 月別集計
	 */
	private boolean monthlyAggCls;

	/**
	 * 承認ルート更新（日次）
	 */
	private AppRouteUpdateDailyDto appRouteUpdateDaily;

	/**
	 * 承認ルート更新（月次）
	 */
	private boolean appRouteUpdateMonthly;

	/**
	 * データの削除
	 **/
	private DeleteDataDto deleteData;

	/**
	 * データの保存
	 **/
	private SaveDataDto saveData;

	/**
	 * 外部受入
	 **/
	private ExternalAcceptanceDto externalAcceptance;

	/**
	 * 外部出力
	 **/
	private ExternalOutputDto externalOutput;

	/**
	 * 任意期間の集計
	 **/
	private AggregationAnyPeriodDto aggrAnyPeriod;

	/**
	 * インデックス再構成
	 **/
	private IndexReconstructionDto indexReconstruction;

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Process execution setting dto
	 */
	public static ProcessExecutionSettingDto createFromDomain(ProcessExecutionSetting domain) {
		if (domain == null) {
			return null;
		}
		ProcessExecutionSettingDto dto = new ProcessExecutionSettingDto();
		dto.alarmExtraction = AlarmExtractionDto.createFromDomain(domain.getAlarmExtraction());
		dto.perSchedule = PersonalScheduleCreationDto.createFromDomain(domain.getPerScheduleCreation());
		dto.dailyPerf = DailyPerformanceCreationDto.createFromDomain(domain.getDailyPerf());
		dto.reflectResultCls = domain.getReflectAppResult().getReflectResultCls() == NotUseAtr.USE;
		dto.monthlyAggCls = domain.getMonthlyAggregate().getMonthlyAggCls() == NotUseAtr.USE;
		dto.appRouteUpdateDaily = AppRouteUpdateDailyDto.createFromDomain(domain.getAppRouteUpdateDaily());
		dto.appRouteUpdateMonthly = domain.getAppRouteUpdateMonthly().getAppRouteUpdateAtr() == NotUseAtr.USE;
		dto.deleteData = DeleteDataDto.createFromDomain(domain.getDeleteData());
		dto.saveData = SaveDataDto.createFromDomain(domain.getSaveData());
		dto.externalAcceptance = ExternalAcceptanceDto.createFromDomain(domain.getExternalAcceptance());
		dto.externalOutput = ExternalOutputDto.createFromDomain(domain.getExternalOutput());
		dto.aggrAnyPeriod = AggregationAnyPeriodDto.createFromDomain(domain.getAggrAnyPeriod());
		dto.indexReconstruction = IndexReconstructionDto.createFromDomain(domain.getIndexReconstruction());
		return dto;
	}

	/**
	 * Converts <code>ProcessExecutionSettingDto</code> to domain.
	 *
	 * @return the domain Process execution setting
	 */
	public ProcessExecutionSetting toDomain() {
		return new ProcessExecutionSetting(this.alarmExtraction.toDomain(), this.perSchedule.toDomain(),
				this.dailyPerf.toDomain(), this.reflectResultCls, this.monthlyAggCls,
				this.appRouteUpdateDaily.toDomain(), this.appRouteUpdateMonthly, this.deleteData.toDomain(),
				this.saveData.toDomain(), this.externalAcceptance.toDomain(), this.externalOutput.toDomain(),
				this.aggrAnyPeriod.toDomain(), this.indexReconstruction.toDomain());
	}

}
