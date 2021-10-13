package nts.uk.ctx.exio.dom.input.workspace.item;

import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.DomainConstraint;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;
import nts.uk.shr.infra.web.component.validation.Helper;

/**
 * ワークスペースの項目
 */
@Value
public class WorkspaceItem {

	/** グループID */
	private final ImportingDomainId domainId;

	/** 項目No */
	private final int itemNo;

	/** 項目名 */
	private final String name;

	/** データ型構成 */
	private final DataTypeConfiguration dataTypeConfig;

	/**
	 * 受入可能項目の定義と一致しているか診断する
	 * 
	 * @param importableItem
	 * @return
	 */
	public boolean diagnose(ImportableItem importableItem) {
		val annotations = new AnnotationValues(importableItem.getDomainConstraint());

		return dataTypeConfig.getType().equals(DataType.of(importableItem.getItemType()))
				&& annotations.isSamesLengths(dataTypeConfig.getLength(), dataTypeConfig.getScale());
	}

	private class AnnotationValues {
		private Optional<Integer> maxLength = Optional.empty();
		private Optional<Integer> mantissaMaxLength = Optional.empty();

		public AnnotationValues(Optional<DomainConstraint> domainContraint) {
			domainContraint.filter(d -> d.getCheckMethod() == CheckMethod.PRIMITIVE_VALUE).ifPresent(constraint -> {
				Class<?> pvClass = constraint.getConstraintClass();
				Helper.processConstraints(pvClass, (name, value) -> {
					sw(name, value);
				});
			});
		}

		private void sw(String name, String value) {
			switch (name) {
			case "maxLength":
				maxLength = Optional.of(Integer.parseInt(value));
				break;
			case "mantissaMaxLength":
				mantissaMaxLength = Optional.of(Integer.parseInt(value));
				break;
			case "min":
			case "max":
				// HalfInteger系はここで対処
				if (value.contains(".")) {
					val vMantissaMaxLength = value.split("\\.")[1].length();
					val vTotalLength = Integer.toString(Math.abs(Integer.valueOf(value.split("\\.")[0]))).length();
					if (!maxLength.isPresent() || (vTotalLength) > maxLength.get()) {
						maxLength = Optional.of(vTotalLength);
					}
					if (!mantissaMaxLength.isPresent() || vMantissaMaxLength > mantissaMaxLength.get()) {
						mantissaMaxLength = Optional.of(vMantissaMaxLength);
					}
				}
				// 時分・時刻はここで
				else if (value.contains(":")) {
					val parts = value.split(":");
					val vLength = Integer
							.toString(Math.abs((Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]))))
							.length();
					if (!maxLength.isPresent() || vLength > maxLength.get()) {
						maxLength = Optional.of(vLength);
					}
				} else {
					val absValueLength = Integer.toString(Math.abs(Integer.parseInt(value))).length();
					if (!maxLength.isPresent() || absValueLength > maxLength.get()) {
						maxLength = Optional.of(absValueLength);
					}
				}
				break;
			}
		}

		public boolean isSamesLengths(int maxLength, int mantissaMaxLength) {
			return isSame(this.maxLength, maxLength) && isSame(this.mantissaMaxLength, mantissaMaxLength);
		}

		private boolean isSame(Optional<Integer> v, int length) {
			return v.map(value -> v.get().intValue() == length).orElse(true);
		}

	}
}
