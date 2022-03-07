package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.estimateamount.EstimateAmountDetailImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;

/**
 * @author sakuratani
 *
 *         月間賃金
 */
@Getter
@AllArgsConstructor
public class NRWebMonthWage {

	// 期間
	private DatePeriod period;

	// 目安
	private ItemValue measure;

	// 現在勤務
	private ItemValue currentWork;

	// 現在勤務残業
	private ItemValue currentOvertime;

	// 計画勤務
	private ItemValue scheduleWork;

	// 計画勤務残業
	private ItemValue scheduleOvertime;
	
	public void setMeasureAmount(long value) {
		this.measure = new ItemValue(this.measure.getTime(), value);
	}

	// [1] 最終勤務を取得
	public ItemValue getLastWork() {
		return new ItemValue(this.currentWork.getTime() + this.scheduleWork.getTime(),
				this.currentWork.getAmount() + this.scheduleWork.getAmount());
	}

	// [2] 最終勤務残業を取得
	public ItemValue getLastOverTime() {
		return new ItemValue(this.currentOvertime.getTime() + this.scheduleOvertime.getTime(),
				this.currentOvertime.getAmount() + this.scheduleOvertime.getAmount());
	}

	// [3] 最終勤務差分を取得
	public ItemValue getLastWorkDiff(List<EstimateAmountDetailImport> dataAmountSetting) {
		int time = getLastWork().getTime() + getLastOverTime().getTime() - measure.getTime();
		long amount = getLastWork().getAmount() + getLastOverTime().getAmount() - measure.getAmount();
		val setting = dataAmountSetting.stream().sorted((x, y) -> y.getAmount() - x.getAmount())
				.filter(x -> amount >= x.getAmount()).findFirst();
		return new ItemValue(time, amount, setting.map(x -> x.getTreatmentByFrameColor()));
	}

	// [4] HTMLを作る
	public String createHtml(NRWebQueryMenuName menuName, YearMonth ym,
			List<EstimateAmountDetailImport> dataAmountSetting) {
		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("<HEAD><TITLE>");
		builder.append(menuName.value);
		builder.append("</TITLE></HEAD>");
		builder.append("<BODY><DL>");
		builder.append(String.format("<DT>%s</DT>", String.format("%d年%02d月度", ym.year(), ym.month())));
		builder.append(this.getMeasure().createOneDataHtml("目安時間", "目安金額"));
		builder.append(this.getCurrentWork().createOneDataHtml("現在勤務時間", "現在勤務金額"));
		builder.append(this.getCurrentOvertime().createOneDataHtml("現在勤務残業時間", "現在勤務残業金額"));
		builder.append(this.getScheduleWork().createOneDataHtml("計画勤務時間", "計画勤務金額"));
		builder.append(this.getScheduleOvertime().createOneDataHtml("計画勤務残業時間", "計画勤務残業金額"));
		builder.append(this.getLastWork().createOneDataHtml("最終勤務時間", "最終勤務金額"));
		builder.append(this.getLastOverTime().createOneDataHtml("最終勤務残業時間", "最終勤務残業金額"));
		builder.append(this.getLastWorkDiff(dataAmountSetting).createOneDataHtml("最終勤務差分時間", "最終勤務差分金額"));
		builder.append("</DL></BODY></HTML>");
		return builder.toString();
	}

	// [5] XMLを作る
	public String createXml(YearMonth ym, List<EstimateAmountDetailImport> dataAmountSetting) {
		StringBuilder builder = new StringBuilder();
		builder.append("<kindata type='5'><item type='head'  repbtn='no'>");
		builder.append(String.format("<subitem index='1' value = %s type='date' align='2'/>",
				String.format("%d年%02d月度", ym.year(), ym.month())));
		builder.append("<subitem index='2' value='時間' align='2'/><subitem index='3' value='金額' align='2'/></item>");
		builder.append(this.getMeasure().createOneDataXml("目安", Optional.empty()));
		builder.append(this.getCurrentWork().createOneDataXml("現在勤務", Optional.empty()));
		builder.append(this.getCurrentOvertime().createOneDataXml("残業", Optional.empty()));
		builder.append(this.getScheduleWork().createOneDataXml("計画勤務", Optional.empty()));
		builder.append(this.getScheduleOvertime().createOneDataXml("残業", Optional.empty()));
		builder.append(this.getLastWork().createOneDataXml("最終勤務", Optional.empty()));
		builder.append(this.getLastOverTime().createOneDataXml("残業", Optional.empty()));
		val itemCalc = this.getLastWorkDiff(dataAmountSetting);
		builder.append(itemCalc.createOneDataXml("最終勤務", itemCalc.getColor()));

		return builder.toString();

	}
}
