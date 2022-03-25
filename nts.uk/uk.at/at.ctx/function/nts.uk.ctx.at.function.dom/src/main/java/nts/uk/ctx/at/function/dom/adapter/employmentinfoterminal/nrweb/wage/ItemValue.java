package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.text.DecimalFormat;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* @author sakuratani
*
*			項目値
*         
*/
@Getter
@AllArgsConstructor
public class ItemValue {

	// 時間
	private int time;

	// 金額
	private long amount;

	// 色
	private Optional<String> color;

	public ItemValue(int time, long amount) {
		this(time, amount, Optional.empty());
	}

	// [1] XMLアイテムを作る
	public String createOneDataXml(String itemName, Optional<String> color) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem index='1' value=%s align='3'/>", itemName));
		builder.append(String.format("<subitem index='2' value=%salign='3'/>", timeFormat()));
		if (color.isPresent())
			builder.append(
					String.format("<subitem index='3' value=%s align='3' color=%s/>", amountFormat(), color.get()));
		else
			builder.append(String.format("<subitem index='3' value=%s align='3'/>", amountFormat()));
		return builder.toString();
	}

	// [2] HTMLアイテムを作る
	public String createOneDataHtml(String itemTimeName, String itemAmountName) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>%s</DT><DT>%s</DT><DT>%s</DT><DT>%s</DT>", itemTimeName, timeFormat(),
				itemAmountName, amountFormat()));
		return builder.toString();
	}

	private String timeFormat() {
		return String.format("%02d:%02d", time / 60, time % 60);
	}

	private String amountFormat() {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		return formatter.format(amount);
	}

	// ItemValueクラスのクローン
	public ItemValue clone() {
		return new ItemValue(this.time, this.amount, this.color);
	}

	// ItemValue（金額と時間）の合計を算出する
	public ItemValue add(ItemValue item) {
		ItemValue dom = this.clone();
		dom.time += item.getTime();
		dom.amount += item.getAmount();
		return dom;
	}
}