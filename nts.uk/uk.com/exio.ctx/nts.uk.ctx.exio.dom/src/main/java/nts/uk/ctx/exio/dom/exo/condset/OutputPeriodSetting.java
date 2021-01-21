package nts.uk.ctx.exio.dom.exo.condset;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.出力条件設定.出力条件設定.出力期間設定
 */
@Getter
public class OutputPeriodSetting extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 期間設定
	 */
	private NotUseAtr periodSetting;

	/**
	 * 条件設定コード
	 */
	private ExternalOutputConditionCode conditionSetCode;

	/**
	 * 締め日区分 ClosureId
	 */
	private Optional<Integer> closureDayAtr;

	/**
	 * 基準日区分
	 */
	private Optional<BaseDateClassificationCode> baseDateClassification;

	/**
	 * 基準日指定
	 */
	private Optional<GeneralDate> baseDateSpecify;

	/**
	 * 終了日区分
	 */
	private Optional<EndDateClassificationCode> endDateClassification;

	/**
	 * 終了日指定
	 */
	private Optional<GeneralDate> endDateSpecify;

	/**
	 * 終了日調整
	 */
	private Optional<DateAdjustment> endDateAdjustment;

	/**
	 * 開始日区分
	 */
	private Optional<StartDateClassificationCode> startDateClassification;

	/**
	 * 開始日指定
	 */
	private Optional<GeneralDate> startDateSpecify;

	/**
	 * 開始日調整
	 */
	private Optional<DateAdjustment> startDateAdjustment;

	/**
	 * 1. Ẩn constructor để khởi tạo domain qua hàm createFromMemento
	 */
	private OutputPeriodSetting() {
	}

	/**
	 * 2. Hàm khởi tạo domain thông qua memento
	 * 
	 * @param memento
	 * @return
	 */
	public static OutputPeriodSetting createFromMemento(MementoGetter memento) {
		OutputPeriodSetting domain = new OutputPeriodSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public static OutputPeriodSetting cloneFromDomain(String newCid, String newConSetCd, OutputPeriodSetting sourceDomain) {
		OutputPeriodSetting targetDomain = new OutputPeriodSetting();
		// Set new cId + conditionSetCode
		targetDomain.cid = newCid;
		targetDomain.conditionSetCode = new ExternalOutputConditionCode(newConSetCd);
		// Clone data
		targetDomain.periodSetting = sourceDomain.periodSetting;
		targetDomain.closureDayAtr = sourceDomain.closureDayAtr;
		targetDomain.baseDateClassification = sourceDomain.baseDateClassification;
		targetDomain.baseDateSpecify = sourceDomain.baseDateSpecify;
		targetDomain.startDateClassification = sourceDomain.startDateClassification;
		targetDomain.startDateSpecify = sourceDomain.startDateSpecify;
		targetDomain.startDateAdjustment = sourceDomain.startDateAdjustment.map(primitiveValue -> new DateAdjustment(primitiveValue.v()));
		targetDomain.endDateClassification = sourceDomain.endDateClassification;
		targetDomain.endDateSpecify = sourceDomain.endDateSpecify;
		targetDomain.endDateAdjustment = sourceDomain.endDateAdjustment.map(primitiveValue -> new DateAdjustment(primitiveValue.v()));
		return targetDomain;
	}

	/**
	 * 3. Hàm get memento được sử dụng để cài đặt giá trị cho các primitive trong
	 * domain
	 * 
	 * @param memento Ý nghĩa của phương thức này là để thể hiện tính đóng gói (bao
	 *                đóng) của đối tượng. Mọi thuộc tính của đối tượng đều được
	 *                khởi tạo và cài đặt bên trong đối tượng. Hàm được sử dụng khi
	 *                lấy các primitive value từ command hoặc entity
	 */
	public void getMemento(MementoGetter memento) {
		this.cid = memento.getCid();
		this.periodSetting = NotUseAtr.valueOf(memento.getPeriodSetting());
		this.conditionSetCode = new ExternalOutputConditionCode(memento.getConditionSetCode());
		
		this.closureDayAtr = Optional.ofNullable(memento.getClosureDayAtr());
		this.baseDateClassification = Optional
				.ofNullable(EnumAdaptor.valueOf(memento.getBaseDateClassification(), BaseDateClassificationCode.class));
		this.baseDateSpecify = Optional.ofNullable(memento.getBaseDateSpecify());
		
		this.startDateClassification = Optional.ofNullable(
				EnumAdaptor.valueOf(memento.getStartDateClassification(), StartDateClassificationCode.class));
		this.startDateSpecify = Optional.ofNullable(memento.getStartDateSpecify());
		this.startDateAdjustment = Optional.ofNullable(memento.getStartDateAdjustment() == null 
				? null 
				: new DateAdjustment(memento.getStartDateAdjustment()));
		
		this.endDateClassification = Optional
				.ofNullable(EnumAdaptor.valueOf(memento.getEndDateClassification(), EndDateClassificationCode.class));
		this.endDateSpecify = Optional.ofNullable(memento.getEndDateSpecify());
		this.endDateAdjustment = Optional.ofNullable(memento.getEndDateAdjustment() == null 
				? null 
				: new DateAdjustment(memento.getEndDateAdjustment()));
	}

	/**
	 * 4. Hàm set memento được sử dụng để set các giá trị primitive của domain cho
	 * các đối tượng cần lấy dữ liệu như là dto hoặc entity
	 * 
	 * @param memento Ý nghĩa của hàm này cũng như getMemento, mọi lỗi ngoại lệ có
	 *                thể xảy ra trong domain đều được quản lý bởi domain
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCid(cid);
		if (this.periodSetting != null) {
			memento.setPeriodSetting(this.periodSetting.value);
		}
		if (this.conditionSetCode != null) {
			memento.setConditionSetCode(this.conditionSetCode.v());
		}

		memento.setClosureDayAtr(this.closureDayAtr.orElse(null));
		memento.setBaseDateClassification(this.baseDateClassification.map(v -> v.value).orElse(null));
		memento.setBaseDateSpecify(this.baseDateSpecify.orElse(null));

		memento.setStartDateClassification(this.startDateClassification.map(v -> v.value).orElse(null));
		memento.setStartDateSpecify(this.startDateSpecify.orElse(null));
		memento.setStartDateAdjustment(this.startDateAdjustment.map(DateAdjustment::v).orElse(null));

		memento.setEndDateClassification(this.endDateClassification.map(v -> v.value).orElse(null));
		memento.setEndDateSpecify(this.endDateSpecify.orElse(null));
		memento.setEndDateAdjustment(this.endDateAdjustment.map(DateAdjustment::v).orElse(null));
	}

	/**
	 * 5. interface này sẽ được implement bởi các đối tượng có quan hệ trực tiếp với
	 * domain Cụ thể trong trường hợp này là DTO và Entity là 2 đối tượng sẽ lấy dữ
	 * liệu từ domain trả ra. Như vậy 2 đối tượng kiểu này sẽ implement interface
	 * này
	 * 
	 * @author vuongnv
	 *
	 */
	public static interface MementoSetter {
		void setCid(String cid);
		void setPeriodSetting(int periodSetting);
		void setConditionSetCode(String conditionSetCode);

		void setClosureDayAtr(Integer closureDayAtr);
		void setBaseDateClassification(Integer baseDateClassification);
		void setBaseDateSpecify(GeneralDate baseDateSpecify);

		void setEndDateClassification(Integer endDateClassification);
		void setEndDateSpecify(GeneralDate endDateSpecify);
		void setEndDateAdjustment(Integer endDateAdjustment);

		void setStartDateClassification(Integer startDateClassification);
		void setStartDateSpecify(GeneralDate startDateSpecify);
		void setStartDateAdjustment(Integer startDateAdjustment);
	}

	/**
	 * 6. Interface này sẽ được implement bởi đối tượng sẽ sử dụng để khởi tạo
	 * domain Trong kiến trúc của project này thì có command và entity sẽ implement
	 * interface này.
	 * 
	 * @author vuongnv
	 *
	 */
	public static interface MementoGetter {
		String getCid();
		int getPeriodSetting();
		String getConditionSetCode();

		Integer getClosureDayAtr();
		Integer getBaseDateClassification();
		GeneralDate getBaseDateSpecify();

		Integer getEndDateClassification();
		GeneralDate getEndDateSpecify();
		Integer getEndDateAdjustment();

		Integer getStartDateClassification();
		GeneralDate getStartDateSpecify();
		Integer getStartDateAdjustment();
	}
}
