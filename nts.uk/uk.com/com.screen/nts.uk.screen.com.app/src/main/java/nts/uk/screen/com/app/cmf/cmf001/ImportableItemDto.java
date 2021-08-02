package nts.uk.screen.com.app.cmf.cmf001;

import lombok.Getter;
import lombok.ToString;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.DomainConstraint;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.shr.infra.web.component.validation.Helper;

@Getter
@ToString
public class ImportableItemDto {
	
	private boolean required;
	private DomainConstraintDto constraint;
	
	public static ImportableItemDto of(ImportableItem domain) {
		
		val dto = new ImportableItemDto();
		
		dto.required = domain.isRequired();
		dto.constraint = domain.getDomainConstraint().map(DomainConstraintDto::of).orElse(null);
		
		return dto;
	}
	
	@Getter
	@ToString
	public static class DomainConstraintDto {
		
		private String name;
		private String domainType;
		private String valueType;
		
		private String charType;
		private Integer maxLength;
	
		private String max;
		private String min;
		private String mantissaMaxLength;
		private Boolean isZeroPadded;
		
		public static DomainConstraintDto of(DomainConstraint constraint) {
			
			val constraintClass = constraint.getConstraintClass();
			
			if (constraint.getCheckMethod() == CheckMethod.PRIMITIVE_VALUE) {
				return asPrimitiveValue(constraintClass);
			} else {
				return asEnum(constraintClass);
			}
			
		}
		
		public static DomainConstraintDto asPrimitiveValue(Class<?> pvClass) {
			
			val dto = new DomainConstraintDto();
			dto.name = pvClass.getSimpleName();
			dto.domainType = "PrimitiveValue";
			dto.valueType = Helper.getValueType(pvClass);
			
			Helper.processConstraints(pvClass, (name, value) -> {
				
				switch (name) {
				case "charType":
					dto.charType = value.replace("'", ""); // シングルクォートが付けられてくるので除外
					break;
				case "maxLength":
					dto.maxLength = Integer.parseInt(value);
					break;
				case "max":
					dto.max = value;
					break;
				case "min":
					dto.min = value;
					break;
				case "mantissaMaxLength":
					dto.mantissaMaxLength = value;
					break;
				case "isZeroPadded":
					dto.isZeroPadded = "true".equals(value);
					break;
				}
			});
			
			return dto;
		}
	
		@SuppressWarnings("unchecked")
		public static <E extends Enum<?>> DomainConstraintDto asEnum(Class<?> enumClass) {
	
			val dto = new DomainConstraintDto();
			dto.name = enumClass.getSimpleName();
			dto.domainType = "Enum";
			dto.valueType = "Integer";
			
			val constants = EnumAdaptor.convertToValueNameList((Class<E>) enumClass);
			
			int max = constants.stream().mapToInt(e -> e.getValue()).max().getAsInt();
			int min = constants.stream().mapToInt(e -> e.getValue()).min().getAsInt();
			
			dto.max = Integer.toString(max);
			dto.min = Integer.toString(min);
			
			return dto;
		}
	}
}
